package com.xxl.service.borrowhistory;

import com.xxl.dao.history.BorrowBookHistoryMapper;
import com.xxl.pojo.BorrowBookHistory;
import com.xxl.util.LogUtil;
import com.xxl.util.TimeUtil;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author: xxl
 * @date: 2023/2/27
 * @ProjectDescription:
 */
@Transactional(rollbackFor = RuntimeException.class)
@Service
public class BorrowBookHistoryServiceImpl implements BorrowBookHistoryService {
    Logger logger = LogUtil.getLogger(BorrowBookHistoryServiceImpl.class);
    /**
     * 引用dao层
     */
    BorrowBookHistoryMapper mapper;
    @Resource
    public void setMapper(BorrowBookHistoryMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int addBorrowHistory(String userPhone, String bookId) {
        //每次添加借阅历史时，就会进行删除就旧历史(删除规则时删除15天之前的)
        int[] ints = TimeUtil.analysisString(TimeUtil.getTime());
        int i = mapper.deleteHistory(TimeUtil.computingTime(ints[0],ints[1],ints[2]));
        logger.info("【当前时间的前十五天="+ Arrays.toString(ints)+",删除的影响行数="+i+"】");
        BorrowBookHistory history = new BorrowBookHistory(userPhone,bookId);
        return mapper.addBorrowHistory(history);
    }

    @Override
    public int deleteHistory(String time) {
        return mapper.deleteHistory(time);
    }

    @Override
    public List<BorrowBookHistory> queryHistory(String userPhone) {
        return mapper.queryHistory(userPhone);
    }
}
