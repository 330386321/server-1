package com.lawu.eshop.member.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/4/26
 */
@Component
public class MemberApiConfig {

	@Value(value = "${image.url}")
	private String imageUrl;
	@Value(value = "${video.url}")
	private String videoUrl;

	@Value(value = "${image.upload.url}")
	private String imageUploadUrl;

	@Value(value = "${video.upload.url}")
	private String videoUploadUrl;

	@Value(value = "${sms.valid.minutes}")
	private Integer smsValidMinutes;

	@Value(value = "${default_headimg}")
	private String defaultHeadimg;

	@Value(value = "${click.praise.ad.times}")
	private Integer clickPraiseAdTimes;

	@Value(value = "${click.praise.prob}")
	private Integer clickPraiseProb;

	@Value(value = "${click.praise.sum.prob}")
	private Integer clickPraiseSumProb;

	@Value(value = "${inviter.merchant.url}")
	private String inviterMerchantUrl;

	@Value(value = "${inviter.member.url}")
	private String inviterMemberUrl;

	@Value(value = "${fastdfs.trackerServers}")
	private String trackerServers;

	@Value(value = "${fastdfs.trackerTttpPport}")
	private Integer trackerTttpPport;

	@Value(value = "${member.qr.code}")
	private String memberQrCode;

	@Value(value = "${member.share.redpacker.url}")
	private String memberShareRedpackerUrl;

	@Value(value = "${member.share.over.url}")
	private String memberShareOverUrl;

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

	public Integer getClickPraiseAdTimes() {
		return clickPraiseAdTimes;
	}

	public void setClickPraiseAdTimes(Integer clickPraiseAdTimes) {
		this.clickPraiseAdTimes = clickPraiseAdTimes;
	}

	public Integer getClickPraiseProb() {
		return clickPraiseProb;
	}

	public void setClickPraiseProb(Integer clickPraiseProb) {
		this.clickPraiseProb = clickPraiseProb;
	}

	public Integer getClickPraiseSumProb() {
		return clickPraiseSumProb;
	}

	public void setClickPraiseSumProb(Integer clickPraiseSumProb) {
		this.clickPraiseSumProb = clickPraiseSumProb;
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
	 * @param trackerServers
	 *            the trackerServers to set
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
	 * @param trackerTttpPport
	 *            the trackerTttpPport to set
	 */
	public void setTrackerTttpPport(Integer trackerTttpPport) {
		this.trackerTttpPport = trackerTttpPport;
	}

	/**
	 * @return the memberQrCode
	 */
	public String getMemberQrCode() {
		return memberQrCode;
	}

	/**
	 * @param memberQrCode the memberQrCode to set
	 */
	public void setMemberQrCode(String memberQrCode) {
		this.memberQrCode = memberQrCode;
	}

	/**
	 * @return the memberShareRedpackerUrl
	 */
	public String getMemberShareRedpackerUrl() {
		return memberShareRedpackerUrl;
	}

	/**
	 * @param memberShareRedpackerUrl the memberShareRedpackerUrl to set
	 */
	public void setMemberShareRedpackerUrl(String memberShareRedpackerUrl) {
		this.memberShareRedpackerUrl = memberShareRedpackerUrl;
	}

	/**
	 * @return the memberShareOverUrl
	 */
	public String getMemberShareOverUrl() {
		return memberShareOverUrl;
	}

	/**
	 * @param memberShareOverUrl the memberShareOverUrl to set
	 */
	public void setMemberShareOverUrl(String memberShareOverUrl) {
		this.memberShareOverUrl = memberShareOverUrl;
	}



}
