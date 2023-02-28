package com.xxl.service.book;

import com.xxl.dao.book.BookMapper;
import com.xxl.dao.history.BorrowBookHistoryMapper;
import com.xxl.pojo.Book;
import com.xxl.pojo.BorrowBookHistory;
import com.xxl.service.borrowhistory.BorrowBookHistoryService;
import com.xxl.util.ConstantUtil;
import com.xxl.util.LogUtil;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: xxl
 * @date: 2023/2/26
 * @ProjectDescription:
 */
@Transactional(rollbackFor = RuntimeException.class)
@Service
public class BookServiceImpl implements BookService{
    private Logger logger = LogUtil.getLogger(BookServiceImpl.class);
    /**
     *  引用service层,用于记录借书时增加记录
     */
    BorrowBookHistoryService historyMapper;
    @Resource
    public void setHistoryMapper(BorrowBookHistoryService historyMapper) {
        this.historyMapper = historyMapper;
    }

    /**
     *  book的dao层
     */
    BookMapper bookMapper;
    @Resource
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public List<Book> queryAllBooks(String bookName, String bookNo) {
        return bookMapper.queryAllBooks(bookName,bookNo);
    }
    /**
     * 主要限制增加书的条件
     * @param bookName
     * @param bookAuthor
     * @param bookPrice
     * @return int
     * @author xxl
     */
    @Override
    public int addBookByName(String bookName, String bookAuthor, int bookPrice) {
        Book book1 = new Book(bookName,bookAuthor,bookPrice);
        return bookMapper.addBookByName(book1);
    }
    /**
     *  图书的id不可改变,用来记录查阅历史
     */
    @Override
    public int modifyBook(String bookName,
                          String bookAuthor,
                          Integer bookPrice,
                          String bookImgUrl,
                          String userPhone,
                          Integer isBorrowing,
                          String bookId)
    {
        logger.info("isBorrowing="+isBorrowing);
        Book book = new Book();
        book.setBookName(bookName);
        book.setBookAuthor(bookAuthor);
        book.setBookPrice(bookPrice);
        book.setBookImgUrl(bookImgUrl);
        book.setBookId(bookId);
        if (isBorrowing != null) {
            //如果是借书，就把此纪录纪录下
            if (isBorrowing == ConstantUtil.BORROWED) {
                book.setIsBorrowing(ConstantUtil.BORROWED);
                //设置借书人的号码
                book.setUserPhone(userPhone);
                int i = historyMapper.addBorrowHistory(userPhone,bookId);
                logger.info("记录返回影响行数"+i);
            }
            if (isBorrowing == ConstantUtil.BORROW){
                //如果是还书就把书的状态设置为0为借阅状态，同时删除手机号码(不管有没有)
                book.setUserPhone("");
                book.setIsBorrowing(ConstantUtil.BORROW);
            }
        }
        logger.info("需要修改的book="+book);
        return bookMapper.modifyBook(book);
    }

    @Override
    public int deleteById(@RequestParam("bookId") String bookId) {
        return bookMapper.deleteById(bookId);
    }
}
