package com.dfc.springmvc.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dfc.springmvc.dao.RoleDao;
import com.dfc.springmvc.dao.UserInfoDao;
import com.dfc.springmvc.pojo.Role;
import com.dfc.springmvc.pojo.UserInfo;

/**
 * 自定义Realm,进行数据源配置
 */

@Service
@Transactional
public class ShiroDataBaseRealm extends AuthorizingRealm {

	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private RoleDao roleDao;

	/**
	 * 获取授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		if (principalCollection == null) {
			throw new AuthorizationException("principalCollection对象不能为空");
		}
		UserInfo user = (UserInfo) principalCollection.fromRealm(getName()).iterator().next();
		if (user != null) {
			// 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 用户的角色集合
			Set<String> roleSet = new HashSet<String>();
			List<Role> roles = roleDao.getRolesByUserId(user.getUserId());
			for (Role role : roles) {
				roleSet.add(role.getRoleName());
			}
			info.setRoles(roleSet);
			
			return info;
		}
		return null;
	}

	/**
	 * 获取身份验证相关信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		// UsernamePasswordToken对象用来存放提交的登录信息
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String userName = token.getUsername();
		String password = String.valueOf(token.getPassword());
		// 查出是否有此用户
		UserInfo userInfo = userInfoDao.login(userName, password);
		if (userInfo != null) {
			// 若存在，将此用户存放到登录认证info中
			return new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), getName());
		} else {
			throw new UnknownAccountException("用户不存在");
		}

	}

}