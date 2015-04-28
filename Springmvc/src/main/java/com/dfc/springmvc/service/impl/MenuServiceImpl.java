package com.dfc.springmvc.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dfc.springmvc.common.PageInfo;
import com.dfc.springmvc.dao.MenuDao;
import com.dfc.springmvc.dao.RoleMenuDao;
import com.dfc.springmvc.pojo.Menu;
import com.dfc.springmvc.service.MenuService;

/**
 * ���ݴ������ʵ��
 * 
 * @author dongdong
 *
 */

@Service("menuService")
public class MenuServiceImpl implements MenuService {

	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private MenuDao menuDao;
	@Autowired
	private RoleMenuDao roleMenuDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<Menu> getUserMenus(String userId) {
		log.info("��ȡ��ǰ�û�ӵ�еĲ˵�");
		return menuDao.getUserMenus(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<Menu> queryAllMenus(PageInfo<Menu> pageInfo, Menu menu) {
		log.info("�˵���ѯ");
		if (null != pageInfo) {
			pageInfo.setTotalRecords(menuDao.getTotalRecords(menu));
		}
		RowBounds rb = new RowBounds(pageInfo.getFromRecord(), pageInfo.getPageSize());
		return menuDao.queryAllMenus(rb, menu);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<Menu> querySystemMenus(PageInfo<Menu> pageInfo, Menu menu) {
		log.info("�˵���ѯ");
		if (null != pageInfo) {
			pageInfo.setTotalRecords(menuDao.getSystemRecords(menu));
		}
		RowBounds rb = new RowBounds(pageInfo.getFromRecord(), pageInfo.getPageSize());
		return menuDao.querySystemMenus(rb, menu);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Menu getMenu(String menuId) {
		log.info("��ȡ�˵���Ϣ");
		return menuDao.getMenu(menuId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Menu> getAllMenus() {
		return menuDao.getMenusByMenuType(null);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Menu> getAllSystemMenus() {
		return menuDao.getMenusByMenuType("system");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Menu> getAllWebMenus() {
		return menuDao.getMenusByMenuType("web");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int saveMenu(Menu menu) {
		log.info("��Ӳ˵���Ϣ");
		return menuDao.saveMenu(menu);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int updateMenu(Menu menu) {
		log.info("���²˵���Ϣ");
		return menuDao.updateMenu(menu);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int deleteMenu(String menuId) {
		log.info("ɾ���˵���Ϣ");
		return menuDao.deleteMenu(menuId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int getMaxMenuOrder() {
		return menuDao.getMaxMenuOrder();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int deleteRoleMenuByMenuId(String menuId) {
		return roleMenuDao.deleteRoleMenuByMenuId(menuId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<String> getMenuTypes() {
		return menuDao.getMenuTypes();
	}

}
