/**
 * 
 */
package com.dfc.springmvc.common;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * model: SpringBeanJobFactory <br>
 * description: �Զ���SpringBeanJobFactory�����ڶ�Jobע��ApplicationContext�ȡ�<br>
 */
public class SpringBeanJobFactory extends
		org.springframework.scheduling.quartz.SpringBeanJobFactory implements
		ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * �������Ǹ�����super��createJobInstance���������䴴�����������ٽ���autowire��
	 */
	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle)
			throws Exception {
		Object jobInstance = super.createJobInstance(bundle);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(jobInstance);
		return jobInstance;
	}
}
