package com.gahzl.bbs.valicode;

import com.gahzl.bbs.utils.DateUtil;
import com.gahzl.bbs.utils.StrUtil;
import com.jfinal.plugin.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya on 15/9/29.
 */
public class ValiCode extends Model<ValiCode> implements Serializable {
    public final static ValiCode me = new ValiCode();

    //查询未过期的验证码
    public ValiCode findByCodeAndEmail(String code, String email, String type) {
        String nowTime = DateUtil.formatDateTime(new Date());
        return super.findFirst("select * from valicode v where v.status = 0 and v.code = ? and v.target = ? and v.expire_time > ? and v.type = ?", code, email, nowTime, type);
    }
}
