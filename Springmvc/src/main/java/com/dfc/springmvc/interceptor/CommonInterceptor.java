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
 * 拦截器，拦截所有的URI
 * 判断是否已经登录
 * 
 * @author dongdong
 * 
 */

@Repository
public class CommonInterceptor implements HandlerInterceptor {

	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
	 * SpringMVC中的Interceptor拦截器是链式的，可以同时存在多个Interceptor，
	 * 然后SpringMVC会根据声明的前后顺序一个接一个的执行，
	 * 而且所有的Interceptor中的preHandle方法都会在Controller方法调用之前调用。
	 * SpringMVC的这种Interceptor链式结构也是可以进行中断的， 这种中断方式是令preHandle的返
	 * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.info("检测session是否失效");
		// 从session中获取当前登录用户信息
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		if (null != userInfo) {
			return true;
		} else {
			log.info("session已经失效,返回重新登录");
			// response.sendRedirect(request.getContextPath()+"/pages/login.jsp");
			// request.getRequestDispatcher("/pages/login.jsp").forward(request,response);
			PrintWriter out = response.getWriter();
			StringBuilder builder = new StringBuilder();
			builder.append("<script type=\"text/javascript\" charset=\"utf-8\">");
			//builder.append("alert(\"页面已过期，请重新登录！\");");
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
		log.info("拦截器拦截URI：" + uri);
		if (uri.equals(request.getContextPath() + "/common/login.do")) {
			log.info("登录URI不拦截");
			return true;
		} else {
			log.info("检测session是否失效");
			// 从session中获取当前登录用户信息
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
			if (null != userInfo) {
				return true;
			} else {
				log.info("session已经失效,返回重新登录");
				// response.sendRedirect(request.getContextPath()+"/pages/login.jsp");
				// request.getRequestDispatcher("/pages/login.jsp").forward(request,response);
				PrintWriter out = response.getWriter();
				StringBuilder builder = new StringBuilder();
				builder.append("<script type=\"text/javascript\" charset=\"utf-8\">");
				//builder.append("alert(\"页面已过期，请重新登录！\");");
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
	 * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。 postHandle是进行处理器拦截用的，
	 * 它的执行时间是在处理器进行处理之后，也就是在Controller的方法调用之后执行，
	 * 但是它会在DispatcherServlet进行视图的渲染之前执行， 也就是说在这个方法中你可以对ModelAndView进行操作。
	 * 这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，
	 * 这跟Struts2里面的拦截器的执行过程有点像，只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法 ，
	 * Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor或者是调用action，
	 * 然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	/**
	 * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。
	 * 该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行， 
	 * 这个方法的主要作用是用于清理资源的，
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
