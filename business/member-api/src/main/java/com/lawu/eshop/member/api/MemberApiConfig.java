package com.lawu.eshop.member.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/4/26
 */
@Component
public class MemberApiConfig {


    @Value(value="${image.url}")
    private String imageUrl;
    @Value(value="${video.url}")
    private String videoUrl;

    @Value(value="${image.upload.url}")
    private String imageUploadUrl;

    @Value(value="${video.upload.url}")
    private String videoUploadUrl;

    @Value(value="${sms.valid.minutes}")
    private Integer smsValidMinutes;

    @Value(value="${default_headimg}")
    private String defaultHeadimg;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getImageUploadUrl() {
        return imageUploadUrl;
    }

    public String getVideoUploadUrl() {
        return videoUploadUrl;
    }

    public Integer getSmsValidMinutes() {
        return smsValidMinutes;
    }

    public String getDefaultHeadimg() {
        return defaultHeadimg;
    }
}
