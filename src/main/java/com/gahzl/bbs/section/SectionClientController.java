package com.gahzl.bbs.section;

import com.gahzl.bbs.common.BaseController;

/**
 * Created by liuyang on 2015/6/27.
 */
public class SectionClientController extends BaseController {

    public void index() {
        success(Section.me.findShow());
    }
}
