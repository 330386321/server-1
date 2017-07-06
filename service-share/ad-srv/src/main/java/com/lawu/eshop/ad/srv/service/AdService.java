package com.lawu.eshop.ad.srv.service;

import java.math.BigDecimal;
import java.util.List;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AuditEnum;
import com.lawu.eshop.ad.param.AdFindParam;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.ad.param.AdSaveParam;
import com.lawu.eshop.ad.param.ListAdParam;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.bo.AdDetailBO;
import com.lawu.eshop.ad.srv.bo.ClickAdPointBO;
import com.lawu.eshop.ad.srv.bo.RedPacketInfoBO;
import com.lawu.eshop.ad.srv.bo.ViewBO;
import com.lawu.eshop.framework.core.page.Page;

/**
 * E赚接口
 *
 * @author zhangrc
 * @date 2017/4/6
 */
public interface AdService {

    /**
     * 商家发布E赚
     *
     * @param adSaveParam
     * @return
     */
    Integer saveAd(AdSaveParam adSaveParam);

    /**
     * 商家E赚查询
     *
     * @param adMerchantParam
     * @param merchantId
     * @return
     */
    Page<AdBO> selectListByMerchant(AdMerchantParam adMerchantParam, Long merchantId);

    /**
     * 运营平台(商家)对E赚的管理(下架)
     *
     * @param id
     * @return
     */
    Integer updateStatus(Long id);

    /**
     * 运营平台(商家)对E赚的管理(删除)
     *
     * @param id
     * @return
     */
    Integer remove(Long id);

    /**
     * 审核视频广告
     *
     * @param id
     * @param auditorId
     * @param remark
     * @param auditEnum
     * @return
     */
    Integer auditVideo(Long id, Integer auditorId, String remark, AuditEnum auditEnum);

    /**
     * 运营平台对E赚的查询
     *
     * @param adPlatParam
     * @return
     */
    Page<AdBO> selectListByPlatForm(AdFindParam adPlatParam);

    /**
     * 会员对E赚的观看
     *
     * @param adMemberParam
     * @param memberId
     * @return
     */
    Page<AdBO> selectListByMember(AdMemberParam adMemberParam, Long memberId);

    /**
     * 查看广告详情
     *
     * @param id
     * @return
     */
    AdBO selectAbById(Long id, Long memberId);


    /**
     * 会员对E赚的观看
     *
     * @param adPraiseParam
     * @return
     */
    Page<AdBO> selectPraiseListByMember(AdPraiseParam adPraiseParam,Long memberId);

    /**
     * 抢赞
     *
     * @param id
     * @param memberId
     * @return
     */
    BigDecimal clickPraise(Long id, Long memberId, String num);

    /**
     * 点击广告
     *
     * @param id
     * @param memberId
     * @return
     */
    BigDecimal clickAd(Long id, Long memberId, String num);



    /**
     * 今日精选
     *
     * @param adMemberParam
     * @return
     */
    Page<AdBO> selectChoiceness(AdMemberParam adMemberParam);

    /**
     * 判断用户是否发送过红包
     *
     * @param merchantId
     * @return
     */
    Integer selectRPIsSend(Long merchantId);

    /**
     * 领取红包
     *
     * @param memberId
     * @return
     */
    BigDecimal getRedPacket(Long merchantId, Long memberId, String memberNum);

    /**
     * 广告信息获取
     *
     * @param id
     * @return
     */
    AdBO get(Long id);

    /**
     * 根据用户查询是否已经抢到红包
     *
     * @param memberId
     * @return
     */
    Boolean selectRedPacketByMember(Long merchantId, Long memberId);

    /**
     * 点击广告获取的积分
     *
     * @param memberId
     * @return
     */
    ClickAdPointBO getClickAdPoint(Long memberId, BigDecimal point);

    /**
     * 获取广告
     */
    List<ViewBO> getAllAd();

    /**
     * 修改广告浏览次数
     *
     * @param id
     */
    void updateViewCount(Long id, Integer count);

    /**
     * 运营后天查询广告列表
     *
     * @param listAdParam
     * @return
     */
    Page<AdBO> listAllAd(ListAdParam listAdParam);

    /**
     * 商家端详情
     *
     * @param id
     * @return
     */
    AdBO selectById(Long id);

    /**
     * 运营后台操作广告(下架、删除)
     *
     * @param id
     * @param adStatusEnum
     */
    void operatorUpdateAdStatus(Long id, AdStatusEnum adStatusEnum);

    /**
     * 查询上架中的平面视频广告
     *
     * @param listAdParam
     * @return
     */
    List<AdBO> listFlatVideoAd(ListAdParam listAdParam);

    /**
     * 更新平面视频广告索引
     *
     * @param id
     */
    void updateAdIndex(Long id);

    /**
     * 重建平面视频广告索引
     */
    void rebuildAdIndex();

    /**
     * 删除无效的平面视频广告索引
     */
    void delInvalidAdIndex();
    
    /**
     * 根据商家id 获取红包的相关信息
     * @param merchantId
     * @return
     */
    RedPacketInfoBO getRedPacketInfo(Long merchantId);
    
	/**
	 * 判断商家红包是否领完
	 * @param merchantId
	 * @return
	 */
	Boolean isExistsRedPacket(Long merchantId);
	
	/**
	 * 判断商家是否发过红包
	 * @param merchantId
	 * @return
	 */
	Boolean isSendRedPacket(Long merchantId);

	/**
	 * 定时任务修改广告上架
	 */
	void updatAdToPutting();

	/**
	 * 定时任务修改结束
	 */
	void updatAdToPuted();
	
	/**
	 * 商家端广告批量删除
	 * @param adIds
	 */
	void batchDeleteAd(List<Long> adIds);
	
	/**
	 * 商家广告详情
	 * @param id
	 * @return
	 */
	AdDetailBO selectDetail(Long id);
	
	/**
	 * 判断是否是当前用户的数据
	 * @param id
	 * @param merchantId
	 * @return
	 */
	Boolean isMyData(Long id ,Long merchantId);

}
