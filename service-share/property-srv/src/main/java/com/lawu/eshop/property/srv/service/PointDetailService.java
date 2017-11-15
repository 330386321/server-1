package com.lawu.eshop.property.srv.service;

import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.property.param.BalancePayValidateDataParam;
import com.lawu.eshop.property.param.CheckRepeatOfPropertyOperationParam;
import com.lawu.eshop.property.param.PointDetailQueryParam;
import com.lawu.eshop.property.param.PointDetailReportParam;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.param.PropertyInfoDataQueryPointDetailParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForBackageParam;
import com.lawu.eshop.property.srv.bo.AreaPointConsumeBO;
import com.lawu.eshop.property.srv.bo.IncomeMsgBO;
import com.lawu.eshop.property.srv.bo.PointConsumeReportBO;
import com.lawu.eshop.property.srv.bo.PointDetailBO;
import com.lawu.eshop.property.srv.bo.ReportAdPointGroupByAreaBO;

/**
 * 积分明细服务接口
 *
 * @author Sunny
 * @date 2017/3/30
 */
public interface PointDetailService {

    /**
     * 根据用户编号、查询参数分页查询积分明细
     *
     * @param userNo                      用户编号
     * @param transactionDetailQueryParam 查询参数
     * @return
     */
    Page<PointDetailBO> findPageByUserNum(String userNo, PointDetailQueryParam transactionDetailQueryParam);

    /**
     * 保存积分明细记录
     *
     * @param param
     * @return
     */
    int save(PointDetailSaveDataParam param);

    /**
     * 查询运营后台充值积分记录
     *
     * @param param
     * @return
     */
    Page<PointDetailBO> getBackagePointPageList(TransactionDetailQueryForBackageParam param);

    /**
     * @param param
     * @return
     * @author yangqh
     * @date 2017年6月30日 下午2:34:48
     */
    PointConsumeReportBO selectPointDetailListByDateAndDirection(PointDetailReportParam param);

    /**
     * @param param
     * @return
     * @author yangqh
     * @date 2017年6月30日 下午2:47:59
     */
    PointConsumeReportBO selectPointDetailListByDateAndDirectionAndPointType(PointDetailReportParam param);

    /**
     * 获取区域分组统计出来的发广告积分明细数据
     *
     * @param bdate
     * @param edate
     * @return
     */
    List<ReportAdPointGroupByAreaBO> getReportAdPointGroupByArea(String bdate, String edate);

    /**
     * 获取区域积分消费统计数据
     *
     * @param bdate
     * @param edate
     * @return
     */
    List<AreaPointConsumeBO> getAreaPointConsume(String bdate, String edate);

    /**
     * 获取区域积分退还统计数据
     *
     * @param bdate
     * @param edate
     * @return
     */
    List<AreaPointConsumeBO> getAreaPointRefund(String bdate, String edate);

    List<IncomeMsgBO> getIncomeMsgDataList(String begin, String end,int offset,int pageSize);

    /**
     * 根据用户编号、业务ID、类型查询记录
     *
     * @param param
     * @return
     */
    boolean getPointDetailByUserNumAndBizIdAndType(PropertyInfoDataQueryPointDetailParam param);

    /**
     * 扣除积分操作，根据用户编号、交易类型、业务ID校验是否重复扣除
     *
     * @param validateParam
     * @return
     */
    boolean verifyRepeatByUserNumAndTransactionTypeAndBizId(CheckRepeatOfPropertyOperationParam validateParam);
}
