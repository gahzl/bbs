package com.gahzl.bbs.notification;

import com.gahzl.bbs.common.BaseController;
import com.gahzl.bbs.common.Constants;
import com.gahzl.bbs.interceptor.UserInterceptor;
import com.gahzl.bbs.user.User;
import com.jfinal.aop.Before;

/**
 * Created by liuyang on 15/4/7.
 */
public class NotificationController extends BaseController {

    @Before(UserInterceptor.class)
    public void countnotread() {
        User user = getSessionAttr(Constants.USER_SESSION);
        if(user == null) {
            error(Constants.ResultDesc.FAILURE);
        } else {
            try {
                int count = Notification.me.countNotRead(user.getStr("id"));
                success(count);
            } catch (Exception e) {
                e.printStackTrace();
                error(Constants.ResultDesc.FAILURE);
            }
        }
    }
}
