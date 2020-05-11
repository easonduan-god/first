package com.numberone.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.numberone.system.domain.SysUser;

/**
 * 用户表 数据层
 * 
 * @author numberone
 */
public interface SysUserMapper
{
    /**
     * 根据条件分页查询用户对象
     * 
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByLoginName(String userName);

    /**
     * 通过手机号码查询用户
     * 
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    public SysUser selectUserByPhoneNumber(String phoneNumber);

    /**
     * 通过邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户对象信息
     */
    public SysUser selectUserByEmail(String email);

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] ids);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 校验用户名称是否唯一
     * 
     * @param loginName 登录名称
     * @return 结果
     */
    public int checkLoginNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phonenumber);

    /**
     * 校验email是否唯一
     * @param: @param email
     * @param: @return
     * @return: SysUser
     */
    public SysUser checkEmailUnique(String email);
    
    /**
     * 新增员工id
     * @param: @param user
     * @param: @return
     * @return: int
     */
	public int updateEmpIdOfUser(SysUser user);
	
	/**
	 * 根据部门编号查询用户
	 * @param: @param deptId
	 * @param: @return
	 * @return: List<SysUser>
	 */
	public List<SysUser> selectUserListByDeptId(Long deptId);
	
	/**
	 * 查询部门领导
	 * @param: @param sysUser
	 * @param: @return
	 * @return: SysUser
	 */
	public SysUser selectLeaderByDeptId(SysUser sysUser);
	
	/**
	 * 查询CEO
	 * @param: @param deptId
	 * @param: @return
	 * @return: SysUser
	 */
	public SysUser selectCEO(Long deptId);

	public SysUser selectUserByEmpId(String empId);
	/**
	 * 根据员工号或名称或名称首字母搜索
	 * @param: @param empAttendAudit
	 * @param: @return 参数说明
	 * @return List<EmpAttendAudit> 返回类型
	 */
	public List<Map<String, Object>> selectUserByKey(SysUser user);

	/**
	 * 加载用户列表树 也包括期所在部分 根据部门id
	 * @param: @param sysUser
	 * @param: @return
	 * @return: List<Map<String,Object>>
	 */
	public List<Map<String, Object>> selectUserTreeDataByDeptId(SysUser sysUser);
	
	/**
	 * 管理员查询全部
	 * @param: @param sysUser
	 * @param: @return
	 * @return: List<Map<String,Object>>
	 */
	public List<Map<String, Object>> selectAllUserTreeData(SysUser sysUser);

	/** 
	 * 查询员工总数
	 * @param: @param sysUser
	 * @param: @return
	 * @return: Long
	 */
	public Integer selectAllUserCount(SysUser sysUser);

	/**
	 * 查询自己部门人数
	 * @param: @param sysUser
	 * @param: @return
	 * @return: Long
	 */
	public Integer selectAllCountByDeptId(SysUser sysUser);

	/**
	 * 根据userid数组查询usernames
	 * @param: @param array
	 * @return: void
	 */
	public List<String> selectUsernamesByUserIds(Object[] idArr);

	/**
	 * 根据userid数组和部门查询符合的用户数
	 * @param: @param idArr
	 * @param: @param deptId
	 * @return: void
	 */
	public Integer selectCountByUserIdsAndDeptId(@Param("userIds") String[] userIds,@Param("deptId") Long deptId);

	/**
	 * 查询所有 得到字段id userid text username
	 * @param: @return
	 * @return: List<Map<String,String>>
	 */
	public List<Map<String, String>> selectUserListForQuery();

	/**
	 * 查询部门员工 得到字段id userid text username
	 * @param deptId 
	 * @param: @return
	 * @return: List<Map<String,String>>
	 */
	public List<Map<String, String>> selectUserListByDeptIdForQuery(Long deptId);

	/**
	 * 新增工时
	 * @param monthHour
	 */
	public void insertMonthHour(Map<String, Object> monthHour);
	
}
