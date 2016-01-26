package com.gahzl.bbs.index;

import com.gahzl.bbs.common.BaseController;
import com.gahzl.bbs.common.Constants;
import com.gahzl.bbs.interceptor.AdminUserInterceptor;
import com.gahzl.bbs.mission.Mission;
import com.gahzl.bbs.reply.Reply;
import com.gahzl.bbs.topic.Topic;
import com.gahzl.bbs.user.User;
import com.jfinal.aop.Before;

import java.util.List;

/**
 * Created by liuyang on 15/4/9.
 */
@Before(AdminUserInterceptor.class)
public class IndexAdminController extends BaseController {

    public void index() {
        //今日话题
        List<Topic> topics = Topic.me.findToday();
        setAttr("topics", topics);
        //今日回复
        List<Reply> replies = Reply.me.findToday();
        setAttr("replies", replies);
        //今日签到
        List<Mission> missions = Mission.me.findToday();
        setAttr("missions", missions);
        //今日用户
        List<User> users = User.me.findToday();
        setAttr("users", users);
        render("index.html");
    }

    public void logout() {
        removeSessionAttr(Constants.SESSION_ADMIN_USER);
        removeCookie(Constants.COOKIE_ADMIN_TOKEN);
        redirect(Constants.getBaseUrl() + "/");
    }
}