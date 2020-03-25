package com.numberone.emp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EmpAttendinfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EmpAttendinfoExample() {
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

        public Criteria andAttendinfoIdIsNull() {
            addCriterion("attendinfo_id is null");
            return (Criteria) this;
        }

        public Criteria andAttendinfoIdIsNotNull() {
            addCriterion("attendinfo_id is not null");
            return (Criteria) this;
        }

        public Criteria andAttendinfoIdEqualTo(String value) {
            addCriterion("attendinfo_id =", value, "attendinfoId");
            return (Criteria) this;
        }

        public Criteria andAttendinfoIdNotEqualTo(String value) {
            addCriterion("attendinfo_id <>", value, "attendinfoId");
            return (Criteria) this;
        }

        public Criteria andAttendinfoIdGreaterThan(String value) {
            addCriterion("attendinfo_id >", value, "attendinfoId");
            return (Criteria) this;
        }

        public Criteria andAttendinfoIdGreaterThanOrEqualTo(String value) {
            addCriterion("attendinfo_id >=", value, "attendinfoId");
            return (Criteria) this;
        }

        public Criteria andAttendinfoIdLessThan(String value) {
            addCriterion("attendinfo_id <", value, "attendinfoId");
            return (Criteria) this;
        }

        public Criteria andAttendinfoIdLessThanOrEqualTo(String value) {
            addCriterion("attendinfo_id <=", value, "attendinfoId");
            return (Criteria) this;
        }

        public Criteria andAttendinfoIdLike(String value) {
            addCriterion("attendinfo_id like", value, "attendinfoId");
            return (Criteria) this;
        }

        public Criteria andAttendinfoIdNotLike(String value) {
            addCriterion("attendinfo_id not like", value, "attendinfoId");
            return (Criteria) this;
        }

        public Criteria andAttendinfoIdIn(List<String> values) {
            addCriterion("attendinfo_id in", values, "attendinfoId");
            return (Criteria) this;
        }

        public Criteria andAttendinfoIdNotIn(List<String> values) {
            addCriterion("attendinfo_id not in", values, "attendinfoId");
            return (Criteria) this;
        }

        public Criteria andAttendinfoIdBetween(String value1, String value2) {
            addCriterion("attendinfo_id between", value1, value2, "attendinfoId");
            return (Criteria) this;
        }

        public Criteria andAttendinfoIdNotBetween(String value1, String value2) {
            addCriterion("attendinfo_id not between", value1, value2, "attendinfoId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
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

        public Criteria andAttendDateIsNull() {
            addCriterion("attend_date is null");
            return (Criteria) this;
        }

        public Criteria andAttendDateIsNotNull() {
            addCriterion("attend_date is not null");
            return (Criteria) this;
        }

        public Criteria andAttendDateEqualTo(Date value) {
            addCriterionForJDBCDate("attend_date =", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("attend_date <>", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateGreaterThan(Date value) {
            addCriterionForJDBCDate("attend_date >", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("attend_date >=", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateLessThan(Date value) {
            addCriterionForJDBCDate("attend_date <", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("attend_date <=", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateIn(List<Date> values) {
            addCriterionForJDBCDate("attend_date in", values, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("attend_date not in", values, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("attend_date between", value1, value2, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("attend_date not between", value1, value2, "attendDate");
            return (Criteria) this;
        }

        public Criteria andWeekIsNull() {
            addCriterion("week is null");
            return (Criteria) this;
        }

        public Criteria andWeekIsNotNull() {
            addCriterion("week is not null");
            return (Criteria) this;
        }

        public Criteria andWeekEqualTo(Integer value) {
            addCriterion("week =", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotEqualTo(Integer value) {
            addCriterion("week <>", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekGreaterThan(Integer value) {
            addCriterion("week >", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekGreaterThanOrEqualTo(Integer value) {
            addCriterion("week >=", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLessThan(Integer value) {
            addCriterion("week <", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLessThanOrEqualTo(Integer value) {
            addCriterion("week <=", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekIn(List<Integer> values) {
            addCriterion("week in", values, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotIn(List<Integer> values) {
            addCriterion("week not in", values, "week");
            return (Criteria) this;
        }

        public Criteria andWeekBetween(Integer value1, Integer value2) {
            addCriterion("week between", value1, value2, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotBetween(Integer value1, Integer value2) {
            addCriterion("week not between", value1, value2, "week");
            return (Criteria) this;
        }

        public Criteria andRecordTimeIsNull() {
            addCriterion("record_time is null");
            return (Criteria) this;
        }

        public Criteria andRecordTimeIsNotNull() {
            addCriterion("record_time is not null");
            return (Criteria) this;
        }

        public Criteria andRecordTimeEqualTo(Date value) {
            addCriterion("record_time =", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeNotEqualTo(Date value) {
            addCriterion("record_time <>", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeGreaterThan(Date value) {
            addCriterion("record_time >", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("record_time >=", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeLessThan(Date value) {
            addCriterion("record_time <", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeLessThanOrEqualTo(Date value) {
            addCriterion("record_time <=", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeIn(List<Date> values) {
            addCriterion("record_time in", values, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeNotIn(List<Date> values) {
            addCriterion("record_time not in", values, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeBetween(Date value1, Date value2) {
            addCriterion("record_time between", value1, value2, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeNotBetween(Date value1, Date value2) {
            addCriterion("record_time not between", value1, value2, "recordTime");
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