package com.numberone.emp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EmpNonworkdayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EmpNonworkdayExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andNonworkIdIsNull() {
            addCriterion("nonwork_id is null");
            return (Criteria) this;
        }

        public Criteria andNonworkIdIsNotNull() {
            addCriterion("nonwork_id is not null");
            return (Criteria) this;
        }

        public Criteria andNonworkIdEqualTo(String value) {
            addCriterion("nonwork_id =", value, "nonworkId");
            return (Criteria) this;
        }

        public Criteria andNonworkIdNotEqualTo(String value) {
            addCriterion("nonwork_id <>", value, "nonworkId");
            return (Criteria) this;
        }

        public Criteria andNonworkIdGreaterThan(String value) {
            addCriterion("nonwork_id >", value, "nonworkId");
            return (Criteria) this;
        }

        public Criteria andNonworkIdGreaterThanOrEqualTo(String value) {
            addCriterion("nonwork_id >=", value, "nonworkId");
            return (Criteria) this;
        }

        public Criteria andNonworkIdLessThan(String value) {
            addCriterion("nonwork_id <", value, "nonworkId");
            return (Criteria) this;
        }

        public Criteria andNonworkIdLessThanOrEqualTo(String value) {
            addCriterion("nonwork_id <=", value, "nonworkId");
            return (Criteria) this;
        }

        public Criteria andNonworkIdLike(String value) {
            addCriterion("nonwork_id like", value, "nonworkId");
            return (Criteria) this;
        }

        public Criteria andNonworkIdNotLike(String value) {
            addCriterion("nonwork_id not like", value, "nonworkId");
            return (Criteria) this;
        }

        public Criteria andNonworkIdIn(List<String> values) {
            addCriterion("nonwork_id in", values, "nonworkId");
            return (Criteria) this;
        }

        public Criteria andNonworkIdNotIn(List<String> values) {
            addCriterion("nonwork_id not in", values, "nonworkId");
            return (Criteria) this;
        }

        public Criteria andNonworkIdBetween(String value1, String value2) {
            addCriterion("nonwork_id between", value1, value2, "nonworkId");
            return (Criteria) this;
        }

        public Criteria andNonworkIdNotBetween(String value1, String value2) {
            addCriterion("nonwork_id not between", value1, value2, "nonworkId");
            return (Criteria) this;
        }

        public Criteria andWorkdateIsNull() {
            addCriterion("workdate is null");
            return (Criteria) this;
        }

        public Criteria andWorkdateIsNotNull() {
            addCriterion("workdate is not null");
            return (Criteria) this;
        }

        public Criteria andWorkdateEqualTo(Date value) {
            addCriterionForJDBCDate("workdate =", value, "workdate");
            return (Criteria) this;
        }

        public Criteria andWorkdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("workdate <>", value, "workdate");
            return (Criteria) this;
        }

        public Criteria andWorkdateGreaterThan(Date value) {
            addCriterionForJDBCDate("workdate >", value, "workdate");
            return (Criteria) this;
        }

        public Criteria andWorkdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("workdate >=", value, "workdate");
            return (Criteria) this;
        }

        public Criteria andWorkdateLessThan(Date value) {
            addCriterionForJDBCDate("workdate <", value, "workdate");
            return (Criteria) this;
        }

        public Criteria andWorkdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("workdate <=", value, "workdate");
            return (Criteria) this;
        }

        public Criteria andWorkdateIn(List<Date> values) {
            addCriterionForJDBCDate("workdate in", values, "workdate");
            return (Criteria) this;
        }

        public Criteria andWorkdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("workdate not in", values, "workdate");
            return (Criteria) this;
        }

        public Criteria andWorkdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("workdate between", value1, value2, "workdate");
            return (Criteria) this;
        }

        public Criteria andWorkdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("workdate not between", value1, value2, "workdate");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeIsNull() {
            addCriterion("workdate_type is null");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeIsNotNull() {
            addCriterion("workdate_type is not null");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeEqualTo(String value) {
            addCriterion("workdate_type =", value, "workdateType");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeNotEqualTo(String value) {
            addCriterion("workdate_type <>", value, "workdateType");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeGreaterThan(String value) {
            addCriterion("workdate_type >", value, "workdateType");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeGreaterThanOrEqualTo(String value) {
            addCriterion("workdate_type >=", value, "workdateType");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeLessThan(String value) {
            addCriterion("workdate_type <", value, "workdateType");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeLessThanOrEqualTo(String value) {
            addCriterion("workdate_type <=", value, "workdateType");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeLike(String value) {
            addCriterion("workdate_type like", value, "workdateType");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeNotLike(String value) {
            addCriterion("workdate_type not like", value, "workdateType");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeIn(List<String> values) {
            addCriterion("workdate_type in", values, "workdateType");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeNotIn(List<String> values) {
            addCriterion("workdate_type not in", values, "workdateType");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeBetween(String value1, String value2) {
            addCriterion("workdate_type between", value1, value2, "workdateType");
            return (Criteria) this;
        }

        public Criteria andWorkdateTypeNotBetween(String value1, String value2) {
            addCriterion("workdate_type not between", value1, value2, "workdateType");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameIsNull() {
            addCriterion("workdate_name is null");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameIsNotNull() {
            addCriterion("workdate_name is not null");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameEqualTo(String value) {
            addCriterion("workdate_name =", value, "workdateName");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameNotEqualTo(String value) {
            addCriterion("workdate_name <>", value, "workdateName");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameGreaterThan(String value) {
            addCriterion("workdate_name >", value, "workdateName");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameGreaterThanOrEqualTo(String value) {
            addCriterion("workdate_name >=", value, "workdateName");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameLessThan(String value) {
            addCriterion("workdate_name <", value, "workdateName");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameLessThanOrEqualTo(String value) {
            addCriterion("workdate_name <=", value, "workdateName");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameLike(String value) {
            addCriterion("workdate_name like", value, "workdateName");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameNotLike(String value) {
            addCriterion("workdate_name not like", value, "workdateName");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameIn(List<String> values) {
            addCriterion("workdate_name in", values, "workdateName");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameNotIn(List<String> values) {
            addCriterion("workdate_name not in", values, "workdateName");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameBetween(String value1, String value2) {
            addCriterion("workdate_name between", value1, value2, "workdateName");
            return (Criteria) this;
        }

        public Criteria andWorkdateNameNotBetween(String value1, String value2) {
            addCriterion("workdate_name not between", value1, value2, "workdateName");
            return (Criteria) this;
        }

        public Criteria andWorkdateFlagIsNull() {
            addCriterion("workdate_flag is null");
            return (Criteria) this;
        }

        public Criteria andWorkdateFlagIsNotNull() {
            addCriterion("workdate_flag is not null");
            return (Criteria) this;
        }

        public Criteria andWorkdateFlagEqualTo(Integer value) {
            addCriterion("workdate_flag =", value, "workdateFlag");
            return (Criteria) this;
        }

        public Criteria andWorkdateFlagNotEqualTo(Integer value) {
            addCriterion("workdate_flag <>", value, "workdateFlag");
            return (Criteria) this;
        }

        public Criteria andWorkdateFlagGreaterThan(Integer value) {
            addCriterion("workdate_flag >", value, "workdateFlag");
            return (Criteria) this;
        }

        public Criteria andWorkdateFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("workdate_flag >=", value, "workdateFlag");
            return (Criteria) this;
        }

        public Criteria andWorkdateFlagLessThan(Integer value) {
            addCriterion("workdate_flag <", value, "workdateFlag");
            return (Criteria) this;
        }

        public Criteria andWorkdateFlagLessThanOrEqualTo(Integer value) {
            addCriterion("workdate_flag <=", value, "workdateFlag");
            return (Criteria) this;
        }

        public Criteria andWorkdateFlagIn(List<Integer> values) {
            addCriterion("workdate_flag in", values, "workdateFlag");
            return (Criteria) this;
        }

        public Criteria andWorkdateFlagNotIn(List<Integer> values) {
            addCriterion("workdate_flag not in", values, "workdateFlag");
            return (Criteria) this;
        }

        public Criteria andWorkdateFlagBetween(Integer value1, Integer value2) {
            addCriterion("workdate_flag between", value1, value2, "workdateFlag");
            return (Criteria) this;
        }

        public Criteria andWorkdateFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("workdate_flag not between", value1, value2, "workdateFlag");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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