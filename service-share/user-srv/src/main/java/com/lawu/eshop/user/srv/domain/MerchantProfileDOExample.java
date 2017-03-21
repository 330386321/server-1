package com.lawu.eshop.user.srv.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MerchantProfileDOExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    public MerchantProfileDOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andInviteMemberCountIsNull() {
            addCriterion("invite_member_count is null");
            return (Criteria) this;
        }

        public Criteria andInviteMemberCountIsNotNull() {
            addCriterion("invite_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andInviteMemberCountEqualTo(Integer value) {
            addCriterion("invite_member_count =", value, "inviteMemberCount");
            return (Criteria) this;
        }

        public Criteria andInviteMemberCountNotEqualTo(Integer value) {
            addCriterion("invite_member_count <>", value, "inviteMemberCount");
            return (Criteria) this;
        }

        public Criteria andInviteMemberCountGreaterThan(Integer value) {
            addCriterion("invite_member_count >", value, "inviteMemberCount");
            return (Criteria) this;
        }

        public Criteria andInviteMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_member_count >=", value, "inviteMemberCount");
            return (Criteria) this;
        }

        public Criteria andInviteMemberCountLessThan(Integer value) {
            addCriterion("invite_member_count <", value, "inviteMemberCount");
            return (Criteria) this;
        }

        public Criteria andInviteMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("invite_member_count <=", value, "inviteMemberCount");
            return (Criteria) this;
        }

        public Criteria andInviteMemberCountIn(List<Integer> values) {
            addCriterion("invite_member_count in", values, "inviteMemberCount");
            return (Criteria) this;
        }

        public Criteria andInviteMemberCountNotIn(List<Integer> values) {
            addCriterion("invite_member_count not in", values, "inviteMemberCount");
            return (Criteria) this;
        }

        public Criteria andInviteMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("invite_member_count between", value1, value2, "inviteMemberCount");
            return (Criteria) this;
        }

        public Criteria andInviteMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_member_count not between", value1, value2, "inviteMemberCount");
            return (Criteria) this;
        }

        public Criteria andInviteMerchantCountIsNull() {
            addCriterion("invite_merchant_count is null");
            return (Criteria) this;
        }

        public Criteria andInviteMerchantCountIsNotNull() {
            addCriterion("invite_merchant_count is not null");
            return (Criteria) this;
        }

        public Criteria andInviteMerchantCountEqualTo(Integer value) {
            addCriterion("invite_merchant_count =", value, "inviteMerchantCount");
            return (Criteria) this;
        }

        public Criteria andInviteMerchantCountNotEqualTo(Integer value) {
            addCriterion("invite_merchant_count <>", value, "inviteMerchantCount");
            return (Criteria) this;
        }

        public Criteria andInviteMerchantCountGreaterThan(Integer value) {
            addCriterion("invite_merchant_count >", value, "inviteMerchantCount");
            return (Criteria) this;
        }

        public Criteria andInviteMerchantCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_merchant_count >=", value, "inviteMerchantCount");
            return (Criteria) this;
        }

        public Criteria andInviteMerchantCountLessThan(Integer value) {
            addCriterion("invite_merchant_count <", value, "inviteMerchantCount");
            return (Criteria) this;
        }

        public Criteria andInviteMerchantCountLessThanOrEqualTo(Integer value) {
            addCriterion("invite_merchant_count <=", value, "inviteMerchantCount");
            return (Criteria) this;
        }

        public Criteria andInviteMerchantCountIn(List<Integer> values) {
            addCriterion("invite_merchant_count in", values, "inviteMerchantCount");
            return (Criteria) this;
        }

        public Criteria andInviteMerchantCountNotIn(List<Integer> values) {
            addCriterion("invite_merchant_count not in", values, "inviteMerchantCount");
            return (Criteria) this;
        }

        public Criteria andInviteMerchantCountBetween(Integer value1, Integer value2) {
            addCriterion("invite_merchant_count between", value1, value2, "inviteMerchantCount");
            return (Criteria) this;
        }

        public Criteria andInviteMerchantCountNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_merchant_count not between", value1, value2, "inviteMerchantCount");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlIsNull() {
            addCriterion("website_url is null");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlIsNotNull() {
            addCriterion("website_url is not null");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlEqualTo(String value) {
            addCriterion("website_url =", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlNotEqualTo(String value) {
            addCriterion("website_url <>", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlGreaterThan(String value) {
            addCriterion("website_url >", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlGreaterThanOrEqualTo(String value) {
            addCriterion("website_url >=", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlLessThan(String value) {
            addCriterion("website_url <", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlLessThanOrEqualTo(String value) {
            addCriterion("website_url <=", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlLike(String value) {
            addCriterion("website_url like", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlNotLike(String value) {
            addCriterion("website_url not like", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlIn(List<String> values) {
            addCriterion("website_url in", values, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlNotIn(List<String> values) {
            addCriterion("website_url not in", values, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlBetween(String value1, String value2) {
            addCriterion("website_url between", value1, value2, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlNotBetween(String value1, String value2) {
            addCriterion("website_url not between", value1, value2, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlIsNull() {
            addCriterion("taobao_url is null");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlIsNotNull() {
            addCriterion("taobao_url is not null");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlEqualTo(String value) {
            addCriterion("taobao_url =", value, "taobaoUrl");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlNotEqualTo(String value) {
            addCriterion("taobao_url <>", value, "taobaoUrl");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlGreaterThan(String value) {
            addCriterion("taobao_url >", value, "taobaoUrl");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlGreaterThanOrEqualTo(String value) {
            addCriterion("taobao_url >=", value, "taobaoUrl");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlLessThan(String value) {
            addCriterion("taobao_url <", value, "taobaoUrl");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlLessThanOrEqualTo(String value) {
            addCriterion("taobao_url <=", value, "taobaoUrl");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlLike(String value) {
            addCriterion("taobao_url like", value, "taobaoUrl");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlNotLike(String value) {
            addCriterion("taobao_url not like", value, "taobaoUrl");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlIn(List<String> values) {
            addCriterion("taobao_url in", values, "taobaoUrl");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlNotIn(List<String> values) {
            addCriterion("taobao_url not in", values, "taobaoUrl");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlBetween(String value1, String value2) {
            addCriterion("taobao_url between", value1, value2, "taobaoUrl");
            return (Criteria) this;
        }

        public Criteria andTaobaoUrlNotBetween(String value1, String value2) {
            addCriterion("taobao_url not between", value1, value2, "taobaoUrl");
            return (Criteria) this;
        }

        public Criteria andTmallUrlIsNull() {
            addCriterion("tmall_url is null");
            return (Criteria) this;
        }

        public Criteria andTmallUrlIsNotNull() {
            addCriterion("tmall_url is not null");
            return (Criteria) this;
        }

        public Criteria andTmallUrlEqualTo(String value) {
            addCriterion("tmall_url =", value, "tmallUrl");
            return (Criteria) this;
        }

        public Criteria andTmallUrlNotEqualTo(String value) {
            addCriterion("tmall_url <>", value, "tmallUrl");
            return (Criteria) this;
        }

        public Criteria andTmallUrlGreaterThan(String value) {
            addCriterion("tmall_url >", value, "tmallUrl");
            return (Criteria) this;
        }

        public Criteria andTmallUrlGreaterThanOrEqualTo(String value) {
            addCriterion("tmall_url >=", value, "tmallUrl");
            return (Criteria) this;
        }

        public Criteria andTmallUrlLessThan(String value) {
            addCriterion("tmall_url <", value, "tmallUrl");
            return (Criteria) this;
        }

        public Criteria andTmallUrlLessThanOrEqualTo(String value) {
            addCriterion("tmall_url <=", value, "tmallUrl");
            return (Criteria) this;
        }

        public Criteria andTmallUrlLike(String value) {
            addCriterion("tmall_url like", value, "tmallUrl");
            return (Criteria) this;
        }

        public Criteria andTmallUrlNotLike(String value) {
            addCriterion("tmall_url not like", value, "tmallUrl");
            return (Criteria) this;
        }

        public Criteria andTmallUrlIn(List<String> values) {
            addCriterion("tmall_url in", values, "tmallUrl");
            return (Criteria) this;
        }

        public Criteria andTmallUrlNotIn(List<String> values) {
            addCriterion("tmall_url not in", values, "tmallUrl");
            return (Criteria) this;
        }

        public Criteria andTmallUrlBetween(String value1, String value2) {
            addCriterion("tmall_url between", value1, value2, "tmallUrl");
            return (Criteria) this;
        }

        public Criteria andTmallUrlNotBetween(String value1, String value2) {
            addCriterion("tmall_url not between", value1, value2, "tmallUrl");
            return (Criteria) this;
        }

        public Criteria andJdUrlIsNull() {
            addCriterion("jd_url is null");
            return (Criteria) this;
        }

        public Criteria andJdUrlIsNotNull() {
            addCriterion("jd_url is not null");
            return (Criteria) this;
        }

        public Criteria andJdUrlEqualTo(String value) {
            addCriterion("jd_url =", value, "jdUrl");
            return (Criteria) this;
        }

        public Criteria andJdUrlNotEqualTo(String value) {
            addCriterion("jd_url <>", value, "jdUrl");
            return (Criteria) this;
        }

        public Criteria andJdUrlGreaterThan(String value) {
            addCriterion("jd_url >", value, "jdUrl");
            return (Criteria) this;
        }

        public Criteria andJdUrlGreaterThanOrEqualTo(String value) {
            addCriterion("jd_url >=", value, "jdUrl");
            return (Criteria) this;
        }

        public Criteria andJdUrlLessThan(String value) {
            addCriterion("jd_url <", value, "jdUrl");
            return (Criteria) this;
        }

        public Criteria andJdUrlLessThanOrEqualTo(String value) {
            addCriterion("jd_url <=", value, "jdUrl");
            return (Criteria) this;
        }

        public Criteria andJdUrlLike(String value) {
            addCriterion("jd_url like", value, "jdUrl");
            return (Criteria) this;
        }

        public Criteria andJdUrlNotLike(String value) {
            addCriterion("jd_url not like", value, "jdUrl");
            return (Criteria) this;
        }

        public Criteria andJdUrlIn(List<String> values) {
            addCriterion("jd_url in", values, "jdUrl");
            return (Criteria) this;
        }

        public Criteria andJdUrlNotIn(List<String> values) {
            addCriterion("jd_url not in", values, "jdUrl");
            return (Criteria) this;
        }

        public Criteria andJdUrlBetween(String value1, String value2) {
            addCriterion("jd_url between", value1, value2, "jdUrl");
            return (Criteria) this;
        }

        public Criteria andJdUrlNotBetween(String value1, String value2) {
            addCriterion("jd_url not between", value1, value2, "jdUrl");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table merchant_profile
     *
     * @mbg.generated do_not_delete_during_merge 2017-03-22 02:28:01
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}