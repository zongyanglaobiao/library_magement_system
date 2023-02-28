package com.xxl.controller;

import com.xxl.service.borrowhistory.BorrowBookHistoryService;
import com.xxl.util.Jsonresult.JsonResultImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: xxl
 * @date: 2023/2/28
 * @ProjectDescription:  借阅历史控制器
 */
@RestController
@RequestMapping(value = "/history",produces = "application/json;charset=UTF-8")
public class BorrowBookHistoryController {

    BorrowBookHistoryService service;
    @Resource
    public void setService(BorrowBookHistoryService service) {
        this.service = service;
    }
    /**
     * 查询借书历史
     * @author xxl
     * @param  userPhone
     * @return String
     */
    @GetMapping("getHistory")
    public String queryHistory(@RequestParam("userPhone") String userPhone) {
        List<com.xxl.pojo.BorrowBookHistory> borrowBookHistories = service.queryHistory(userPhone);
        if (borrowBookHistories.size() > 0) {
            return JsonResultImpl.successResult(borrowBookHistories);
        }
        return JsonResultImpl.failResult("暂无借书历史");
    }
}
