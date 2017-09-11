package com.lawu.eshop.cache.srv.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.lawu.eshop.cache.srv.constants.KeyConstant;
import com.lawu.eshop.cache.srv.service.UserVisitService;
import com.lawu.eshop.framework.core.type.UserType;

/**
 * @author zhangyong
 * @date 2017/7/3.
 */
@Service
public class UserVisitServiceImpl implements UserVisitService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void addUserVisitCount(String userNum, String nowTimeStr, Long userId,UserType type) {
        String time = nowTimeStr.concat("_");
        String suffix = "";
        if(UserType.MEMBER.equals(type)){
            //用户
            suffix = KeyConstant.REDIS_KEY_USER_VISIT_COUNT.concat(time);
        }else{
            suffix = KeyConstant.REDIS_KEY_MERCHANT_VISIT_COUNT.concat(time);
        }
        Long currPage = userId / 100 + 1;
        String keySuffix = suffix.concat(String.valueOf(currPage)+"_");
        String key = keySuffix.concat(userNum);
        String oldVal = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(oldVal)) {
            //如果不存在放1
            stringRedisTemplate.opsForValue().set(key, "1");
        } else {
            stringRedisTemplate.boundValueOps(key).increment(1);//val +1
        }
    }

    @Override
    public Map<String, String> getVisitRecords(Integer currentPage,String time,Byte type) {
        String suffix ="";
        if(UserType.MEMBER.val.byteValue() == type){
            suffix = KeyConstant.REDIS_KEY_USER_VISIT_COUNT.concat(time+"_");
        }else{
            suffix = KeyConstant.REDIS_KEY_MERCHANT_VISIT_COUNT.concat(time+"_");
        }

        String pattern = suffix.concat(currentPage.toString()+"*");
        //匹配对应的keys
        Set<String> keys =  redisTemplate.keys(pattern);
        Iterator<String> it = keys.iterator();
        Map<String,String > map = new HashMap<>();
        while (it.hasNext()) {
            String key =  it.next();
            String val = stringRedisTemplate.opsForValue().get(key);
            map.put(key,val);
        }
        return map;
    }

    @Override
    public void delVisitRecords(String time) {
        //用户
        String memberPattern  = KeyConstant.REDIS_KEY_USER_VISIT_COUNT.concat(time+"*");

        String merchantPattern  = KeyConstant.REDIS_KEY_MERCHANT_VISIT_COUNT.concat(time+"*");

        Set<String> memberKeys =  redisTemplate.keys(memberPattern);
        redisTemplate.delete(memberKeys);

        Set<String> merchantKeys =  redisTemplate.keys(merchantPattern);
        redisTemplate.delete(merchantKeys);


    }

    @Override
    public void addUserVisitTime(Long userId, UserType type) {
        String key = KeyConstant.REDIS_KEY_MEMBER_VISIT_TIME + userId;
        if (UserType.MERCHANT.equals(type)) {
            key = KeyConstant.REDIS_KEY_MERCHANT_VISIT_TIME + userId;
        }
        stringRedisTemplate.opsForValue().set(key, String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void addUserVisitFrequency(Long userId, UserType type, Integer expireTime) {
        String key = KeyConstant.REDIS_KEY_MEMBER_VISIT_FREQUENCY + userId;
        if (UserType.MERCHANT.equals(type)) {
            key = KeyConstant.REDIS_KEY_MERCHANT_VISIT_FREQUENCY + userId;
        }
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(value)) {
            stringRedisTemplate.opsForValue().set(key, "1", expireTime, TimeUnit.MINUTES);
        } else {
            stringRedisTemplate.boundValueOps(key).increment(1);
        }
    }

    @Override
    public Long getUserVisitTime(Long userId, UserType type) {
        String key = KeyConstant.REDIS_KEY_MEMBER_VISIT_TIME + userId;
        if (UserType.MERCHANT.equals(type)) {
            key = KeyConstant.REDIS_KEY_MERCHANT_VISIT_TIME + userId;
        }
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(value)) {
            return 0L;
        }
        return Long.valueOf(value);
    }

    @Override
    public Integer getUserVisitFrequency(Long userId, UserType type) {
        String key = KeyConstant.REDIS_KEY_MEMBER_VISIT_FREQUENCY + userId;
        if (UserType.MERCHANT.equals(type)) {
            key = KeyConstant.REDIS_KEY_MERCHANT_VISIT_FREQUENCY + userId;
        }
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(value)) {
            return 0;
        }
        return Integer.valueOf(value);
    }

    @Override
    public void delUserVisitFrequency(Long userId, UserType type) {
        String key = KeyConstant.REDIS_KEY_MEMBER_VISIT_FREQUENCY + userId;
        if (UserType.MERCHANT.equals(type)) {
            key = KeyConstant.REDIS_KEY_MERCHANT_VISIT_FREQUENCY + userId;
        }
        stringRedisTemplate.delete(key);
    }

}
