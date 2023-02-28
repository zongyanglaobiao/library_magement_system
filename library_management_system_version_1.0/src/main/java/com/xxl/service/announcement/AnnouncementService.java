package com.xxl.service.announcement;

import com.xxl.pojo.Announcement;
import org.apache.ibatis.annotations.Param;

/**
 * @author: xxl
 * @date: 2023/2/25
 * @ProjectDescription:  业务层
 */
public interface AnnouncementService {
    /**
     * 查询公告，只有一条
     * @return Announcement
     * @author xxl
     */
    Announcement queryAnnouncement();
    /**
     * 修改公告，原则是数据库只有一条公告但管理员可以更新
     * @param content
     * @return  int
     * @author xxl
     */
    int updateAnnouncementById(String content);
}
