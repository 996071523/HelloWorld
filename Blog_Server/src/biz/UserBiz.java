package biz;

import java.util.ArrayList;
import java.util.List;

import bean.User;
import controller.exception.BizException;
import dao.DBHelper;
import util.BeanUtils;

public class UserBiz {
	
	
	
	
	/**
	 * 登录方法
	 * @param username
	 * @param userpwd
	 * @return返回登录成功的用户对象
	 * 
	 * 使用userbiz类相比于之前直接在loginservlet中使用DBHelper连接数据库获取用户信息而言
	 * 优势在于：
	 * 1、之前直接获取适用于小程序，当业务量过大，会造成数据存储过大，垃圾过多
	 * 2、若在以后对程序应用需求需要改动，就不需要改动相关数据库的代码，使操作更方便
	 * 3、userbiz属于业务层，servlet属于jsp控制层，在以后工作中会有不同的专门的人负责业务层与控制层，
	 * 意味着负责业务层的人在将与数据库连接的代码封装后直接发给负责jsp控制层的负责人来编写代码，现在使用方便我们提前适应工作环境
	 */
	public User login(String username,String userpwd) throws BizException{
		
		if(username == null || username.trim().isEmpty()){
			throw new BizException("请输入正确的账号");
		}
		if(userpwd == null || userpwd.trim().isEmpty()){
			throw new BizException("请输入正确的密码");
		}
		
		String sql ="select * from user where account = ? and pwd = ?";
		return  DBHelper.unique(sql, User.class,username,userpwd);
		
	}

		
	
	/**
	 * 用户信息查询方法
	 * 查询调用后在界面显示用户信息
	 */
	public List<User> findAll(){
		return DBHelper.select("select * from user", User.class);
	}
	
	

	/**
	 * 添加用户方法
	 * @param user
	 * @throws BizException 
	 */
	public void add(User user) throws BizException {
		//验证用户名
		if(user.getName() == null || user.getName().trim().isEmpty())
			throw new BizException("请输入用户名");		
		//验证账号
		if(user.getAccount() == null || user.getAccount().trim().isEmpty() || user.getAccount().matches("[0-9a-z]*") == false)
			throw new BizException("请输入合法账号，禁止含有特殊字符");
		//验证电话
		if(user.getTel() == null || user.getTel().trim().isEmpty() || user.getTel().matches("[0-9]{11}") == false)
			throw new BizException("请输入11位的联系电话");
		//验证密码		
		if(user.getPwd() == null || user.getPwd().trim().isEmpty())
			throw new BizException("请输入密码");
				
		String sql = "insert into user (name,account,pwd,tel) values(?,?,?,?)";
		DBHelper.insert(sql, user.getName(),user.getAccount(),user.getPwd(),user.getTel());
	}



	public Object find(User user) {
		ArrayList<Object> params = new ArrayList<Object>();
		String sql = "select * from user where 1=1 ";
		if(user.getName() != null && user.getName().trim().isEmpty() == false){
			sql += "and name like ?";
			params.add("%"+user.getName()+"%");
		}
		if(user.getAccount() != null && user.getAccount().trim().isEmpty() == false){
			sql += "and account like ?";
			params.add("%"+user.getAccount()+"%");
		}
		if(user.getTel() != null && user.getTel().trim().isEmpty() == false){
			sql += "and tel like ?";
			params.add("%"+user.getTel()+"%");
		}
		return DBHelper.select(sql, params);
	}


	public User findById(String id) {
		return DBHelper.unique("select * from user where id=?", User.class,id);
	}



	public void save(User user) throws BizException {		
		if(user.getAccount() == null || user.getAccount().trim().isEmpty()){
			throw new BizException("请输入账号");
		}
		if(user.getName() == null || user.getName().trim().isEmpty()){
			throw new BizException("请输入姓名");
		}			
		DBHelper.update("update user set account=? ,name=? ,tel=? where id=?", user.getAccount(),user.getName(),user.getTel(),user.getId());
		
	}



	public void delete(User user) throws BizException {
		int i =DBHelper.update("delete from user where id=?", user.getId());
		if(i == 0)
			throw new BizException("删除失败");
	}
	
	
	
	
	
	
	
	
}
