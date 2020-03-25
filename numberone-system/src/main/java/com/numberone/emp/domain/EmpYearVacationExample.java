package com.numberone.emp.domain;

import java.util.ArrayList;
import java.util.List;

public class EmpYearVacationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EmpYearVacationExample() {
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

        public Criteria andYearVacationIdIsNull() {
            addCriterion("year_vacation_id is null");
            return (Criteria) this;
        }

        public Criteria andYearVacationIdIsNotNull() {
            addCriterion("year_vacation_id is not null");
            return (Criteria) this;
        }

        public Criteria andYearVacationIdEqualTo(String value) {
            addCriterion("year_vacation_id =", value, "yearVacationId");
            return (Criteria) this;
        }

        public Criteria andYearVacationIdNotEqualTo(String value) {
            addCriterion("year_vacation_id <>", value, "yearVacationId");
            return (Criteria) this;
        }

        public Criteria andYearVacationIdGreaterThan(String value) {
            addCriterion("year_vacation_id >", value, "yearVacationId");
            return (Criteria) this;
        }

        public Criteria andYearVacationIdGreaterThanOrEqualTo(String value) {
            addCriterion("year_vacation_id >=", value, "yearVacationId");
            return (Criteria) this;
        }

        public Criteria andYearVacationIdLessThan(String value) {
            addCriterion("year_vacation_id <", value, "yearVacationId");
            return (Criteria) this;
        }

        public Criteria andYearVacationIdLessThanOrEqualTo(String value) {
            addCriterion("year_vacation_id <=", value, "yearVacationId");
            return (Criteria) this;
        }

        public Criteria andYearVacationIdLike(String value) {
            addCriterion("year_vacation_id like", value, "yearVacationId");
            return (Criteria) this;
        }

        public Criteria andYearVacationIdNotLike(String value) {
            addCriterion("year_vacation_id not like", value, "yearVacationId");
            return (Criteria) this;
        }

        public Criteria andYearVacationIdIn(List<String> values) {
            addCriterion("year_vacation_id in", values, "yearVacationId");
            return (Criteria) this;
        }

        public Criteria andYearVacationIdNotIn(List<String> values) {
            addCriterion("year_vacation_id not in", values, "yearVacationId");
            return (Criteria) this;
        }

        public Criteria andYearVacationIdBetween(String value1, String value2) {
            addCriterion("year_vacation_id between", value1, value2, "yearVacationId");
            return (Criteria) this;
        }

        public Criteria andYearVacationIdNotBetween(String value1, String value2) {
            addCriterion("year_vacation_id not between", value1, value2, "yearVacationId");
            return (Criteria) this;
        }

        public Criteria andEmpIdIsNull() {
            addCriterion("emp_id is null");
            return (Criteria) this;
        }

        public Criteria andEmpIdIsNotNull() {
            addCriterion("emp_id is not null");
            return (Criteria) this;
        }

