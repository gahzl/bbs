package com.gahzl.bbs.interceptor;

import com.gahzl.bbs.common.Constants;
import com.gahzl.bbs.link.Link;
import com.gahzl.bbs.section.Section;
import com.gahzl.bbs.user.User;
import com.gahzl.bbs.utils.DateUtil;
import com.gahzl.bbs.utils.StrUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import java.util.Date;

/**
 * Created by liuyang on 15/4/2.
 */
public class CommonInterceptor implements Interceptor {

    public void intercept(Invocation ai) {
        // session cookie 互换
        String user_cookie = ai.getController().getCookie(Constants.USER_COOKIE);
        User user_session = (User) ai.getController().getSession().getAttribute(Constants.USER_SESSION);
        if(StrUtil.isBlank(user_cookie) && user_session != null) {
            ai.getController().setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user_session.getStr("token")), 30*24 * 60 * 60);
        } else if (!StrUtil.isBlank(user_cookie) && user_session == null) {
            User user = User.me.findByToken(StrUtil.getDecryptToken(user_cookie));
            ai.getController().setSessionAttr(Constants.USER_SESSION, user);
        }

        // 获取今天时间，放到session里
        ai.getController().setSessionAttr(Constants.TODAY, DateUtil.formatDate(new Date()));

        // 查询模块
        ai.getController().getRequest().setAttribute("sections", Section.me.findShow());

        // 查询友链
        ai.getController().getRequest().setAttribute("links", Link.me.findAll());

        //生产
        ai.getController().getRequest().setAttribute("baseUrl", Constants.getBaseUrl());

        ai.getController().getRequest().setAttribute("siteTitle", Constants.getSiteTitle());

        ai.invoke();
    }
}
