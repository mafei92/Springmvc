package com.dfc.springmvc.common;

import java.text.MessageFormat;
import java.util.List;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.dfc.springmvc.dao.MenuDao;
import com.dfc.springmvc.pojo.Menu;

/**
 * @author dongdong
 * 
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Section> {

	@Autowired
	private MenuDao menuDao;

	private String filterChainDefinitions;

	public static final String PREMISSION_STRING = "authc, perms[{0}]";

	@Override
	public Section getObject() throws Exception {
		Ini ini = new Ini();
		// 加载默认的url
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		// 添加所有权限
		List<Menu> menus = menuDao.getMenusByMenuType(null);
		for (Menu menu : menus) {
			section.put(menu.getMenuUri(), MessageFormat.format(PREMISSION_STRING, menu.getMenuUri()));
		}
		// 最后添加其他URL过滤，此处为anon，否则css、js等被拦截
		// section.put("/**", "authc, roles[admin]");
		section.put("/**", "authc");
		return section;
	}

	/**
	 * 通过filterChainDefinitions对默认的url过滤定义
	 * 
	 * @param filterChainDefinitions
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	@Override
	public Class<?> getObjectType() {
		return this.getClass();
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
