package com.numberone.framework.shiro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.numberone.common.constant.Constants;
import com.numberone.common.constant.ShiroConstants;
import com.numberone.common.constant.UserConstants;
import com.numberone.common.enums.UserStatus;
import com.numberone.common.exception.user.CaptchaException;
import com.numberone.common.exception.user.UserBlockedException;
import com.numberone.common.exception.user.UserDeleteException;
import com.numberone.common.exception.user.UserNotExistsException;
import com.numberone.common.exception.user.UserPasswordNotMatchException;
import com.numberone.common.utils.DateUtils;
import com.numberone.common.utils.MessageUtils;
import com.numberone.common.utils.ServletUtils;
import com.numberone.framework.manager.AsyncManager;
import com.numberone.framework.manager.factory.AsyncFactory;
import com.numberone.framework.util.ShiroUtils;
import com.numberone.system.domain.SysUser;
import com.numberone.system.service.ISysUserService;

/**
 * 登录校验方法
 * 
 * @author numberone
 */
@Component
public class SysLoginService
{
    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private ISysUserService userService;
    @Autowired
    private CacheManager cacheManager;

    private Cache<String, List<Map<String,String>>> cache;
    @PostConstruct
    private void init(){
    	cache = cacheManager.getCache("querableCache");
    }
    /**
     * 登录
     */
    public SysUser login(String username, String password)
    {
        // 验证码校验
        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA)))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }

        // 查询用户信息
        //--200220 添加使用工号登陆
        SysUser user = userService.selectUserByLoginName(username);
        
        if (user == null && maybeMobilePhoneNumber(username))
        {
            user = userService.selectUserByPhoneNumber(username);
        }

        if (user == null && maybeEmail(username))
        {
            user = userService.selectUserByEmail(username);
        }
        
        if (user == null && maybeEmpId(username))
        {
        	user = userService.selectUserByEmpId(username);
        }

        if (user == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new UserNotExistsException();
        }
        
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.delete")));
            throw new UserDeleteException();
        }
        
        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked", user.getRemark())));
            throw new UserBlockedException();
        }

        passwordService.validate(user, password);

        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        recordLoginInfo(user);
	        //200225--修改 新增用户可操作用户列表
        	List<SysUser> operableUserList = userService.selectUserByUserIdAndPost(user);
	        user.setOperableUserList(operableUserList);
	        if(operableUserList!=null){
	        	List<Long> operableUserIds = new ArrayList<Long>();
	        	for (SysUser sysUser : operableUserList) {
	        		operableUserIds.add(sysUser.getUserId());
	        	}
	        	user.setOperableUserIds(operableUserIds);
	        }
	        
	        //200225 --新增 用户可以查询的用户列表 hr admin ceo全部 其他用户只能查询本部门员工
	        List<Map<String,String>> querableList  = userService.selectListByUserAndPostForQuery(user);
	        if(querableList!=null){
	        	cache.put("querableMap", querableList);
	        }
        return user;
    }

    private boolean maybeEmail(String username)
    {
        if (!username.matches(UserConstants.EMAIL_PATTERN))
        {
            return false;
        }
        return true;
    }
    private boolean maybeEmpId(String username)
    {
    	if (!username.matches(UserConstants.EMPID_PATTERN))
    	{
    		return false;
    	}
    	return true;
    }

    private boolean maybeMobilePhoneNumber(String username)
    {
        if (!username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN))
        {
            return false;
        }
        return true;
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(SysUser user)
    {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateUserInfo(user);
    }
}
