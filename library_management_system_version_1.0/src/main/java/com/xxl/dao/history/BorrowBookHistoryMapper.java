package com.xxl.dao.history;

import com.xxl.pojo.BorrowBookHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: xxl
 * @date: 2023/2/25
 * @ProjectDescription: 查看历史
 */
public interface BorrowBookHistoryMapper {
    /**
     * 增加记录时间
     * @param borrowBookHistory
     * @return int
     * @author xxl
     */
    int addBorrowHistory(BorrowBookHistory borrowBookHistory);

    /**
     * 删除记录时间,超过半个月自动删除
     * @param time
     * @return int
     * @author xxl
     */
    int deleteHistory(@Param("time")String time);

    /**
     * 查询用户借阅历史以及记录时间,需要联表查询(三表)
     * @param userPhone
     * @return  List<BorrowBookHistory>
     * @author xxl
     */
    List<BorrowBookHistory> queryHistory(@Param("userPhone")String userPhone);
}
