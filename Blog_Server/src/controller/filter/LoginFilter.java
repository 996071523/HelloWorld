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
 * ��¼������
 * �ж��ڽ���ǵ�¼����ʱ�Ƿ��ѵ�¼
 * �ѵ�¼��ֱ�ӽ��룬δ��¼�򷵻ص�¼����
 */
@WebFilter(urlPatterns={"*.jsp","*.s"})
public class LoginFilter implements Filter {
 
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		HttpServletRequest httpRequest = (HttpServletRequest) request;//��ServletRequest���͵�requestת��ΪHttpServletRequest����
		
		String path = httpRequest.getServletPath();//��ȡ���ص���Դ·��
		
		if(path.endsWith("user.s") || path.endsWith("login.jsp")){//ɸѡʱ�ų�login.s��login.jsp
			chain.doFilter(request, response);
		}else if(httpRequest.getSession().getAttribute("loginedUser") != null){//��ȡ�Ự״̬������Ϊ�գ����Ѿ���¼��������ų�������
			chain.doFilter(request, response);
		}else{//���Ự״̬Ϊ�գ���δ��¼��������ʾ��¼�����ض��򵽵�¼���棨login.jsp��
			request.setAttribute("msg","���ȵ�¼ϵͳ");
			request.getRequestDispatcher("login.jsp").forward(httpRequest, response);
		}
	}
				

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
