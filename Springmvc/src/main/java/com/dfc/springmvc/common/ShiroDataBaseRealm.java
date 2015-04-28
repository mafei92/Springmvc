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
 * �Զ���Realm,��������Դ����
 */

@Service
@Transactional
public class ShiroDataBaseRealm extends AuthorizingRealm {

	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private RoleDao roleDao;

	/**
	 * ��ȡ��Ȩ��Ϣ
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		if (principalCollection == null) {
			throw new AuthorizationException("principalCollection������Ϊ��");
		}
		UserInfo user = (UserInfo) principalCollection.fromRealm(getName()).iterator().next();
		if (user != null) {
			// Ȩ����Ϣ����info,������Ų�����û������еĽ�ɫ��role����Ȩ�ޣ�permission��
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// �û��Ľ�ɫ����
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
	 * ��ȡ�����֤�����Ϣ
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		// UsernamePasswordToken������������ύ�ĵ�¼��Ϣ
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String userName = token.getUsername();
		String password = String.valueOf(token.getPassword());
		// ����Ƿ��д��û�
		UserInfo userInfo = userInfoDao.login(userName, password);
		if (userInfo != null) {
			// �����ڣ������û���ŵ���¼��֤info��
			return new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), getName());
		} else {
			throw new UnknownAccountException("�û�������");
		}

	}

}