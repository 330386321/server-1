package com.lawu.eshop.order.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.order.constants.EvaluationEnum;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
public class PayOrderListParam extends AbstractPageParam {
    @ApiModelProperty(value = "UN_EVALUATION：未评，EVALUATION_SUCCESS：已评 不传全部")
    private EvaluationEnum evaluationEnum;

    public EvaluationEnum getEvaluationEnum() {
        return evaluationEnum;
    }

    public void setEvaluationEnum(EvaluationEnum evaluationEnum) {
        this.evaluationEnum = evaluationEnum;
    }
}
