package com.xxl.service.announcement;

import com.xxl.dao.announcement.CustomAnnouncement;
import com.xxl.pojo.Announcement;
import com.xxl.util.ConstantUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: xxl
 * @date: 2023/2/25
 * @ProjectDescription:
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementService{
    /**
     *  调用业务层
     */
    CustomAnnouncement announcement;

    @Resource
    public void setAnnouncement(CustomAnnouncement announcement) {
        this.announcement = announcement;
    }

    @Override
    public Announcement queryAnnouncement() {
        return announcement.queryAnnouncement(ConstantUtil.ANNOUNCEMENT_ID);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int updateAnnouncementById(String content) {
        return announcement.updateAnnouncementById(ConstantUtil.ANNOUNCEMENT_ID,content);
    }
}
