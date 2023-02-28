package com.xxl.controller;

import com.xxl.pojo.Announcement;
import com.xxl.service.announcement.AnnouncementService;
import com.xxl.util.Jsonresult.JsonResultImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xxl
 * @date: 2023/2/25
 * @ProjectDescription:
 */
@RestController
@RequestMapping(value = "announcement",produces = "application/json;charset=utf-8")
public class AnnouncementController {
    /**
     *  调用业务层
     */
    AnnouncementService service;
    @Resource
    public void setService(AnnouncementService service) {
        this.service = service;
    }
    @GetMapping("getAnnouncement")
    public String getAnnouncement() {
        Announcement announcement = service.queryAnnouncement();
        return JsonResultImpl.successResult(announcement);
    }
    /**
     * 修改公告(需要权限)
     * @author xxl
     * @param  content
     * @return String
     */
    @GetMapping("permissions/modifyAnnouncement")
    public String modifyAnnouncement(@RequestParam("content")String content) {
        int i = service.updateAnnouncementById(content);
        if (i > 0) {
            return JsonResultImpl.successResult("修改公告成功");
        }
        return JsonResultImpl.failResult("修改公告失败");
    }
}
