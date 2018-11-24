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
 * 将用户有关的登录、注册、查询、退出、忘记密码，使用op字段标识业务操作类型
 * 避免了一个功能创建一个servlet类，节省了很多资源
 */
@WebServlet("/user.s")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public UserBiz userbiz =new UserBiz();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
		String op = request.getParameter("op");
		
		if("login".equals(op)){//登录
			login(request,response);
		}else if("query".equals(op)){//查询
			query(request,response);
		}else if("add".equals(op)){//添加
			add(request,response);
		}else if("find".equals(op)){//修改
			find(request,response);
		}else if("save".equals(op)){//保存修改
			save(request,response);
		}else if("delete".equals(op)){//删除
			delete(request,response);
		}
		
	}
	
	
	/**
	 * 删除用户
	 * @param request
	 * @param response
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		User user = BeanUtils.asBean(request, User.class);
		String msg;
		try {
			userbiz.delete(user);
			msg="用户删除成功";
		} catch (BizException e) {			
			e.printStackTrace();
			msg=e.getMessage();
		}
	}



	/**
	 * 修改保存提交
	 * 根据用户id修改保存信息
	 * @param request
	 * @param response
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) {		
		User user = BeanUtils.asBean(request, User.class);
		String msg;
		try {
			userbiz.save(user);
			msg="用户保存成功";
		} catch (BizException e) {			
			e.printStackTrace();
			msg=e.getMessage();
		}
	}



	/**
	 * 修改查询
	 * 根据id查询用户信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void find(HttpServletRequest request, HttpServletResponse response) throws IOException {			
		String id=request.getParameter("id");
		User user = userbiz.findById(id);
		//将user返回给页面
		String userString = JSON.toJSONString(user);
		response.getWriter().append(userString);
	}


	/**
	 * 用户添加方法
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
	 * 查询显示
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
	 * 登录检测
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");//获取参数账号与密码
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
		if(user == null){//登录失败   账号或者密码错误
			request.setAttribute("msg", "账号或密码输入错误");
			request.getRequestDispatcher("login.jsp").forward(request, response);//进行重定向，刷新登录（login.jsp）界面
		}else{//登录成功
			request.getSession().setAttribute("loginedUser", user);//保存会话状态，表示该用户登录状态
			response.sendRedirect("index.jsp");//登录成功后，转到后台主界面
		}		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
