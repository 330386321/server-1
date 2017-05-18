package com.lawu.eshop.utils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.APNTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lohas on 2015/5/9. https://github.com/lohasle 个推 推送工程类
 */
public class GtPush {


	private static String appId = "y64i2nxRdNASBuhu7PCX25";// 商家
	private static String appkey = "m7BWuujJ246kECQYw8zk9A";// 商家
	private static String masterSecret = "rBgXZPQney8aaUvwaVW3b4";// 商家

	private static String appId2 = "TQd0dTZC8b7Az2zwH4wPk1";// 用户端
	private static String appkey2 = "vi1F0oUgRYAaxZ3xtPeVUA";// 用户端
	private static String masterSecret2 = "5f23soSbuN76qvvxOZipc1";// 用户端
	private static String host = "http://sdk.open.api.igexin.com/apiex.htm";

	/**
	 * 单推
	 * 
	 * @param contents
	 * @param CID
	 * @return
	 */
	public String sendMessageToCid(String contents, String CID, String title) {
		IGtPush push = new IGtPush(host, appkey, masterSecret);
		NotificationTemplate template = notificationTemplateDemo(title, contents);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(CID);
		// target.setAlias(Alias);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if (ret != null) {
			String result = (String) ret.getResponse().get("result");
			return result;
			// System.out.println(ret.getTaskId());
		} else {
			System.out.println("服务器响应异常");
			return "false";
		}
	}

	/**
	 * 单推用户端
	 * 
	 * @param contents
	 * @param CID
	 * @return
	 */
	public String sendMessageToCidCustoms(String contents, String CID, String title) {
		IGtPush push = new IGtPush(host, appkey2, masterSecret2);
		NotificationTemplate template = notificationTemplateDemo2(title, contents);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appId2);
		target.setClientId(CID);
		// target.setAlias(Alias);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if (ret != null) {
			String result = (String) ret.getResponse().get("result");
			return result;
			// System.out.println(ret.getTaskId());
		} else {
			System.out.println("服务器响应异常");
			return "false";
		}
	}

	public static NotificationTemplate notificationTemplateDemo(String title, String contents) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
		// 设置通知栏标题与内容
		template.setTitle(title);
		template.setText(contents);
		// 配置通知栏图标
		template.setLogo("icon.png");
		// 配置通知栏网络图标
		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		// template.setIsClearable(true);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(1);
		template.setTransmissionContent(contents);
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		return template;
	}

	/**
	 * 用户端推送
	 * 
	 * @param title
	 * @param contents
	 * @return
	 */
	public static NotificationTemplate notificationTemplateDemo2(String title, String contents) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId2);
		template.setAppkey(appkey2);
		// 设置通知栏标题与内容
		template.setTitle(title);
		template.setText(contents);
		// 配置通知栏图标
		template.setLogo("icon.png");
		// 配置通知栏网络图标
		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		// template.setIsClearable(true);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(1);
		template.setTransmissionContent(contents);
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		return template;
	}

	/**
	 * 推送给所有的商家
	 * 
	 * @param title
	 * @param contents
	 * @return
	 */
	public String pushToAllCompany(String title, String contents) {
		IGtPush push = new IGtPush(host, appkey, masterSecret);

		NotificationTemplate template = notificationTemplateDemo(title, contents);
		AppMessage message = new AppMessage();
		message.setData(template);

		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);
		// 推送给App的目标用户需要满足的条件
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(appId);
		message.setAppIdList(appIdList);

		IPushResult ret = push.pushMessageToApp(message);
		String result = (String) ret.getResponse().get("result");
		return result;
	}

	/**
	 * 推送给所有用户
	 * 
	 * @param title
	 * @param contents
	 * @return
	 */
	public String pushToAllUser(String title, String contents) {
		IGtPush push = new IGtPush(host, appkey2, masterSecret2);

		NotificationTemplate template = notificationTemplateDemo2(title, contents);
		AppMessage message = new AppMessage();
		message.setData(template);

		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);
		// 推送给App的目标用户需要满足的条件
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(appId2);
		message.setAppIdList(appIdList);

		IPushResult ret = push.pushMessageToApp(message);
		String result = (String) ret.getResponse().get("result");
		return result;
	}

	public static TransmissionTemplate getTemplate(String title, String contents) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId2);
		template.setAppkey(appkey2);
		template.setTransmissionContent(contents);
		template.setTransmissionType(2);
		APNPayload payload = new APNPayload();
		payload.setBadge(1);
		payload.setContentAvailable(1);
		payload.setSound("default");
		// payload.setCategory("default");
		// 简单模式APNPayload.SimpleMsg
		payload.setAlertMsg(new APNPayload.SimpleAlertMsg(title));

		// 字典模式使用下者
		// payload.setAlertMsg(getDictionaryAlertMsg());
		template.setAPNInfo(payload);
		return template;
	}

	public String pushUserIos(String contents, String devicetoken, String title) {
		IGtPush push = new IGtPush(host, appkey2, masterSecret2);
		TransmissionTemplate template = getTemplate(title, contents);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		// Target target = new Target();
		// target.setAppId(appId2);
		// target.setClientId(CID);

		// target.setAlias(Alias);
		IPushResult ret = null;
		try {
			// ret = push.pushMessageToSingle(message, target);
			// 通过ios设备唯一标识推送
			ret = push.pushAPNMessageToSingle(appId2, devicetoken, message);
		} catch (RequestException e) {
			e.printStackTrace();
			// ret = push.pushMessageToSingle(message, target,
			// e.getRequestId());
		}
		if (ret != null) {
			String result = (String) ret.getResponse().get("result");
			return result;
			// System.out.println(ret.getTaskId());
		} else {
			System.out.println("服务器响应异常");
			return "false";
		}
	}

	public static void apnpush() throws Exception {
		IGtPush push = new IGtPush(host, appkey2, masterSecret2);
		APNTemplate t = new APNTemplate();
		APNPayload apnpayload = new APNPayload();
		apnpayload.setSound("default");
		// apn高级推送
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		//// 通知文本消息标题
		alertMsg.setTitle("aaaaaa");
		// 通知文本消息字符串
		alertMsg.setBody("bbbb");
		// 对于标题指定执行按钮所使用的Localizable.strings,仅支持IOS8.2以上版本
		alertMsg.setTitleLocKey("ccccc");
		// 指定执行按钮所使用的Localizable.strings
		alertMsg.setActionLocKey("ddddd");
		apnpayload.setAlertMsg(alertMsg);

		t.setAPNInfo(apnpayload);
		SingleMessage sm = new SingleMessage();
		sm.setData(t);
		IPushResult ret0 = push.pushAPNMessageToSingle(appId2,
				"56095A26F34B71799ABEDA3312A7482A0D616180367DBCB435C59522C5BEF331", sm);
		System.out.println(ret0.getResponse());

	}

	public static void main(String[] args) throws Exception {
		/*GtPush p = new GtPush();
		System.out.println(p.sendMessageToCidCustoms("测试内容4", "7025e9871b8450137cdb0df202688d64", "测试标题4"));*/

		// apnpush();
	}
}
