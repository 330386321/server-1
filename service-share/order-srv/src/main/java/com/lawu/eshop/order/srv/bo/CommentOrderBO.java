package com.lawu.eshop.order.srv.bo;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
public class CommentOrderBO {

    private Long id;

    private Boolean isEvaluation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEvaluation() {
        return isEvaluation;
    }

    public void setEvaluation(Boolean evaluation) {
        isEvaluation = evaluation;
    }
}
