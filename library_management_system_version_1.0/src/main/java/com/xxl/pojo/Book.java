package com.xxl.pojo;

import com.xxl.util.ConstantUtil;
import com.xxl.util.UUIDUtil;
import lombok.Data;

/**
 * @author: xxl
 * @date: 2023/2/25
 * @ProjectDescription: 图书类
 */
@Data
public class Book {
    private String bookName;
    private String bookId;
    private String bookAuthor;
    private Integer  bookPrice ;
    private String bookImgUrl;
    private String userPhone;
    private Integer isBorrowing;

    public Book(String bookName, String bookAuthor, int bookPrice) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
        //默认创建书id
        this.bookId = UUIDUtil.getUUID();
        //默认未借出去
        this.isBorrowing = ConstantUtil.BORROW;
    }

    public Book() {

    }




    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                ", bookId='" + bookId + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPrice=" + bookPrice +
                ", bookImgUrl='" + bookImgUrl + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", isBorrowing=" + isBorrowing +
                '}';
    }
}
