package com.lawu.eshop.utils;

import com.gexin.fastjson.JSON;
import com.gexin.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lohas on 2015/5/9. https://github.com/lohasle 个推 推送工程类
 */
public class GtPush {

    private static Logger logger = LoggerFactory.getLogger(GtPush.class);

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
        TransmissionTemplate template = getTemplateMerchant(title, contents);
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
            logger.info("gtpush result:result({})", result);
            return result;
        } else {
            logger.error("gtpush--服务器响应异常");
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
        TransmissionTemplate template = getTemplateUser(title, contents);
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
            logger.info("gtpush result:result({})", result);
            return result;
        } else {
            logger.error("gtpush--服务器响应异常");
            return "false";
        }
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
        TransmissionTemplate template = getTemplateMerchant(title, contents);
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
        TransmissionTemplate template = getTemplateUser(title, contents);
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

    /**
     * 推送商家透传模板
     *
     * @param title
     * @param contents
     * @return
     * @author zhangyong
     */
    public static TransmissionTemplate getTemplateMerchant(String title, String contents) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        template.setTransmissionContent(contents);
        template.setTransmissionType(2);
        APNPayload payload = new APNPayload();
        payload.setBadge(1);
        payload.setContentAvailable(1);
        payload.setSound("default");
        // 字典模式使用下者
        payload.setAlertMsg(getDictionaryAlertMsg(title, contents));
        template.setAPNInfo(payload);
        return template;
    }

    /**
     * 用户端推送透传模板
     *
     * @param title
     * @param contents
     * @return
     */
    public static TransmissionTemplate getTemplateUser(String title, String contents) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId2);
        template.setAppkey(appkey2);
        template.setTransmissionContent(contents);
        template.setTransmissionType(2);
        APNPayload payload = new APNPayload();
        payload.setBadge(1);
        payload.setContentAvailable(1);
        payload.setSound("default");

        // 字典模式使用下者
        payload.setAlertMsg(getDictionaryAlertMsg(title, contents));
        template.setAPNInfo(payload);
        return template;
    }

    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title, String contents) {
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody(contents);
        alertMsg.setActionLocKey("ActionLockey");
        JSONObject jobj = JSON.parseObject(contents);
        //推送展示内容
        alertMsg.setLocKey(jobj.get("content").toString());

        alertMsg.addLocArg("loc-args");
        alertMsg.setLaunchImage("launch-image");
        // iOS8.2以上版本支持
        alertMsg.setTitle(title);
        //推送展示标题
        alertMsg.setTitleLocKey(title);
        alertMsg.addTitleLocArg(contents);

        return alertMsg;
    }


  /*  public static void main(String[] args) throws Exception {
    	GtPush p = new GtPush();
		JSONObject json = new JSONObject();
		json.put("title","积分充值");
		json.put("content","充值10块钱");
		json.put("type","MESSAGE_TYPE_RECHARGE_BALANCE");
		System.out.println(p.sendMessageToCidCustoms(json.toString(),"7025e9871b8450137cdb0df202688d64", "积分充值"));
    }*/
}
