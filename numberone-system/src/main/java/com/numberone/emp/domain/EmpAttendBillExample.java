package com.numberone.emp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EmpAttendBillExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EmpAttendBillExample() {
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

        public Criteria andAttendBillIdIsNull() {
            addCriterion("attend_bill_id is null");
            return (Criteria) this;
        }

        public Criteria andAttendBillIdIsNotNull() {
            addCriterion("attend_bill_id is not null");
            return (Criteria) this;
        }

        public Criteria andAttendBillIdEqualTo(String value) {
            addCriterion("attend_bill_id =", value, "attendBillId");
            return (Criteria) this;
        }

        public Criteria andAttendBillIdNotEqualTo(String value) {
            addCriterion("attend_bill_id <>", value, "attendBillId");
            return (Criteria) this;
        }

        public Criteria andAttendBillIdGreaterThan(String value) {
            addCriterion("attend_bill_id >", value, "attendBillId");
            return (Criteria) this;
        }

        public Criteria andAttendBillIdGreaterThanOrEqualTo(String value) {
            addCriterion("attend_bill_id >=", value, "attendBillId");
            return (Criteria) this;
        }

        public Criteria andAttendBillIdLessThan(String value) {
            addCriterion("attend_bill_id <", value, "attendBillId");
            return (Criteria) this;
        }

        public Criteria andAttendBillIdLessThanOrEqualTo(String value) {
            addCriterion("attend_bill_id <=", value, "attendBillId");
            return (Criteria) this;
        }

        public Criteria andAttendBillIdLike(String value) {
            addCriterion("attend_bill_id like", value, "attendBillId");
            return (Criteria) this;
        }

        public Criteria andAttendBillIdNotLike(String value) {
            addCriterion("attend_bill_id not like", value, "attendBillId");
            return (Criteria) this;
        }

        public Criteria andAttendBillIdIn(List<String> values) {
            addCriterion("attend_bill_id in", values, "attendBillId");
            return (Criteria) this;
        }

        public Criteria andAttendBillIdNotIn(List<String> values) {
            addCriterion("attend_bill_id not in", values, "attendBillId");
            return (Criteria) this;
        }

        public Criteria andAttendBillIdBetween(String value1, String value2) {
            addCriterion("attend_bill_id between", value1, value2, "attendBillId");
            return (Criteria) this;
        }

        public Criteria andAttendBillIdNotBetween(String value1, String value2) {
            addCriterion("attend_bill_id not between", value1, value2, "attendBillId");
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

        public Criteria andThemeIsNull() {
            addCriterion("theme is null");
            return (Criteria) this;
        }

        public Criteria andThemeIsNotNull() {
            addCriterion("theme is not null");
            return (Criteria) this;
        }

        public Criteria andThemeEqualTo(String value) {
            addCriterion("theme =", value, "theme");
            return (Criteria) this;
        }

        public Criteria andThemeNotEqualTo(String value) {
            addCriterion("theme <>", value, "theme");
            return (Criteria) this;
        }

        public Criteria andThemeGreaterThan(String value) {
            addCriterion("theme >", value, "theme");
            return (Criteria) this;
        }

        public Criteria andThemeGreaterThanOrEqualTo(String value) {
            addCriterion("theme >=", value, "theme");
            return (Criteria) this;
        }

        public Criteria andThemeLessThan(String value) {
            addCriterion("theme <", value, "theme");
            return (Criteria) this;
        }

        public Criteria andThemeLessThanOrEqualTo(String value) {
            addCriterion("theme <=", value, "theme");
            return (Criteria) this;
        }

        public Criteria andThemeLike(String value) {
            addCriterion("theme like", value, "theme");
            return (Criteria) this;
        }

        public Criteria andThemeNotLike(String value) {
            addCriterion("theme not like", value, "theme");
            return (Criteria) this;
        }

        public Criteria andThemeIn(List<String> values) {
            addCriterion("theme in", values, "theme");
            return (Criteria) this;
        }

        public Criteria andThemeNotIn(List<String> values) {
            addCriterion("theme not in", values, "theme");
            return (Criteria) this;
        }

        public Criteria andThemeBetween(String value1, String value2) {
            addCriterion("theme between", value1, value2, "theme");
            return (Criteria) this;
        }

        public Criteria andThemeNotBetween(String value1, String value2) {
            addCriterion("theme not between", value1, value2, "theme");
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

        public Criteria andDeptNameIsNull() {
            addCriterion("dept_name is null");
            return (Criteria) this;
        }

        public Criteria andDeptNameIsNotNull() {
            addCriterion("dept_name is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNameEqualTo(String value) {
            addCriterion("dept_name =", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotEqualTo(String value) {
            addCriterion("dept_name <>", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThan(String value) {
            addCriterion("dept_name >", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThanOrEqualTo(String value) {
            addCriterion("dept_name >=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThan(String value) {
            addCriterion("dept_name <", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThanOrEqualTo(String value) {
            addCriterion("dept_name <=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLike(String value) {
            addCriterion("dept_name like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotLike(String value) {
            addCriterion("dept_name not like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameIn(List<String> values) {
            addCriterion("dept_name in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotIn(List<String> values) {
            addCriterion("dept_name not in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameBetween(String value1, String value2) {
            addCriterion("dept_name between", value1, value2, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotBetween(String value1, String value2) {
            addCriterion("dept_name not between", value1, value2, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptIdIsNull() {
            addCriterion("dept_id is null");
            return (Criteria) this;
        }

        public Criteria andDeptIdIsNotNull() {
            addCriterion("dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeptIdEqualTo(Integer value) {
            addCriterion("dept_id =", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotEqualTo(Integer value) {
            addCriterion("dept_id <>", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdGreaterThan(Integer value) {
            addCriterion("dept_id >", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dept_id >=", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdLessThan(Integer value) {
            addCriterion("dept_id <", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("dept_id <=", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdIn(List<Integer> values) {
            addCriterion("dept_id in", values, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotIn(List<Integer> values) {
            addCriterion("dept_id not in", values, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("dept_id between", value1, value2, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dept_id not between", value1, value2, "deptId");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNull() {
            addCriterion("apply_date is null");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNotNull() {
            addCriterion("apply_date is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDateEqualTo(Date value) {
            addCriterionForJDBCDate("apply_date =", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("apply_date <>", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThan(Date value) {
            addCriterionForJDBCDate("apply_date >", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("apply_date >=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThan(Date value) {
            addCriterionForJDBCDate("apply_date <", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("apply_date <=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateIn(List<Date> values) {
            addCriterionForJDBCDate("apply_date in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("apply_date not in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("apply_date between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("apply_date not between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterionForJDBCDate("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterionForJDBCDate("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdaysIsNull() {
            addCriterion("apply_workdays is null");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdaysIsNotNull() {
            addCriterion("apply_workdays is not null");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdaysEqualTo(Integer value) {
            addCriterion("apply_workdays =", value, "applyWorkdays");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdaysNotEqualTo(Integer value) {
            addCriterion("apply_workdays <>", value, "applyWorkdays");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdaysGreaterThan(Integer value) {
            addCriterion("apply_workdays >", value, "applyWorkdays");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("apply_workdays >=", value, "applyWorkdays");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdaysLessThan(Integer value) {
            addCriterion("apply_workdays <", value, "applyWorkdays");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdaysLessThanOrEqualTo(Integer value) {
            addCriterion("apply_workdays <=", value, "applyWorkdays");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdaysIn(List<Integer> values) {
            addCriterion("apply_workdays in", values, "applyWorkdays");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdaysNotIn(List<Integer> values) {
            addCriterion("apply_workdays not in", values, "applyWorkdays");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdaysBetween(Integer value1, Integer value2) {
            addCriterion("apply_workdays between", value1, value2, "applyWorkdays");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdaysNotBetween(Integer value1, Integer value2) {
            addCriterion("apply_workdays not between", value1, value2, "applyWorkdays");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdayTimesIsNull() {
            addCriterion("apply_workday_times is null");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdayTimesIsNotNull() {
            addCriterion("apply_workday_times is not null");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdayTimesEqualTo(Double value) {
            addCriterion("apply_workday_times =", value, "applyWorkdayTimes");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdayTimesNotEqualTo(Double value) {
            addCriterion("apply_workday_times <>", value, "applyWorkdayTimes");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdayTimesGreaterThan(Double value) {
            addCriterion("apply_workday_times >", value, "applyWorkdayTimes");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdayTimesGreaterThanOrEqualTo(Double value) {
            addCriterion("apply_workday_times >=", value, "applyWorkdayTimes");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdayTimesLessThan(Double value) {
            addCriterion("apply_workday_times <", value, "applyWorkdayTimes");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdayTimesLessThanOrEqualTo(Double value) {
            addCriterion("apply_workday_times <=", value, "applyWorkdayTimes");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdayTimesIn(List<Double> values) {
            addCriterion("apply_workday_times in", values, "applyWorkdayTimes");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdayTimesNotIn(List<Double> values) {
            addCriterion("apply_workday_times not in", values, "applyWorkdayTimes");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdayTimesBetween(Double value1, Double value2) {
            addCriterion("apply_workday_times between", value1, value2, "applyWorkdayTimes");
            return (Criteria) this;
        }

        public Criteria andApplyWorkdayTimesNotBetween(Double value1, Double value2) {
            addCriterion("apply_workday_times not between", value1, value2, "applyWorkdayTimes");
            return (Criteria) this;
        }

        public Criteria andMatterIsNull() {
            addCriterion("matter is null");
            return (Criteria) this;
        }

        public Criteria andMatterIsNotNull() {
            addCriterion("matter is not null");
            return (Criteria) this;
        }

        public Criteria andMatterEqualTo(String value) {
            addCriterion("matter =", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterNotEqualTo(String value) {
            addCriterion("matter <>", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterGreaterThan(String value) {
            addCriterion("matter >", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterGreaterThanOrEqualTo(String value) {
            addCriterion("matter >=", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterLessThan(String value) {
            addCriterion("matter <", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterLessThanOrEqualTo(String value) {
            addCriterion("matter <=", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterLike(String value) {
            addCriterion("matter like", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterNotLike(String value) {
            addCriterion("matter not like", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterIn(List<String> values) {
            addCriterion("matter in", values, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterNotIn(List<String> values) {
            addCriterion("matter not in", values, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterBetween(String value1, String value2) {
            addCriterion("matter between", value1, value2, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterNotBetween(String value1, String value2) {
            addCriterion("matter not between", value1, value2, "matter");
            return (Criteria) this;
        }

        public Criteria andIsOffetIsNull() {
            addCriterion("is_offet is null");
            return (Criteria) this;
        }

        public Criteria andIsOffetIsNotNull() {
            addCriterion("is_offet is not null");
            return (Criteria) this;
        }

        public Criteria andIsOffetEqualTo(Integer value) {
            addCriterion("is_offet =", value, "isOffet");
            return (Criteria) this;
        }

        public Criteria andIsOffetNotEqualTo(Integer value) {
            addCriterion("is_offet <>", value, "isOffet");
            return (Criteria) this;
        }

        public Criteria andIsOffetGreaterThan(Integer value) {
            addCriterion("is_offet >", value, "isOffet");
            return (Criteria) this;
        }

        public Criteria andIsOffetGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_offet >=", value, "isOffet");
            return (Criteria) this;
        }

        public Criteria andIsOffetLessThan(Integer value) {
            addCriterion("is_offet <", value, "isOffet");
            return (Criteria) this;
        }

        public Criteria andIsOffetLessThanOrEqualTo(Integer value) {
            addCriterion("is_offet <=", value, "isOffet");
            return (Criteria) this;
        }

        public Criteria andIsOffetIn(List<Integer> values) {
            addCriterion("is_offet in", values, "isOffet");
            return (Criteria) this;
        }

        public Criteria andIsOffetNotIn(List<Integer> values) {
            addCriterion("is_offet not in", values, "isOffet");
            return (Criteria) this;
        }

        public Criteria andIsOffetBetween(Integer value1, Integer value2) {
            addCriterion("is_offet between", value1, value2, "isOffet");
            return (Criteria) this;
        }

        public Criteria andIsOffetNotBetween(Integer value1, Integer value2) {
            addCriterion("is_offet not between", value1, value2, "isOffet");
            return (Criteria) this;
        }

        public Criteria andCompleteFlagIsNull() {
            addCriterion("complete_flag is null");
            return (Criteria) this;
        }

        public Criteria andCompleteFlagIsNotNull() {
            addCriterion("complete_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCompleteFlagEqualTo(Integer value) {
            addCriterion("complete_flag =", value, "completeFlag");
            return (Criteria) this;
        }

        public Criteria andCompleteFlagNotEqualTo(Integer value) {
            addCriterion("complete_flag <>", value, "completeFlag");
            return (Criteria) this;
        }

        public Criteria andCompleteFlagGreaterThan(Integer value) {
            addCriterion("complete_flag >", value, "completeFlag");
            return (Criteria) this;
        }

        public Criteria andCompleteFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("complete_flag >=", value, "completeFlag");
            return (Criteria) this;
        }

        public Criteria andCompleteFlagLessThan(Integer value) {
            addCriterion("complete_flag <", value, "completeFlag");
            return (Criteria) this;
        }

        public Criteria andCompleteFlagLessThanOrEqualTo(Integer value) {
            addCriterion("complete_flag <=", value, "completeFlag");
            return (Criteria) this;
        }

        public Criteria andCompleteFlagIn(List<Integer> values) {
            addCriterion("complete_flag in", values, "completeFlag");
            return (Criteria) this;
        }

        public Criteria andCompleteFlagNotIn(List<Integer> values) {
            addCriterion("complete_flag not in", values, "completeFlag");
            return (Criteria) this;
        }

        public Criteria andCompleteFlagBetween(Integer value1, Integer value2) {
            addCriterion("complete_flag between", value1, value2, "completeFlag");
            return (Criteria) this;
        }

        public Criteria andCompleteFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("complete_flag not between", value1, value2, "completeFlag");
            return (Criteria) this;
        }

        public Criteria andAuditFlagIsNull() {
            addCriterion("audit_flag is null");
            return (Criteria) this;
        }

        public Criteria andAuditFlagIsNotNull() {
            addCriterion("audit_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAuditFlagEqualTo(Integer value) {
            addCriterion("audit_flag =", value, "auditFlag");
            return (Criteria) this;
        }

        public Criteria andAuditFlagNotEqualTo(Integer value) {
            addCriterion("audit_flag <>", value, "auditFlag");
            return (Criteria) this;
        }

        public Criteria andAuditFlagGreaterThan(Integer value) {
            addCriterion("audit_flag >", value, "auditFlag");
            return (Criteria) this;
        }

        public Criteria andAuditFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("audit_flag >=", value, "auditFlag");
            return (Criteria) this;
        }

        public Criteria andAuditFlagLessThan(Integer value) {
            addCriterion("audit_flag <", value, "auditFlag");
            return (Criteria) this;
        }

        public Criteria andAuditFlagLessThanOrEqualTo(Integer value) {
            addCriterion("audit_flag <=", value, "auditFlag");
            return (Criteria) this;
        }

        public Criteria andAuditFlagIn(List<Integer> values) {
            addCriterion("audit_flag in", values, "auditFlag");
            return (Criteria) this;
        }

        public Criteria andAuditFlagNotIn(List<Integer> values) {
            addCriterion("audit_flag not in", values, "auditFlag");
            return (Criteria) this;
        }

        public Criteria andAuditFlagBetween(Integer value1, Integer value2) {
            addCriterion("audit_flag between", value1, value2, "auditFlag");
            return (Criteria) this;
        }

        public Criteria andAuditFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("audit_flag not between", value1, value2, "auditFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(Integer value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Integer value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Integer value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Integer value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Integer value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Integer> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Integer> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Integer value1, Integer value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
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