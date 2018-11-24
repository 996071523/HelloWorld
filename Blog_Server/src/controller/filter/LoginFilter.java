package controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * 登录过滤类
 * 判断在进入非登录界面时是否已登录
 * 已登录则直接进入，未登录则返回登录界面
 */
@WebFilter(urlPatterns={"*.jsp","*.s"})
public class LoginFilter implements Filter {
 
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		HttpServletRequest httpRequest = (HttpServletRequest) request;//将ServletRequest类型的request转型为HttpServletRequest类型
		
		String path = httpRequest.getServletPath();//获取返回的资源路径
		
		if(path.endsWith("user.s") || path.endsWith("login.jsp")){//筛选时放出login.s与login.jsp
			chain.doFilter(request, response);
		}else if(httpRequest.getSession().getAttribute("loginedUser") != null){//获取会话状态，若不为空，则已经登录，将请求放出过滤器
			chain.doFilter(request, response);
		}else{//若会话状态为空，则未登录，设置提示登录，并重定向到登录界面（login.jsp）
			request.setAttribute("msg","请先登录系统");
			request.getRequestDispatcher("login.jsp").forward(httpRequest, response);
		}
	}
				

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
