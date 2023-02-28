package com.xxl.dao.book;

import com.xxl.pojo.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: xxl
 * @date: 2023/2/25
 * @ProjectDescription: 图书表dao层
 */
public interface BookMapper {
    /**
     * 根据图书名称或者图书编号查询图书信息，可查询图书的编号、名称、作者、价格等。
     * 既可以查全部也可定位到某一本书
     * @author xxl
     * @param bookName
     * @param bookNo
     * @return List<Book>
     */
    List<Book> queryAllBooks(@Param("bookName")String bookName,@Param("bookNo")String bookNo);

    /**
     * 根据图书名称添加图书
     * @param book
     * @return int
     * @author xxl
     */
    int addBookByName(Book book);
    /**
     * 根据图书编号、图书名称修改图书
     * @author xxl
     * @param book
     * @return int
     */
    int modifyBook(Book book);
    /**
     * 根据图书编号、图书名称删除图书
     * @author xxl
     * @param bookId
     * @return int
     */
    int deleteById(@Param("bookId")String bookId);

}
