package util;


import com.lawu.eshop.utils.DateUtil;
import constants.SmsConstant;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信工具类
 *
 * @author meishuquan
 * @date 2017/3/27
 */
public class SmsUtil {

    private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    /**
     * 发送短信验证码
     *
     * @param smsCode 短信验证码
     * @param mobile  手机号码
     * @param ip      请求IP
     * @return
     * @throws IOException
     */
    public static Map<String, Object> sendSms(String smsCode, String mobile, String ip) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        String sendResult = "";
        HttpClient httpclient = new HttpClient();
        PostMethod post = new PostMethod(SmsConstant.SMS_URL);
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, SmsConstant.SMS_ENCODING);
        post.addParameter("SpCode", SmsConstant.SP_CODE);
        post.addParameter("LoginName", SmsConstant.LOGIN_NAME);
        post.addParameter("Password", SmsConstant.PASSWORD);
        post.addParameter("MessageContent", SmsConstant.SMS_TEMPLATE.replace("{smsCode}", smsCode));
        post.addParameter("UserNumber", mobile);
        post.addParameter("SerialNumber", SmsConstant.SERIAL_NUMBER + DateUtil.getIntDateTime());
        post.addParameter("ScheduleTime", "");
        post.addParameter("ExtendAccessNum", "");
        post.addParameter("f", SmsConstant.F);
        httpclient.executeMethod(post);
        sendResult = new String(post.getResponseBody(), SmsConstant.SMS_ENCODING);
        logger.info("发送短信结果：{} --------- {}", sendResult, ip);
        if (sendResult.indexOf("发送短信成功") > 0) {
            map.put("sendCode", SmsConstant.SMS_SEND_SUCCESS);
            map.put("sendResult", "");
        } else {
            map.put("sendCode", SmsConstant.SMS_SEND_FAIL);
            map.put("sendResult", sendResult);
        }
        return map;
    }
}
