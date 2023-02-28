package com.xxl.dao.announcement;

import com.xxl.pojo.Announcement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author:xxl
 * @date:2023/2/25
 * @ProjectDescription: 自定义公告，可以不加注释因为这里的动态代理实例由spring帮我们做完了
 */
@Repository
public interface CustomAnnouncement {
    /**
     * 查询公告，只有一条
     * @param id
     * @return Announcement
     * @author xxl
     */
    Announcement queryAnnouncement(@Param("id")int  id);
    /**
     * 修改公告，原则是数据库只有一条公告但管理员可以更新
     * @param id
     * @param content
     * @return  int
     * @author xxl
     */
    int updateAnnouncementById(@Param("id")int id,@Param("content")String content);
}
