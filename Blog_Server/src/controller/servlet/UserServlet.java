package controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import bean.User;
import biz.UserBiz;
import controller.exception.BizException;
import util.BeanUtils;

/**
 * ���û��йصĵ�¼��ע�ᡢ��ѯ���˳����������룬ʹ��op�ֶα�ʶҵ���������
 * ������һ�����ܴ���һ��servlet�࣬��ʡ�˺ܶ���Դ
 */
@WebServlet("/user.s")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public UserBiz userbiz =new UserBiz();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
		String op = request.getParameter("op");
		
		if("login".equals(op)){//��¼
			login(request,response);
		}else if("query".equals(op)){//��ѯ
			query(request,response);
		}else if("add".equals(op)){//���
			add(request,response);
		}else if("find".equals(op)){//�޸�
			find(request,response);
		}else if("save".equals(op)){//�����޸�
			save(request,response);
		}else if("delete".equals(op)){//ɾ��
			delete(request,response);
		}
		
	}
	
	
	/**
	 * ɾ���û�
	 * @param request
	 * @param response
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		User user = BeanUtils.asBean(request, User.class);
		String msg;
		try {
			userbiz.delete(user);
			msg="�û�ɾ���ɹ�";
		} catch (BizException e) {			
			e.printStackTrace();
			msg=e.getMessage();
		}
	}



	/**
	 * �޸ı����ύ
	 * �����û�id�޸ı�����Ϣ
	 * @param request
	 * @param response
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) {		
		User user = BeanUtils.asBean(request, User.class);
		String msg;
		try {
			userbiz.save(user);
			msg="�û�����ɹ�";
		} catch (BizException e) {			
			e.printStackTrace();
			msg=e.getMessage();
		}
	}



	/**
	 * �޸Ĳ�ѯ
	 * ����id��ѯ�û���Ϣ
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void find(HttpServletRequest request, HttpServletResponse response) throws IOException {			
		String id=request.getParameter("id");
		User user = userbiz.findById(id);
		//��user���ظ�ҳ��
		String userString = JSON.toJSONString(user);
		response.getWriter().append(userString);
	}


	/**
	 * �û���ӷ���
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
		User user = BeanUtils.asBean(request, User.class);
		System.out.println(user);
		try {
			userbiz.add(user);			
		} catch (BizException e) {			
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
		}finally{
			query(request,response);
		}
	}

	/**
	 * ��ѯ��ʾ
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = BeanUtils.asBean(request, User.class);
		request.setAttribute("userList", userbiz.find(user));
		request.getRequestDispatcher("manage-user.jsp").forward(request, response);
	}

	/**
	 * ��¼���
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");//��ȡ�����˺�������
		String userpwd = request.getParameter("userpwd");
		
		User user = null;
		try {
			user = userbiz.login(username, userpwd);
		} catch (BizException e) {			
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return ;
		}
		if(user == null){//��¼ʧ��   �˺Ż����������
			request.setAttribute("msg", "�˺Ż������������");
			request.getRequestDispatcher("login.jsp").forward(request, response);//�����ض���ˢ�µ�¼��login.jsp������
		}else{//��¼�ɹ�
			request.getSession().setAttribute("loginedUser", user);//����Ự״̬����ʾ���û���¼״̬
			response.sendRedirect("index.jsp");//��¼�ɹ���ת����̨������
		}		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
