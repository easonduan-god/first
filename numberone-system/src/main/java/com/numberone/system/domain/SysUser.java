package com.numberone.system.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberone.common.annotation.Excel;
import com.numberone.common.annotation.Excel.Type;
import com.numberone.common.base.BaseEntity;
import com.numberone.common.json.JSON;

/**
 * 用户对象 sys_user
 * 
 * @author numberone
 */
public class SysUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Excel(name = "用户序号")
    private Long userId;
    
    @Excel(name = "员工号")
    private String empId;
    /** 部门ID */
    @Excel(name = "部门编号", type = Type.IMPORT)
    private Long deptId;

    /** 部门父ID */
    private Long parentId;

    /** 登录名称 */
    @Excel(name = "登录名称")
    private String loginName;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String userName;
    private String text;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    private String email;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生日")
    private Date birthday;
    
    @Excel(name = "地址")
    private String address;
    
    @Excel(name = "办公电话")
    private String officePhone;
    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phonenumber;

    /** 用户性别 */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 用户头像 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 盐加密 */
    private String salt;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 最后登陆IP */
    @Excel(name = "最后登陆IP", type = Type.EXPORT)
    private String loginIp;

    /** 最后登陆时间 */
    @Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date loginDate;

    /** 部门对象 */
    @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT)
    private SysDept dept;
    
    /** 角色名称 */
    private String rolesName;
    private List<SysRole> roles;

    /** 角色组 */
    private Long[] roleIds;

    /** 岗位组 */
    private Long[] postIds;
    
    /** 用户可操作的用户列表 */
    private List<SysUser> operableUserList;
    /** 用户可操作的用户id */
    private List<Long> operableUserIds;
    
    public SysUser(Long userId) {
		super();
		this.userId = userId;
	}
    public SysUser() {
    }

	public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getLoginIp()
    {
        return loginIp;
    }

    public void setLoginIp(String loginIp)
    {
        this.loginIp = loginIp;
    }

    public Date getLoginDate()
    {
        return loginDate;
    }

    public void setLoginDate(Date loginDate)
    {
        this.loginDate = loginDate;
    }

    public SysDept getDept()
    {
        if (dept == null)
        {
            dept = new SysDept();
        }
        return dept;
    }

    public void setDept(SysDept dept)
    {
        this.dept = dept;
    }

    public List<SysRole> getRoles()
    {
        return roles;
    }
    public String getRolesName()
    {
    	StringBuffer sb = new StringBuffer();
    	SysRole role = null;
    	if(roles != null && roles.size() != 0){ 
	    	for (Object obj : roles) {
	    		if(obj instanceof SysRole) {
	    			role = (SysRole) obj;
	            } else {
	            	try {
	            		role = JSON.unmarshal(JSON.marshal(obj), SysRole.class);
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    			}
	            }
				sb.append(role.getRoleName()+",");
			}
	    	int length = sb.length();
	    	if(length!=0)
				sb.deleteCharAt(length-1);
	    	this.rolesName = sb.toString();
    	}
    	return rolesName;
    }

    public void setRoles(List<SysRole> roles)
    {
        this.roles = roles;
    }

    public Long[] getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds)
    {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds()
    {
        return postIds;
    }

    public void setPostIds(Long[] postIds)
    {
        this.postIds = postIds;
    }

    public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<SysUser> getOperableUserList() {
		if(this.operableUserList==null) this.operableUserList = new ArrayList<SysUser>();
		return operableUserList;
	}
	public List<Long> getOperableUserIds() {
		if(this.operableUserIds==null){
			operableUserIds = new ArrayList<Long>();
		}
		return operableUserIds;
	}
	public void setOperableUserIds(List<Long> operableUserIds) {
		this.operableUserIds = operableUserIds;
	}

	public void setOperableUserList(List<SysUser> operableUserList) {
		this.operableUserList = operableUserList;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("empId", getEmpId())
            .append("deptId", getDeptId())
            .append("loginName", getLoginName())
            .append("userName", getUserName())
            .append("email", getEmail())
            .append("address", getAddress())
            .append("officePhone", getOfficePhone())
            .append("birthday", getBirthday())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("avatar", getAvatar())
            .append("password", getPassword())
            .append("salt", getSalt())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("loginIp", getLoginIp())
            .append("loginDate", getLoginDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("dept", getDept())
            .toString();
    }
}
