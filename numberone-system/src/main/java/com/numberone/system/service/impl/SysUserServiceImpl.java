package com.numberone.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberone.common.annotation.DataScope;
import com.numberone.common.constant.UserConstants;
import com.numberone.common.exception.BusinessException;
import com.numberone.common.support.Convert;
import com.numberone.common.utils.DateUtils;
import com.numberone.common.utils.Md5Utils;
import com.numberone.common.utils.StringUtils;
import com.numberone.emp.domain.EmpYearVacation;
import com.numberone.emp.mapper.EmpYearVacationMapper;
import com.numberone.system.domain.SysPost;
import com.numberone.system.domain.SysRole;
import com.numberone.system.domain.SysUser;
import com.numberone.system.domain.SysUserPost;
import com.numberone.system.domain.SysUserRole;
import com.numberone.system.mapper.SysPostMapper;
import com.numberone.system.mapper.SysRoleMapper;
import com.numberone.system.mapper.SysUserMapper;
import com.numberone.system.mapper.SysUserPostMapper;
import com.numberone.system.mapper.SysUserRoleMapper;
import com.numberone.system.service.ISysConfigService;
import com.numberone.system.service.ISysUserService;

/**
 * 用户 业务层处理
 * 
 * @author numberone
 */
@Service
public class SysUserServiceImpl implements ISysUserService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;
    
    @Autowired
    private EmpYearVacationMapper empYearVacationMapper;
    
    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private ISysConfigService configService;

    /**
     * 根据条件分页查询用户对象
     * 
     * @param user 用户信息
     * 
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(tableAlias = "u")
    public List<SysUser> selectUserList(SysUser user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByLoginName(String userName)
    {
        return userMapper.selectUserByLoginName(userName);
    }

    /**
     * 通过手机号码查询用户
     * 
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByPhoneNumber(String phoneNumber)
    {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * 通过邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByEmail(String email)
    {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId)
    {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(String ids) throws BusinessException
    {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds)
        {
            if (SysUser.isAdmin(userId))
            {
                throw new BusinessException("不允许删除超级管理员用户");
            }
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 新增保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int insertUser(SysUser user)
    {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        
        //---020206新增员工id
        Long userId = user.getUserId();
        int length = (userId+"").length();
        if(length>5){
        	user.setEmpId(userId+"");
        }else{
        	user.setEmpId("cn"+StringUtils.leftPad(userId+"", 5, "0"));
        }
        userMapper.updateEmpIdOfUser(user);
        
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        
        //---200207 新增用户年假
        	insertUserYearVacation(user);
        return rows;
    }
  //---200207 新增用户年假
    private void insertUserYearVacation(SysUser user) {
    	EmpYearVacation yearVacation = new EmpYearVacation();
    	//补全数据
    	yearVacation.setYearVacationId(StringUtils.getUUID());
    	yearVacation.setEmpId(user.getEmpId());
    	yearVacation.setUserId(user.getUserId());
    	yearVacation.setYear(Integer.parseInt(DateUtils.dateTimeNow(DateUtils.YYYY)));
    	yearVacation.setTimeUsed(0.0);
    	yearVacation.setTimeTotal(0.0);
    	yearVacation.setTimeSurplus(0.0);
    	//新增
    	empYearVacationMapper.insert(yearVacation);
	}

	/**
     * 修改保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(SysUser user)
    {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户个人详细信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户密码
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(SysUser user)
    {
        return updateUserInfo(user);
    }

    /**
     * 新增用户角色信息
     * 
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user)
    {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     * 
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user)
    {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts))
        {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0)
            {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 校验用户名称是否唯一
     * 
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName)
    {
        int count = userMapper.checkLoginNameUnique(loginName);
        if (count > 0)
        {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 查询用户所属角色组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId)
    {
        List<SysRole> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list)
        {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(Long userId)
    {
        List<SysPost> list = postMapper.selectPostsByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list)
        {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 导入用户数据
     * 
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList)
        {
            try
            {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByLoginName(user.getLoginName());
                if (StringUtils.isNull(u))
                {
                    user.setPassword(Md5Utils.hash(user.getLoginName() + password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getLoginName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getLoginName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 用户状态修改
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int changeStatus(SysUser user)
    {
        if (SysUser.isAdmin(user.getUserId()))
        {
            throw new BusinessException("不允许修改超级管理员用户");
        }
        return userMapper.updateUser(user);
    }

	@Override
	public List<SysUser> selectUserByUserIdAndPost(SysUser sysUser) {
		Boolean admin_flag = false;
		//1、查询用户岗位
			//admin用户
		if("admin".equals(sysUser.getUserName())){
			admin_flag = true;
		}
			//非管理员
		List<SysPost> postList = postMapper.selectPostsByUserId(sysUser.getUserId());
		String postNameStr = "";
		for (SysPost sysPost : postList) {
			postNameStr += "," + sysPost.getPostCode();
		}
		//2、判断用户属于什么岗位，以此进行相应操作
			//ceo 或者 hr
		if(admin_flag || postNameStr.contains("ceo") || postNameStr.contains("hr")){
			return userMapper.selectUserList(new SysUser());
		}
			//se 项目经理
		if(postNameStr.contains("manage")){
			return userMapper.selectUserListByDeptId(sysUser.getDeptId());
		}
			//普通员工
		ArrayList<SysUser> list = new ArrayList<SysUser>();
		list.add(sysUser);
		
		return list;
	}

	@Override
	public SysUser selectLeader(SysUser sysUser) {
		//管理员
		if("admin".equals(sysUser.getUserName())) return sysUser;
		//非管理员
		List<SysPost> postList = postMapper.selectPostsByUserId(sysUser.getUserId());
		String postNameStr = "";
		for (SysPost sysPost : postList) {
			postNameStr += "," + sysPost.getPostCode();
		}
		//2、判断用户属于什么岗位，以此进行相应操作
			//ceo 或者 hr
		if(postNameStr.contains("ceo")){
			return sysUser;
		}
		
		//se 项目经理或者 hr交由总经理审核
		//关联部门编号
		if(postNameStr.contains("manage") || postNameStr.contains("hr")){
			return userMapper.selectCEO(sysUser.getDeptId());
		}
		//user 普通员工
		if(postNameStr.contains("user")){
			return userMapper.selectLeaderByDeptId(sysUser);
		}
		throw new BusinessException("不要瞎改岗位表");
	}

	@Override
	public SysUser selectUserByEmpId(String empId) {
		
		return userMapper.selectUserByEmpId(empId);
	}

	@Override
	public List<Map<String, Object>> selectUserByKey(SysUser user) {
		return userMapper.selectUserByKey(user);
	}

	/** 
	 * @Description: 加载用户列表树 也包括期所在部分
	 * @param: @param sysUser
	 * @param: @return
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> selectUserTree(SysUser sysUser) {
		//管理员可以查询全部
		if("admin".equals(sysUser.getLoginName())){
			return userMapper.selectAllUserTreeData(sysUser);
		}
		//总经理和hr也能查询全部
		String rolesName = sysUser.getRolesName();
		if(rolesName.contains("hr") || rolesName.contains("ceo")){
			return userMapper.selectAllUserTreeData(sysUser);
		}
		return userMapper.selectUserTreeDataByDeptId(sysUser);
	}

	/** 
	 * @Description: 查询用户数量 普通员工只能查询本部门的 管理员 hr ceo查询总人数
	 * @param: @param sysUser
	 * @param: @return
	 * @throws
	 */
	@Override
	public Integer selectUserCount(SysUser sysUser) {
		//管理员可以查询全部
		if("admin".equals(sysUser.getLoginName())){
			return userMapper.selectAllUserCount(sysUser);
		}
		//总经理和hr也能查询全部
		String rolesName = sysUser.getRolesName();
		if(rolesName.contains("hr") || rolesName.contains("ceo")){
			return userMapper.selectAllUserCount(sysUser);
		}
		return userMapper.selectAllCountByDeptId(sysUser);
	}

	/** 
	 * @Description: 
	 * @param: @param user
	 * @param: @return
	 * @throws
	 */
	@Override
	public List<Map<String,String>> selectListByUserAndPostForQuery(SysUser user) {
		//1、查询用户岗位
			//admin用户
		if("admin".equals(user.getUserName())){
			return userMapper.selectUserListForQuery();
		}
			//非管理员
		List<SysPost> postList = postMapper.selectPostsByUserId(user.getUserId());
		String postNameStr = "";
		for (SysPost sysPost : postList) {
			postNameStr += "," + sysPost.getPostCode();
		}
		//2、判断用户属于什么岗位，以此进行相应操作
			//ceo 或者 hr
		if(postNameStr.contains("ceo") || postNameStr.contains("hr")){
			return userMapper.selectUserListForQuery();
		}
		//其他人员
		return userMapper.selectUserListByDeptIdForQuery(user.getDeptId());
	}

}
