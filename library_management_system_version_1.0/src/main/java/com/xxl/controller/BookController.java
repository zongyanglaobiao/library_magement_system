package com.xxl.controller;

import com.xxl.pojo.Book;
import com.xxl.service.book.BookService;
import com.xxl.service.borrowhistory.BorrowBookHistoryService;
import com.xxl.util.ConstantUtil;
import com.xxl.util.Jsonresult.JsonResultImpl;
import com.xxl.util.LogUtil;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author: xxl
 * @date: 2023/2/26
 * @ProjectDescription: 控制器
 */
@RestController
@RequestMapping(value = "book",produces = "application/json;charset=UTF-8")
public class BookController {
    private Logger logger = LogUtil.getLogger(BookController.class);
    /**
     *  引用业务层
     */
    BookService bookService;
    BorrowBookHistoryService historyService;
    @Resource
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    @Resource
    public void setHistoryService(BorrowBookHistoryService historyService) {
        this.historyService = historyService;
    }
    /**
     * 查询书籍可带条件
     * @author xxl
     * @param  bookName
     * @param  bookNo
     * @return String
     */
    @GetMapping("queryAllBook")
    public String queryAllBook(String bookName,String bookNo) {
        logger.info("不用requestParams结果，书名="+bookName+"书id="+bookNo);
        List<Book> books = bookService.queryAllBooks(bookName, bookNo);
        if (books.size() > 0) {
            return JsonResultImpl.successResult(books);
        }else {
            return JsonResultImpl.failResult("查询失败");
        }
    }
    /**
     * 添加书本(需要权限)可能会添加同一样的书，只是编号不一样
     * @author xxl
     * @param  bookName
     * @param  bookAuthor
     * @param  bookPrice
     * @return String
     */
    @GetMapping("permissions/addBook")
    public String addBook(@RequestParam("bookName")String bookName,
                          @RequestParam("bookAuthor") String bookAuthor,
                          @RequestParam("bookPrice") int bookPrice )
    {
        int i = bookService.addBookByName(bookName, bookAuthor, bookPrice);
        if (i > 0) {
            return JsonResultImpl.successResult("添加成功");
        }
        return JsonResultImpl.failResult("添加失败");
    }
    /**
     * @RequestBody解析前端的参数
     * @author xxl
     * @param
     * @return
     */
    @PostMapping("permissions/modifyBook")
    public String modify(@RequestBody Book book){
        logger.info("book="+book);
        return modifyBook(
                book.getBookName(),
                book.getBookAuthor(),
                book.getBookPrice(),
                book.getUserPhone(),
                book.getIsBorrowing(),
                book.getBookId()
                );
    }




    /**
     * 根据图书编号、图书名称修改图书(不精通前端不然这里是个对象)
     * 先查看是否有此书，有就可以修改
     * @author xxl
     * @param bookName
     * @param bookAuthor
     * @param bookPrice
     * @param userPhone
     * @param isBorrowing
     * @param bookId
     * @return int
     */
    public String modifyBook(String bookName,
                             String bookAuthor,
                             Integer bookPrice,
                             String userPhone,
                             Integer isBorrowing,
                             String bookId)
    {
        logger.info("bookId="+bookId+",isBorrowing="+isBorrowing+",bookName="+bookName+",bookAuthor="+bookAuthor+",bookPrice="+bookPrice+",userPhone="+userPhone);
        if (bookId == null){
            return JsonResultImpl.failResult("bookId为null，因此不知修改那本书");
        }
        //精确查询只会有一本书被查到
        List<Book> books = bookService.queryAllBooks(null, bookId);
        //大于0表示就会查询有此书
        if (books.size() > 0) {
            //如果是借书需要判断书是否借出去了
            if (isBorrowing == ConstantUtil.BORROWED) {
                Book book = books.get(0);
                if (book.getIsBorrowing() == ConstantUtil.BORROWED) {
                    return JsonResultImpl.failResult("借书失败，此书已经被借阅了");
                }
            }

            int i = bookService.modifyBook(
                    bookName,
                    bookAuthor,
                    bookPrice,
                    null,
                    userPhone,
                    isBorrowing,
                    bookId
            );
            if (i > 0) {
                return JsonResultImpl.successResult("修改成功");
            }
        }
        return JsonResultImpl.failResult("修改失败，没有此书,，也有可能是其他原因");
    }

    @GetMapping("permissions/deleteBook")
    public String deleteBook(@RequestParam("bookId")String bookId)
    {
        int i = bookService.deleteById(bookId);
        if (i > 0) {
            return JsonResultImpl.successResult("删除成功");
        }
        return JsonResultImpl.failResult("删除失败");
    }

    /**
     * 借书或者还书，不需要权限
     * @param
     * @return
     * @author xxl
     */
    @GetMapping("borrow")
    public String borrow(@RequestParam("isBorrowing") Integer isBorrowing,
                         @RequestParam("bookId")String bookId,
                         @RequestParam("userPhone")String userPhone)
    {
        return modifyBook(null, null, null,  userPhone, isBorrowing, bookId);
    }

    /**
     * 添加书图片
     * @author xxl
     * @param  bookId 用来查找那本书
     * @param  file 文件上传
     * @return
     */
    @PostMapping("permissions/loadBookImg")
    public String addBookImg(@RequestParam("bookId")String bookId,
                             @RequestParam("fileLoad") MultipartFile file)
    {
        //查询是否有此书
        List<Book> books = bookService.queryAllBooks(null, bookId);
        if (books.size() > 0) {
            try {
                //图片名
                String originalFilename = file.getOriginalFilename();
                logger.info("文件名= "+originalFilename);
                //图片地址
                String path = ConstantUtil.ImgPath+System.currentTimeMillis()+originalFilename;
                logger.info("图片具体位置="+path);
                //保存图片
                file.transferTo(new File(path));
                //文件上传成功后就把此地址上传到数据库
                bookService.modifyBook(null,null,null,path,null,null,bookId);
                return  JsonResultImpl.successResult("文件上传成功");
            } catch (IOException e) {
                return JsonResultImpl.failResult("文件上传,具体原因:"+e);
            }
        }
        return JsonResultImpl.failResult("没有此书");
    }
}
