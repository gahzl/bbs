package com.gahzl.bbs.reply;

import com.gahzl.bbs.common.BaseController;
import com.gahzl.bbs.common.Constants;
import com.gahzl.bbs.notification.Notification;
import com.gahzl.bbs.topic.Topic;
import com.gahzl.bbs.user.User;
import com.gahzl.bbs.utils.DateUtil;
import com.gahzl.bbs.utils.StrUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyang on 2015/6/27.
 */
public class ReplyClientController extends BaseController {

    public void create() {
        error("暂不支持回复");
    }
}
