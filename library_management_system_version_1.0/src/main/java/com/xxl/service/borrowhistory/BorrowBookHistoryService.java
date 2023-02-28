package com.xxl.service.borrowhistory;

import com.xxl.pojo.BorrowBookHistory;

import java.util.List;

/**
 * @author: xxl
 * @date: 2023/2/27
 * @ProjectDescription: 业务层
 */
public interface BorrowBookHistoryService {
    /**
     * 增加记录时间
     * @param userPhone
     * @param bookId
     * @return int
     * @author xxl
     */
    int addBorrowHistory(String userPhone, String bookId);

    /**
     * 删除记录时间,超过半个月自动删除
     * @param time
     * @return int
     * @author xxl
     */
    int deleteHistory(String time);

    /**
     * 查询用户借阅历史以及记录时间,需要联表查询(三表)
     * @param userPhone
     * @return  List<BorrowBookHistory>
     * @author xxl
     */
    List<BorrowBookHistory> queryHistory(String userPhone);
}
