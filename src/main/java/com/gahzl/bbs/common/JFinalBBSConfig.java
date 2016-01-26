package com.gahzl.bbs.common;

import com.gahzl.bbs.collect.Collect;
import com.gahzl.bbs.collect.CollectClientController;
import com.gahzl.bbs.collect.CollectController;
import com.gahzl.bbs.handler.HtmlHandler;
import com.gahzl.bbs.index.IndexAdminController;
import com.gahzl.bbs.index.IndexClientController;
import com.gahzl.bbs.index.IndexController;
import com.gahzl.bbs.interceptor.CommonInterceptor;
import com.gahzl.bbs.label.Label;
import com.gahzl.bbs.label.LabelAdminController;
import com.gahzl.bbs.label.LabelController;
import com.gahzl.bbs.label.LabelTopicId;
import com.gahzl.bbs.link.Link;
import com.gahzl.bbs.link.LinkAdminController;
import com.gahzl.bbs.mission.Mission;
import com.gahzl.bbs.mission.MissionAdminController;
import com.gahzl.bbs.mission.MissionClientController;
import com.gahzl.bbs.mission.MissionController;
import com.gahzl.bbs.notification.Notification;
import com.gahzl.bbs.notification.NotificationClientController;
import com.gahzl.bbs.notification.NotificationController;
import com.gahzl.bbs.reply.Reply;
import com.gahzl.bbs.reply.ReplyAdminController;
import com.gahzl.bbs.reply.ReplyClientController;
import com.gahzl.bbs.reply.ReplyController;
import com.gahzl.bbs.section.Section;
import com.gahzl.bbs.section.SectionAdminController;
import com.gahzl.bbs.section.SectionClientController;
import com.gahzl.bbs.topic.Topic;
import com.gahzl.bbs.topic.TopicAdminController;
import com.gahzl.bbs.topic.TopicClientController;
import com.gahzl.bbs.topic.TopicController;
import com.gahzl.bbs.user.AdminUser;
import com.gahzl.bbs.user.User;
import com.gahzl.bbs.user.UserAdminController;
import com.gahzl.bbs.user.UserClientController;
import com.gahzl.bbs.user.UserController;
import com.gahzl.bbs.valicode.ValiCode;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;

/**
 * API引导式配置
 */
public class JFinalBBSConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("config.properties");
		me.setDevMode(getPropertyToBoolean("devMode", false));
        me.setBaseUploadPath(com.gahzl.bbs.common.Constants.UPLOAD_DIR);
		me.setMaxPostSize(2048000);
    }
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", IndexController.class, "ftl");	// 第三个参数为该Controller的视图存放路径
		me.add("/topic", TopicController.class, "ftl");
		me.add("/user", UserController.class, "ftl");
		me.add("/mission", MissionController.class, "ftl");
		me.add("/reply", ReplyController.class, "ftl");
		me.add("/collect", CollectController.class, "ftl");
		me.add("/notification", NotificationController.class, "ftl");
		me.add("/label", LabelController.class, "ftl");
        //添加后台路由
		adminRoute(me);
        //添加客户端路由
        clientRoute(me);
	}

	//后台路由配置
    public void adminRoute(Routes me) {
        me.add("/admin", IndexAdminController.class, "ftl/admin");
        me.add("/admin/topic", TopicAdminController.class, "ftl/admin/topic");
        me.add("/admin/reply", ReplyAdminController.class, "ftl/admin/reply");
        me.add("/admin/user", UserAdminController.class, "ftl/admin/user");
        me.add("/admin/section", SectionAdminController.class, "ftl/admin/section");
        me.add("/admin/link", LinkAdminController.class, "ftl/admin/link");
        me.add("/admin/mission", MissionAdminController.class, "ftl/admin/mission");
        me.add("/admin/label", LabelAdminController.class, "ftl/admin/label");
    }

	public void clientRoute(Routes me) {
        me.add("/api/index", IndexClientController.class);
        me.add("/api/topic", TopicClientController.class);
        me.add("/api/reply", ReplyClientController.class);
        me.add("/api/user", UserClientController.class);
        me.add("/api/notification", NotificationClientController.class);
        me.add("/api/section", SectionClientController.class);
        me.add("/api/collect", CollectClientController.class);
        me.add("/api/mission", MissionClientController.class);
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
        DruidPlugin druidPlugin = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
        druidPlugin.setFilters("stat,wall");
		me.add(druidPlugin);
        me.add(new EhCachePlugin());
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.setShowSql(getPropertyToBoolean("showSql", false));
		me.add(arp);
		arp.addMapping("topic", Topic.class);	// 映射blog 表到 Blog模型
		arp.addMapping("reply", Reply.class);
		arp.addMapping("user", User.class);
		arp.addMapping("mission", Mission.class);
		arp.addMapping("collect", Collect.class);
		arp.addMapping("notification", Notification.class);
		arp.addMapping("admin_user", AdminUser.class);
		arp.addMapping("section", Section.class);
		arp.addMapping("link", Link.class);
		arp.addMapping("valicode", ValiCode.class);
		arp.addMapping("label", Label.class);
		arp.addMapping("label_topic_id", LabelTopicId.class);
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
        me.add(new SessionInViewInterceptor());
        me.add(new CommonInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
        //配置druid的监听，可以在浏览器里输入http://localhost:8080/druid 查看druid监听的数据
        me.add(new DruidStatViewHandler("/druid"));
        me.add(new HtmlHandler());
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 80, "/", 5);
	}
}
