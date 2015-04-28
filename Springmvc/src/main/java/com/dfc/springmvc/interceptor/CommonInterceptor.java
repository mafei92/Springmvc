package com.dfc.springmvc.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dfc.springmvc.pojo.UserInfo;

/**
 * ���������������е�URI
 * �ж��Ƿ��Ѿ���¼
 * 
 * @author dongdong
 * 
 */

@Repository
public class CommonInterceptor implements HandlerInterceptor {

	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * preHandle�����ǽ��д����������õģ�����˼�壬�÷�������Controller����֮ǰ���е��ã�
	 * SpringMVC�е�Interceptor����������ʽ�ģ�����ͬʱ���ڶ��Interceptor��
	 * Ȼ��SpringMVC�����������ǰ��˳��һ����һ����ִ�У�
	 * �������е�Interceptor�е�preHandle����������Controller��������֮ǰ���á�
	 * SpringMVC������Interceptor��ʽ�ṹҲ�ǿ��Խ����жϵģ� �����жϷ�ʽ����preHandle�ķ�
	 * ��ֵΪfalse����preHandle�ķ���ֵΪfalse��ʱ����������ͽ����ˡ�
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.info("���session�Ƿ�ʧЧ");
		// ��session�л�ȡ��ǰ��¼�û���Ϣ
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		if (null != userInfo) {
			return true;
		} else {
			log.info("session�Ѿ�ʧЧ,�������µ�¼");
			// response.sendRedirect(request.getContextPath()+"/pages/login.jsp");
			// request.getRequestDispatcher("/pages/login.jsp").forward(request,response);
			PrintWriter out = response.getWriter();
			StringBuilder builder = new StringBuilder();
			builder.append("<script type=\"text/javascript\" charset=\"utf-8\">");
			//builder.append("alert(\"ҳ���ѹ��ڣ������µ�¼��\");");
			builder.append("window.top.location.href=\"");
			builder.append(request.getContextPath());
			builder.append("/pages/login.jsp");
			builder.append("\";</script>");
			out.print(builder.toString());
			out.close();
			return false;
		}
		/*
		String uri = request.getRequestURI();
		log.info("����������URI��" + uri);
		if (uri.equals(request.getContextPath() + "/common/login.do")) {
			log.info("��¼URI������");
			return true;
		} else {
			log.info("���session�Ƿ�ʧЧ");
			// ��session�л�ȡ��ǰ��¼�û���Ϣ
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
			if (null != userInfo) {
				return true;
			} else {
				log.info("session�Ѿ�ʧЧ,�������µ�¼");
				// response.sendRedirect(request.getContextPath()+"/pages/login.jsp");
				// request.getRequestDispatcher("/pages/login.jsp").forward(request,response);
				PrintWriter out = response.getWriter();
				StringBuilder builder = new StringBuilder();
				builder.append("<script type=\"text/javascript\" charset=\"utf-8\">");
				//builder.append("alert(\"ҳ���ѹ��ڣ������µ�¼��\");");
				builder.append("window.top.location.href=\"");
				builder.append(request.getContextPath());
				builder.append("/pages/login.jsp");
				builder.append("\";</script>");
				out.print(builder.toString());
				out.close();
				return false;
			}
		}
		*/
	}

	/**
	 * �������ֻ���ڵ�ǰ���Interceptor��preHandle��������ֵΪtrue��ʱ��Ż�ִ�С� postHandle�ǽ��д����������õģ�
	 * ����ִ��ʱ�����ڴ��������д���֮��Ҳ������Controller�ķ�������֮��ִ�У�
	 * ����������DispatcherServlet������ͼ����Ⱦ֮ǰִ�У� Ҳ����˵���������������Զ�ModelAndView���в�����
	 * �����������ʽ�ṹ���������ʵķ������෴�ģ�Ҳ����˵��������Interceptor�������÷������������ã�
	 * ���Struts2�������������ִ�й����е���ֻ��Struts2�����intercept������Ҫ�ֶ��ĵ���ActionInvocation��invoke���� ��
	 * Struts2�е���ActionInvocation��invoke�������ǵ�����һ��Interceptor�����ǵ���action��
	 * Ȼ��Ҫ��Interceptor֮ǰ���õ����ݶ�д�ڵ���invoke֮ǰ��Ҫ��Interceptor֮����õ����ݶ�д�ڵ���invoke����֮��
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	/**
	 * �÷���Ҳ����Ҫ��ǰ��Ӧ��Interceptor��preHandle�����ķ���ֵΪtrueʱ�Ż�ִ�С�
	 * �÷������������������֮��Ҳ����DispatcherServlet��Ⱦ����ͼִ�У� 
	 * �����������Ҫ����������������Դ�ģ�
	 * �����������׳��쳣ʱ,��ӵ�ǰ����������ִ�����е���������
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
