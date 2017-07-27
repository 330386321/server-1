package com.lawu.eshop.merchant.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/4/26
 */
@Component
public class MerchantApiConfig { 


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

    @Value(value="${merchant.qr.code}")
    private String merchantQrCode;

    @Value(value="${share.redpacket.url}")
    private String shareRedpacketUrl;

    @Value(value="${share.register.url}")
    private String shareRegisterUrl;

    @Value(value="${default_headimg}")
    private String defaultHeadimg;
    
    @Value(value="${ffmpeg.url}")
    private String ffmpegUrl;
    
    @Value(value="${inviter.merchant.url}")
    private String inviterMerchantUrl;
    
    @Value(value="${inviter.member.url}")
    private String inviterMemberUrl;

    @Value(value="${fastdfs.trackerServers}")
    private String trackerServers;
    
    @Value(value="${fastdfs.trackerTttpPport}")
    private Integer trackerTttpPport;
    
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

    public String getMerchantQrCode() {
        return merchantQrCode;
    }

    public String getShareRedpacketUrl() {
        return shareRedpacketUrl;
    }

    public String getShareRegisterUrl() {
        return shareRegisterUrl;
    }

    public String getDefaultHeadimg() {
        return defaultHeadimg;
    }

	public String getFfmpegUrl() {
		return ffmpegUrl;
	}

	public String getInviterMerchantUrl() {
		return inviterMerchantUrl;
	}

	public String getInviterMemberUrl() {
		return inviterMemberUrl;
	}

	/**
	 * @return the trackerServers
	 */
	public String getTrackerServers() {
		return trackerServers;
	}

	/**
	 * @param trackerServers the trackerServers to set
	 */
	public void setTrackerServers(String trackerServers) {
		this.trackerServers = trackerServers;
	}

	/**
	 * @return the trackerTttpPport
	 */
	public Integer getTrackerTttpPport() {
		return trackerTttpPport;
	}

	/**
	 * @param trackerTttpPport the trackerTttpPport to set
	 */
	public void setTrackerTttpPport(Integer trackerTttpPport) {
		this.trackerTttpPport = trackerTttpPport;
	}

    
}
