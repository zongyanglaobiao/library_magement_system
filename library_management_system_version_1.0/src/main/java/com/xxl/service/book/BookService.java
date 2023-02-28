package com.xxl.service.book;

import com.xxl.pojo.Book;
import jakarta.annotation.Nullable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: xxl
 * @date: 2023/2/26
 * @ProjectDescription:  业务层
 */
public interface BookService {
    /**
     * 根据图书名称或者图书编号查询图书信息，可查询图书的编号、名称、作者、价格等。
     * 既可以查全部也可定位到某一本书
     * @author xxl
     * @param bookName
     * @param bookNo
     * @return List<Book>
     */
    List<Book> queryAllBooks(@Nullable String bookName, @Nullable String bookNo);

    /**
     * 根据图书名称添加图书
     * @param bookName
     * @param bookAuthor
     * @param bookPrice
     * @return int
     * @author xxl
     */
    int addBookByName(String bookName, String bookAuthor, int bookPrice);
    /**
     * 根据图书编号、图书名称修改图书
     * @author xxl
     * @param bookName
     * @param bookAuthor
     * @param bookPrice
     * @param bookImgUrl
     * @param userPhone
     * @param isBorrowing
     * @param bookId
     * @return int
     */
    public int modifyBook(@Nullable String bookName,
                          @Nullable String bookAuthor,
                          @Nullable Integer bookPrice,
                          @Nullable String bookImgUrl,
                          @Nullable String userPhone,
                          @Nullable Integer isBorrowing,
                          @Nullable String bookId);
    /**
     * 根据图书编号、图书名称删除图书
     * @author xxl
     * @param bookId
     * @return int
     */
    int deleteById(String bookId);

}
