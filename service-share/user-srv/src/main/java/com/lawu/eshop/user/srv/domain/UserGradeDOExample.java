package com.lawu.eshop.user.srv.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserGradeDOExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_grade
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_grade
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_grade
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_grade
     *
     * @mbg.generated
     */
    public UserGradeDOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_grade
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_grade
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_grade
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_grade
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_grade
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_grade
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_grade
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_grade
     *
     * @mbg.generated
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
     * This method corresponds to the database table user_grade
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_grade
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user_grade
     *
     * @mbg.generated
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

        public Criteria andGradeNameIsNull() {
            addCriterion("grade_name is null");
            return (Criteria) this;
        }

        public Criteria andGradeNameIsNotNull() {
            addCriterion("grade_name is not null");
            return (Criteria) this;
        }

        public Criteria andGradeNameEqualTo(String value) {
            addCriterion("grade_name =", value, "gradeName");
            return (Criteria) this;
        }

        public Criteria andGradeNameNotEqualTo(String value) {
            addCriterion("grade_name <>", value, "gradeName");
            return (Criteria) this;
        }

        public Criteria andGradeNameGreaterThan(String value) {
            addCriterion("grade_name >", value, "gradeName");
            return (Criteria) this;
        }

        public Criteria andGradeNameGreaterThanOrEqualTo(String value) {
            addCriterion("grade_name >=", value, "gradeName");
            return (Criteria) this;
        }

        public Criteria andGradeNameLessThan(String value) {
            addCriterion("grade_name <", value, "gradeName");
            return (Criteria) this;
        }

        public Criteria andGradeNameLessThanOrEqualTo(String value) {
            addCriterion("grade_name <=", value, "gradeName");
            return (Criteria) this;
        }

        public Criteria andGradeNameLike(String value) {
            addCriterion("grade_name like", value, "gradeName");
            return (Criteria) this;
        }

        public Criteria andGradeNameNotLike(String value) {
            addCriterion("grade_name not like", value, "gradeName");
            return (Criteria) this;
        }

        public Criteria andGradeNameIn(List<String> values) {
            addCriterion("grade_name in", values, "gradeName");
            return (Criteria) this;
        }

        public Criteria andGradeNameNotIn(List<String> values) {
            addCriterion("grade_name not in", values, "gradeName");
            return (Criteria) this;
        }

        public Criteria andGradeNameBetween(String value1, String value2) {
            addCriterion("grade_name between", value1, value2, "gradeName");
            return (Criteria) this;
        }

        public Criteria andGradeNameNotBetween(String value1, String value2) {
            addCriterion("grade_name not between", value1, value2, "gradeName");
            return (Criteria) this;
        }

        public Criteria andGradeValueIsNull() {
            addCriterion("grade_value is null");
            return (Criteria) this;
        }

        public Criteria andGradeValueIsNotNull() {
            addCriterion("grade_value is not null");
            return (Criteria) this;
        }

        public Criteria andGradeValueEqualTo(Byte value) {
            addCriterion("grade_value =", value, "gradeValue");
            return (Criteria) this;
        }

        public Criteria andGradeValueNotEqualTo(Byte value) {
            addCriterion("grade_value <>", value, "gradeValue");
            return (Criteria) this;
        }

        public Criteria andGradeValueGreaterThan(Byte value) {
            addCriterion("grade_value >", value, "gradeValue");
            return (Criteria) this;
        }

        public Criteria andGradeValueGreaterThanOrEqualTo(Byte value) {
            addCriterion("grade_value >=", value, "gradeValue");
            return (Criteria) this;
        }

        public Criteria andGradeValueLessThan(Byte value) {
            addCriterion("grade_value <", value, "gradeValue");
            return (Criteria) this;
        }

        public Criteria andGradeValueLessThanOrEqualTo(Byte value) {
            addCriterion("grade_value <=", value, "gradeValue");
            return (Criteria) this;
        }

        public Criteria andGradeValueIn(List<Byte> values) {
            addCriterion("grade_value in", values, "gradeValue");
            return (Criteria) this;
        }

        public Criteria andGradeValueNotIn(List<Byte> values) {
            addCriterion("grade_value not in", values, "gradeValue");
            return (Criteria) this;
        }

        public Criteria andGradeValueBetween(Byte value1, Byte value2) {
            addCriterion("grade_value between", value1, value2, "gradeValue");
            return (Criteria) this;
        }

        public Criteria andGradeValueNotBetween(Byte value1, Byte value2) {
            addCriterion("grade_value not between", value1, value2, "gradeValue");
            return (Criteria) this;
        }

        public Criteria andGradeWeightIsNull() {
            addCriterion("grade_weight is null");
            return (Criteria) this;
        }

        public Criteria andGradeWeightIsNotNull() {
            addCriterion("grade_weight is not null");
            return (Criteria) this;
        }

        public Criteria andGradeWeightEqualTo(Integer value) {
            addCriterion("grade_weight =", value, "gradeWeight");
            return (Criteria) this;
        }

        public Criteria andGradeWeightNotEqualTo(Integer value) {
            addCriterion("grade_weight <>", value, "gradeWeight");
            return (Criteria) this;
        }

        public Criteria andGradeWeightGreaterThan(Integer value) {
            addCriterion("grade_weight >", value, "gradeWeight");
            return (Criteria) this;
        }

        public Criteria andGradeWeightGreaterThanOrEqualTo(Integer value) {
            addCriterion("grade_weight >=", value, "gradeWeight");
            return (Criteria) this;
        }

        public Criteria andGradeWeightLessThan(Integer value) {
            addCriterion("grade_weight <", value, "gradeWeight");
            return (Criteria) this;
        }

        public Criteria andGradeWeightLessThanOrEqualTo(Integer value) {
            addCriterion("grade_weight <=", value, "gradeWeight");
            return (Criteria) this;
        }

        public Criteria andGradeWeightIn(List<Integer> values) {
            addCriterion("grade_weight in", values, "gradeWeight");
            return (Criteria) this;
        }

        public Criteria andGradeWeightNotIn(List<Integer> values) {
            addCriterion("grade_weight not in", values, "gradeWeight");
            return (Criteria) this;
        }

        public Criteria andGradeWeightBetween(Integer value1, Integer value2) {
            addCriterion("grade_weight between", value1, value2, "gradeWeight");
            return (Criteria) this;
        }

        public Criteria andGradeWeightNotBetween(Integer value1, Integer value2) {
            addCriterion("grade_weight not between", value1, value2, "gradeWeight");
            return (Criteria) this;
        }

        public Criteria andMinGrowthValueIsNull() {
            addCriterion("min_growth_value is null");
            return (Criteria) this;
        }

        public Criteria andMinGrowthValueIsNotNull() {
            addCriterion("min_growth_value is not null");
            return (Criteria) this;
        }

        public Criteria andMinGrowthValueEqualTo(Integer value) {
            addCriterion("min_growth_value =", value, "minGrowthValue");
            return (Criteria) this;
        }

        public Criteria andMinGrowthValueNotEqualTo(Integer value) {
            addCriterion("min_growth_value <>", value, "minGrowthValue");
            return (Criteria) this;
        }

        public Criteria andMinGrowthValueGreaterThan(Integer value) {
            addCriterion("min_growth_value >", value, "minGrowthValue");
            return (Criteria) this;
        }

        public Criteria andMinGrowthValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("min_growth_value >=", value, "minGrowthValue");
            return (Criteria) this;
        }

        public Criteria andMinGrowthValueLessThan(Integer value) {
            addCriterion("min_growth_value <", value, "minGrowthValue");
            return (Criteria) this;
        }

        public Criteria andMinGrowthValueLessThanOrEqualTo(Integer value) {
            addCriterion("min_growth_value <=", value, "minGrowthValue");
            return (Criteria) this;
        }

        public Criteria andMinGrowthValueIn(List<Integer> values) {
            addCriterion("min_growth_value in", values, "minGrowthValue");
            return (Criteria) this;
        }

        public Criteria andMinGrowthValueNotIn(List<Integer> values) {
            addCriterion("min_growth_value not in", values, "minGrowthValue");
            return (Criteria) this;
        }

        public Criteria andMinGrowthValueBetween(Integer value1, Integer value2) {
            addCriterion("min_growth_value between", value1, value2, "minGrowthValue");
            return (Criteria) this;
        }

        public Criteria andMinGrowthValueNotBetween(Integer value1, Integer value2) {
            addCriterion("min_growth_value not between", value1, value2, "minGrowthValue");
            return (Criteria) this;
        }

        public Criteria andLotteryActivityPointIsNull() {
            addCriterion("lottery_activity_point is null");
            return (Criteria) this;
        }

        public Criteria andLotteryActivityPointIsNotNull() {
            addCriterion("lottery_activity_point is not null");
            return (Criteria) this;
        }

        public Criteria andLotteryActivityPointEqualTo(Integer value) {
            addCriterion("lottery_activity_point =", value, "lotteryActivityPoint");
            return (Criteria) this;
        }

        public Criteria andLotteryActivityPointNotEqualTo(Integer value) {
            addCriterion("lottery_activity_point <>", value, "lotteryActivityPoint");
            return (Criteria) this;
        }

        public Criteria andLotteryActivityPointGreaterThan(Integer value) {
            addCriterion("lottery_activity_point >", value, "lotteryActivityPoint");
            return (Criteria) this;
        }

        public Criteria andLotteryActivityPointGreaterThanOrEqualTo(Integer value) {
            addCriterion("lottery_activity_point >=", value, "lotteryActivityPoint");
            return (Criteria) this;
        }

        public Criteria andLotteryActivityPointLessThan(Integer value) {
            addCriterion("lottery_activity_point <", value, "lotteryActivityPoint");
            return (Criteria) this;
        }

        public Criteria andLotteryActivityPointLessThanOrEqualTo(Integer value) {
            addCriterion("lottery_activity_point <=", value, "lotteryActivityPoint");
            return (Criteria) this;
        }

        public Criteria andLotteryActivityPointIn(List<Integer> values) {
            addCriterion("lottery_activity_point in", values, "lotteryActivityPoint");
            return (Criteria) this;
        }

        public Criteria andLotteryActivityPointNotIn(List<Integer> values) {
            addCriterion("lottery_activity_point not in", values, "lotteryActivityPoint");
            return (Criteria) this;
        }

        public Criteria andLotteryActivityPointBetween(Integer value1, Integer value2) {
            addCriterion("lottery_activity_point between", value1, value2, "lotteryActivityPoint");
            return (Criteria) this;
        }

        public Criteria andLotteryActivityPointNotBetween(Integer value1, Integer value2) {
            addCriterion("lottery_activity_point not between", value1, value2, "lotteryActivityPoint");
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
     * This class corresponds to the database table user_grade
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user_grade
     *
     * @mbg.generated
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