        public Criteria andEmpIdEqualTo(String value) {
            addCriterion("emp_id =", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdNotEqualTo(String value) {
            addCriterion("emp_id <>", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdGreaterThan(String value) {
            addCriterion("emp_id >", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdGreaterThanOrEqualTo(String value) {
            addCriterion("emp_id >=", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdLessThan(String value) {
            addCriterion("emp_id <", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdLessThanOrEqualTo(String value) {
            addCriterion("emp_id <=", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdLike(String value) {
            addCriterion("emp_id like", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdNotLike(String value) {
            addCriterion("emp_id not like", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdIn(List<String> values) {
            addCriterion("emp_id in", values, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdNotIn(List<String> values) {
            addCriterion("emp_id not in", values, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdBetween(String value1, String value2) {
            addCriterion("emp_id between", value1, value2, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdNotBetween(String value1, String value2) {
            addCriterion("emp_id not between", value1, value2, "empId");
            return (Criteria) this;
        }

        public Criteria andYearIsNull() {
            addCriterion("year is null");
            return (Criteria) this;
        }

        public Criteria andYearIsNotNull() {
            addCriterion("year is not null");
            return (Criteria) this;
        }

        public Criteria andYearEqualTo(Integer value) {
            addCriterion("year =", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotEqualTo(Integer value) {
            addCriterion("year <>", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThan(Integer value) {
            addCriterion("year >", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("year >=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThan(Integer value) {
            addCriterion("year <", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThanOrEqualTo(Integer value) {
            addCriterion("year <=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearIn(List<Integer> values) {
            addCriterion("year in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotIn(List<Integer> values) {
            addCriterion("year not in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearBetween(Integer value1, Integer value2) {
            addCriterion("year between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotBetween(Integer value1, Integer value2) {
            addCriterion("year not between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andTimeTotalIsNull() {
            addCriterion("time_total is null");
            return (Criteria) this;
        }

        public Criteria andTimeTotalIsNotNull() {
            addCriterion("time_total is not null");
            return (Criteria) this;
        }

        public Criteria andTimeTotalEqualTo(Double value) {
            addCriterion("time_total =", value, "timeTotal");
            return (Criteria) this;
        }

        public Criteria andTimeTotalNotEqualTo(Double value) {
            addCriterion("time_total <>", value, "timeTotal");
            return (Criteria) this;
        }

        public Criteria andTimeTotalGreaterThan(Double value) {
            addCriterion("time_total >", value, "timeTotal");
            return (Criteria) this;
        }

        public Criteria andTimeTotalGreaterThanOrEqualTo(Double value) {
            addCriterion("time_total >=", value, "timeTotal");
            return (Criteria) this;
        }

        public Criteria andTimeTotalLessThan(Double value) {
            addCriterion("time_total <", value, "timeTotal");
            return (Criteria) this;
        }

        public Criteria andTimeTotalLessThanOrEqualTo(Double value) {
            addCriterion("time_total <=", value, "timeTotal");
            return (Criteria) this;
        }

        public Criteria andTimeTotalIn(List<Double> values) {
            addCriterion("time_total in", values, "timeTotal");
            return (Criteria) this;
        }

        public Criteria andTimeTotalNotIn(List<Double> values) {
            addCriterion("time_total not in", values, "timeTotal");
            return (Criteria) this;
        }

        public Criteria andTimeTotalBetween(Double value1, Double value2) {
            addCriterion("time_total between", value1, value2, "timeTotal");
            return (Criteria) this;
        }

        public Criteria andTimeTotalNotBetween(Double value1, Double value2) {
            addCriterion("time_total not between", value1, value2, "timeTotal");
            return (Criteria) this;
        }

        public Criteria andTimeUsedIsNull() {
            addCriterion("time_used is null");
            return (Criteria) this;
        }

        public Criteria andTimeUsedIsNotNull() {
            addCriterion("time_used is not null");
            return (Criteria) this;
        }

        public Criteria andTimeUsedEqualTo(Double value) {
            addCriterion("time_used =", value, "timeUsed");
            return (Criteria) this;
        }

        public Criteria andTimeUsedNotEqualTo(Double value) {
            addCriterion("time_used <>", value, "timeUsed");
            return (Criteria) this;
        }

        public Criteria andTimeUsedGreaterThan(Double value) {
            addCriterion("time_used >", value, "timeUsed");
            return (Criteria) this;
        }

        public Criteria andTimeUsedGreaterThanOrEqualTo(Double value) {
            addCriterion("time_used >=", value, "timeUsed");
            return (Criteria) this;
        }

        public Criteria andTimeUsedLessThan(Double value) {
            addCriterion("time_used <", value, "timeUsed");
            return (Criteria) this;
        }

        public Criteria andTimeUsedLessThanOrEqualTo(Double value) {
            addCriterion("time_used <=", value, "timeUsed");
            return (Criteria) this;
        }

        public Criteria andTimeUsedIn(List<Double> values) {
            addCriterion("time_used in", values, "timeUsed");
            return (Criteria) this;
        }

        public Criteria andTimeUsedNotIn(List<Double> values) {
            addCriterion("time_used not in", values, "timeUsed");
            return (Criteria) this;
        }

        public Criteria andTimeUsedBetween(Double value1, Double value2) {
            addCriterion("time_used between", value1, value2, "timeUsed");
            return (Criteria) this;
        }

        public Criteria andTimeUsedNotBetween(Double value1, Double value2) {
            addCriterion("time_used not between", value1, value2, "timeUsed");
            return (Criteria) this;
        }

        public Criteria andTimeSurplusIsNull() {
            addCriterion("time_surplus is null");
            return (Criteria) this;
        }

        public Criteria andTimeSurplusIsNotNull() {
            addCriterion("time_surplus is not null");
            return (Criteria) this;
        }

        public Criteria andTimeSurplusEqualTo(Double value) {
            addCriterion("time_surplus =", value, "timeSurplus");
            return (Criteria) this;
        }

        public Criteria andTimeSurplusNotEqualTo(Double value) {
            addCriterion("time_surplus <>", value, "timeSurplus");
            return (Criteria) this;
        }

        public Criteria andTimeSurplusGreaterThan(Double value) {
            addCriterion("time_surplus >", value, "timeSurplus");
            return (Criteria) this;
        }

        public Criteria andTimeSurplusGreaterThanOrEqualTo(Double value) {
            addCriterion("time_surplus >=", value, "timeSurplus");
            return (Criteria) this;
        }

        public Criteria andTimeSurplusLessThan(Double value) {
            addCriterion("time_surplus <", value, "timeSurplus");
            return (Criteria) this;
        }

        public Criteria andTimeSurplusLessThanOrEqualTo(Double value) {
            addCriterion("time_surplus <=", value, "timeSurplus");
            return (Criteria) this;
        }

        public Criteria andTimeSurplusIn(List<Double> values) {
            addCriterion("time_surplus in", values, "timeSurplus");
            return (Criteria) this;
        }

        public Criteria andTimeSurplusNotIn(List<Double> values) {
            addCriterion("time_surplus not in", values, "timeSurplus");
            return (Criteria) this;
        }

        public Criteria andTimeSurplusBetween(Double value1, Double value2) {
            addCriterion("time_surplus between", value1, value2, "timeSurplus");
            return (Criteria) this;
        }

        public Criteria andTimeSurplusNotBetween(Double value1, Double value2) {
            addCriterion("time_surplus not between", value1, value2, "timeSurplus");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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