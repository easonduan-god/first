package com.numberone.common.constant;

/**
 * attend处常亮
 * @author: easonduan
 * @Company: 创智和宇
 * @date: 2020年2月4日 下午5:01:59
 * @param:
 */
public class AttendConstants
{
	/**
     * 日程唯一
     */
    public static final String WORKDATE_UNIQUE = "0";
    public static final String WORKDATE_NOT_UNIQUE = "1";
    
    /**
     * 是否销假(0否 1是)
     */
    public static final Integer IS_NOT_OFFET = 0;//否
    public static final Integer IS_OFFET = 1;//是
    
    /**
     * 完成状态(0未完成 1已完成)
     */
    public static final Integer IS_NOT_COMPLETE = 0;//否
    public static final Integer IS_COMPLETE = 1;//是
    
    /**
     * 审核状态(0未审核 1审核中 2审核不通过 3审核已通过)
     */
    public static final Integer UNAUDIT = 0;//未审核
    public static final Integer IN_AUDIT = 1;//审核中
    public static final Integer AUDIT_FAIL = 2;//审核不通过
    public static final Integer AUDIT_PASS = 3;//审核已通过
    
}
