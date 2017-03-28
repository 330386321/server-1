package com.lawu.eshop.authorization.interceptor;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.manager.TokenManager;
import com.lawu.eshop.authorization.manager.impl.AbstractTokenManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;

/**
 * 自定义拦截器，对请求进行身份验证
 * 引入JWT 2017/03/15
 *
 * @author ScienJus
 * @author Leach
 * @date 2015/7/30.
 * @see com.lawu.eshop.authorization.annotation.Authorization
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    /**
     * 存放登录用户模型Key的Request Key
     */
    public static final String REQUEST_CURRENT_USER_NUM = "REQUEST_CURRENT_USER_NUM";

    public static final String REQUEST_CURRENT_USER_ID = "REQUEST_CURRENT_USER_ID";

    public static final String REQUEST_CURRENT_ACCOUNT = "REQUEST_CURRENT_ACCOUNT";

    private Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    // 管理身份验证操作的对象
    private TokenManager manager;

    // 存放鉴权信息的Header名称，默认是Authorization
    private String httpHeaderName = "Authorization";

    // 鉴权信息的无用前缀，默认为空
    private String httpHeaderPrefix = "";

    // 鉴权失败后返回的错误信息，默认为401 unauthorized
    private String unauthorizedErrorMessage = "401 unauthorized";

    // 鉴权失败后返回的HTTP错误码，默认为401
    private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

    private Long testId;

    private String testNum;

    private String testAccount;

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public void setTestNum(String testNum) {
        this.testNum = testNum;
    }

    public void setTestAccount(String testAccount) {
        this.testAccount = testAccount;
    }

    public void setManager(TokenManager manager) {
        this.manager = manager;
    }

    public void setHttpHeaderName(String httpHeaderName) {
        this.httpHeaderName = httpHeaderName;
    }

    public void setHttpHeaderPrefix(String httpHeaderPrefix) {
        this.httpHeaderPrefix = httpHeaderPrefix;
    }

    public void setUnauthorizedErrorMessage(String unauthorizedErrorMessage) {
        this.unauthorizedErrorMessage = unauthorizedErrorMessage;
    }

    public void setUnauthorizedErrorCode(int unauthorizedErrorCode) {
        this.unauthorizedErrorCode = unauthorizedErrorCode;
    }

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 从header中得到token
        String token = request.getHeader(httpHeaderName);
        if (token != null && token.length() > 0) {
            Jws<Claims> claimsJws = null;
            try {

                claimsJws = Jwts.parser().setSigningKey(AbstractTokenManager.TOKEN_KEY).parseClaimsJws(token);
                // OK, we can trust this JWT

            } catch (SignatureException e) {
                // don't trust the JWT!
                logger.warn("Wrong token: {}", token);
            }
            String key = manager.getAccount(token);
            if (claimsJws != null && key != null) {
                String userNum = claimsJws.getBody().getId();
                String userId = claimsJws.getBody().getAudience();
                String userAccount = claimsJws.getBody().getSubject();
                if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(userAccount)) {

                    // 如果token验证成功，将token对应的用户id存在request中，便于之后注入
                    request.setAttribute(REQUEST_CURRENT_USER_NUM, userNum);
                    request.setAttribute(REQUEST_CURRENT_USER_ID, userId);
                    request.setAttribute(REQUEST_CURRENT_ACCOUNT, userAccount);
                    return true;

                }
            }
        }
        // 如果验证token失败，并且方法注明了Authorization，返回401错误
        if (handlerMethod.getBeanType().getAnnotation(Authorization.class) != null   // 查看方法所在的Controller是否有注解
                || method.getAnnotation(Authorization.class) != null) { // 查看方法上是否有注解

            response.setStatus(unauthorizedErrorCode);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            writer.write("{\n" +
                    "  \"msg\": \"" + unauthorizedErrorMessage + "\",\n" +
                    "  \"ret\": 401\n" +
                    "}");
            writer.close();
            return false;
        }

        // 用户未授权测试 TODO remove
        request.setAttribute(REQUEST_CURRENT_USER_NUM, testNum);
        request.setAttribute(REQUEST_CURRENT_USER_ID, testId);
        request.setAttribute(REQUEST_CURRENT_ACCOUNT, testAccount);

        // 为了防止以恶意操作直接在REQUEST_CURRENT_USER_ID、REQUEST_CURRENT_ACCOUNT中写入数据，将其设为null
        // request.setAttribute(REQUEST_CURRENT_USER_NUM, null);
        //request.setAttribute(REQUEST_CURRENT_USER_ID, null);
        //request.setAttribute(REQUEST_CURRENT_ACCOUNT, null);
        return true;
    }
}
