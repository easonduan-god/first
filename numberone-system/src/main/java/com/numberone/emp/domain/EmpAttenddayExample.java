package com.numberone.emp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EmpAttenddayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EmpAttenddayExample() {
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

        public Criteria andAttenddayIdIsNull() {
            addCriterion("attendday_id is null");
            return (Criteria) this;
        }

        public Criteria andAttenddayIdIsNotNull() {
            addCriterion("attendday_id is not null");
            return (Criteria) this;
        }

        public Criteria andAttenddayIdEqualTo(String value) {
            addCriterion("attendday_id =", value, "attenddayId");
            return (Criteria) this;
        }

        public Criteria andAttenddayIdNotEqualTo(String value) {
            addCriterion("attendday_id <>", value, "attenddayId");
            return (Criteria) this;
        }

        public Criteria andAttenddayIdGreaterThan(String value) {
            addCriterion("attendday_id >", value, "attenddayId");
            return (Criteria) this;
        }

        public Criteria andAttenddayIdGreaterThanOrEqualTo(String value) {
            addCriterion("attendday_id >=", value, "attenddayId");
            return (Criteria) this;
        }

        public Criteria andAttenddayIdLessThan(String value) {
            addCriterion("attendday_id <", value, "attenddayId");
            return (Criteria) this;
        }

        public Criteria andAttenddayIdLessThanOrEqualTo(String value) {
            addCriterion("attendday_id <=", value, "attenddayId");
            return (Criteria) this;
        }

        public Criteria andAttenddayIdLike(String value) {
            addCriterion("attendday_id like", value, "attenddayId");
            return (Criteria) this;
        }

        public Criteria andAttenddayIdNotLike(String value) {
            addCriterion("attendday_id not like", value, "attenddayId");
            return (Criteria) this;
        }

        public Criteria andAttenddayIdIn(List<String> values) {
            addCriterion("attendday_id in", values, "attenddayId");
            return (Criteria) this;
        }

        public Criteria andAttenddayIdNotIn(List<String> values) {
            addCriterion("attendday_id not in", values, "attenddayId");
            return (Criteria) this;
        }

        public Criteria andAttenddayIdBetween(String value1, String value2) {
            addCriterion("attendday_id between", value1, value2, "attenddayId");
            return (Criteria) this;
        }

        public Criteria andAttenddayIdNotBetween(String value1, String value2) {
            addCriterion("attendday_id not between", value1, value2, "attenddayId");
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

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
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

        public Criteria andFirstTiemIsNull() {
            addCriterion("first_tiem is null");
            return (Criteria) this;
        }

        public Criteria andFirstTiemIsNotNull() {
            addCriterion("first_tiem is not null");
            return (Criteria) this;
        }

        public Criteria andFirstTiemEqualTo(Date value) {
            addCriterion("first_tiem =", value, "firstTiem");
            return (Criteria) this;
        }

        public Criteria andFirstTiemNotEqualTo(Date value) {
            addCriterion("first_tiem <>", value, "firstTiem");
            return (Criteria) this;
        }

        public Criteria andFirstTiemGreaterThan(Date value) {
            addCriterion("first_tiem >", value, "firstTiem");
            return (Criteria) this;
        }

        public Criteria andFirstTiemGreaterThanOrEqualTo(Date value) {
            addCriterion("first_tiem >=", value, "firstTiem");
            return (Criteria) this;
        }

        public Criteria andFirstTiemLessThan(Date value) {
            addCriterion("first_tiem <", value, "firstTiem");
            return (Criteria) this;
        }

        public Criteria andFirstTiemLessThanOrEqualTo(Date value) {
            addCriterion("first_tiem <=", value, "firstTiem");
            return (Criteria) this;
        }

        public Criteria andFirstTiemIn(List<Date> values) {
            addCriterion("first_tiem in", values, "firstTiem");
            return (Criteria) this;
        }

        public Criteria andFirstTiemNotIn(List<Date> values) {
            addCriterion("first_tiem not in", values, "firstTiem");
            return (Criteria) this;
        }

        public Criteria andFirstTiemBetween(Date value1, Date value2) {
            addCriterion("first_tiem between", value1, value2, "firstTiem");
            return (Criteria) this;
        }

        public Criteria andFirstTiemNotBetween(Date value1, Date value2) {
            addCriterion("first_tiem not between", value1, value2, "firstTiem");
            return (Criteria) this;
        }

        public Criteria andLastTimeIsNull() {
            addCriterion("last_time is null");
            return (Criteria) this;
        }

        public Criteria andLastTimeIsNotNull() {
            addCriterion("last_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastTimeEqualTo(Date value) {
            addCriterion("last_time =", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotEqualTo(Date value) {
            addCriterion("last_time <>", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeGreaterThan(Date value) {
            addCriterion("last_time >", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_time >=", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeLessThan(Date value) {
            addCriterion("last_time <", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_time <=", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeIn(List<Date> values) {
            addCriterion("last_time in", values, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotIn(List<Date> values) {
            addCriterion("last_time not in", values, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeBetween(Date value1, Date value2) {
            addCriterion("last_time between", value1, value2, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_time not between", value1, value2, "lastTime");
            return (Criteria) this;
        }

        public Criteria andAttendResultIsNull() {
            addCriterion("attend_result is null");
            return (Criteria) this;
        }

        public Criteria andAttendResultIsNotNull() {
            addCriterion("attend_result is not null");
            return (Criteria) this;
        }

        public Criteria andAttendResultEqualTo(Integer value) {
            addCriterion("attend_result =", value, "attendResult");
            return (Criteria) this;
        }

        public Criteria andAttendResultNotEqualTo(Integer value) {
            addCriterion("attend_result <>", value, "attendResult");
            return (Criteria) this;
        }

        public Criteria andAttendResultGreaterThan(Integer value) {
            addCriterion("attend_result >", value, "attendResult");
            return (Criteria) this;
        }

        public Criteria andAttendResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("attend_result >=", value, "attendResult");
            return (Criteria) this;
        }

        public Criteria andAttendResultLessThan(Integer value) {
            addCriterion("attend_result <", value, "attendResult");
            return (Criteria) this;
        }

        public Criteria andAttendResultLessThanOrEqualTo(Integer value) {
            addCriterion("attend_result <=", value, "attendResult");
            return (Criteria) this;
        }

        public Criteria andAttendResultIn(List<Integer> values) {
            addCriterion("attend_result in", values, "attendResult");
            return (Criteria) this;
        }

        public Criteria andAttendResultNotIn(List<Integer> values) {
            addCriterion("attend_result not in", values, "attendResult");
            return (Criteria) this;
        }

        public Criteria andAttendResultBetween(Integer value1, Integer value2) {
            addCriterion("attend_result between", value1, value2, "attendResult");
            return (Criteria) this;
        }

        public Criteria andAttendResultNotBetween(Integer value1, Integer value2) {
            addCriterion("attend_result not between", value1, value2, "attendResult");
            return (Criteria) this;
        }

        public Criteria andAttendTypeIsNull() {
            addCriterion("attend_type is null");
            return (Criteria) this;
        }

        public Criteria andAttendTypeIsNotNull() {
            addCriterion("attend_type is not null");
            return (Criteria) this;
        }

        public Criteria andAttendTypeEqualTo(Integer value) {
            addCriterion("attend_type =", value, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeNotEqualTo(Integer value) {
            addCriterion("attend_type <>", value, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeGreaterThan(Integer value) {
            addCriterion("attend_type >", value, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("attend_type >=", value, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeLessThan(Integer value) {
            addCriterion("attend_type <", value, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeLessThanOrEqualTo(Integer value) {
            addCriterion("attend_type <=", value, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeIn(List<Integer> values) {
            addCriterion("attend_type in", values, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeNotIn(List<Integer> values) {
            addCriterion("attend_type not in", values, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeBetween(Integer value1, Integer value2) {
            addCriterion("attend_type between", value1, value2, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("attend_type not between", value1, value2, "attendType");
            return (Criteria) this;
        }

        public Criteria andAdditionalTimeIsNull() {
            addCriterion("additional_time is null");
            return (Criteria) this;
        }

        public Criteria andAdditionalTimeIsNotNull() {
            addCriterion("additional_time is not null");
            return (Criteria) this;
        }

        public Criteria andAdditionalTimeEqualTo(Double value) {
            addCriterion("additional_time =", value, "additionalTime");
            return (Criteria) this;
        }

        public Criteria andAdditionalTimeNotEqualTo(Double value) {
            addCriterion("additional_time <>", value, "additionalTime");
            return (Criteria) this;
        }

        public Criteria andAdditionalTimeGreaterThan(Double value) {
            addCriterion("additional_time >", value, "additionalTime");
            return (Criteria) this;
        }

        public Criteria andAdditionalTimeGreaterThanOrEqualTo(Double value) {
            addCriterion("additional_time >=", value, "additionalTime");
            return (Criteria) this;
        }

        public Criteria andAdditionalTimeLessThan(Double value) {
            addCriterion("additional_time <", value, "additionalTime");
            return (Criteria) this;
        }

        public Criteria andAdditionalTimeLessThanOrEqualTo(Double value) {
            addCriterion("additional_time <=", value, "additionalTime");
            return (Criteria) this;
        }

        public Criteria andAdditionalTimeIn(List<Double> values) {
            addCriterion("additional_time in", values, "additionalTime");
            return (Criteria) this;
        }

        public Criteria andAdditionalTimeNotIn(List<Double> values) {
            addCriterion("additional_time not in", values, "additionalTime");
            return (Criteria) this;
        }

        public Criteria andAdditionalTimeBetween(Double value1, Double value2) {
            addCriterion("additional_time between", value1, value2, "additionalTime");
            return (Criteria) this;
        }

        public Criteria andAdditionalTimeNotBetween(Double value1, Double value2) {
            addCriterion("additional_time not between", value1, value2, "additionalTime");
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