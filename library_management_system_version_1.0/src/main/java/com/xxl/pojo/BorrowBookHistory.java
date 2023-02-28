package com.xxl.pojo;

import com.xxl.util.TimeUtil;
import lombok.Data;

import java.util.Date;

/**
 * @author: xxl
 * @date: 2023/2/25
 * @ProjectDescription: 借阅历史实体类
 */
@Data
public class BorrowBookHistory {
    private String borrowId;
    private String borrowTime;
    private String userPhone;
    private String bookId;
    private Book book;

    public BorrowBookHistory() {
    }

    public BorrowBookHistory(String userPhone, String bookId) {
        this.userPhone = userPhone;
        this.bookId = bookId;
        this.borrowTime = TimeUtil.getTime();
    }
}
