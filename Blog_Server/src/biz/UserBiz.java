package biz;

import java.util.ArrayList;
import java.util.List;

import bean.User;
import controller.exception.BizException;
import dao.DBHelper;
import util.BeanUtils;

public class UserBiz {
	
	
	
	
	/**
	 * ��¼����
	 * @param username
	 * @param userpwd
	 * @return���ص�¼�ɹ����û�����
	 * 
	 * ʹ��userbiz�������֮ǰֱ����loginservlet��ʹ��DBHelper�������ݿ��ȡ�û���Ϣ����
	 * �������ڣ�
	 * 1��֮ǰֱ�ӻ�ȡ������С���򣬵�ҵ�������󣬻�������ݴ洢������������
	 * 2�������Ժ�Գ���Ӧ��������Ҫ�Ķ����Ͳ���Ҫ�Ķ�������ݿ�Ĵ��룬ʹ����������
	 * 3��userbiz����ҵ��㣬servlet����jsp���Ʋ㣬���Ժ����л��в�ͬ��ר�ŵ��˸���ҵ�������Ʋ㣬
	 * ��ζ�Ÿ���ҵ�������ڽ������ݿ����ӵĴ����װ��ֱ�ӷ�������jsp���Ʋ�ĸ���������д���룬����ʹ�÷���������ǰ��Ӧ��������
	 */
	public User login(String username,String userpwd) throws BizException{
		
		if(username == null || username.trim().isEmpty()){
			throw new BizException("��������ȷ���˺�");
		}
		if(userpwd == null || userpwd.trim().isEmpty()){
			throw new BizException("��������ȷ������");
		}
		
		String sql ="select * from user where account = ? and pwd = ?";
		return  DBHelper.unique(sql, User.class,username,userpwd);
		
	}

		
	
	/**
	 * �û���Ϣ��ѯ����
	 * ��ѯ���ú��ڽ�����ʾ�û���Ϣ
	 */
	public List<User> findAll(){
		return DBHelper.select("select * from user", User.class);
	}
	
	

	/**
	 * ����û�����
	 * @param user
	 * @throws BizException 
	 */
	public void add(User user) throws BizException {
		//��֤�û���
		if(user.getName() == null || user.getName().trim().isEmpty())
			throw new BizException("�������û���");		
		//��֤�˺�
		if(user.getAccount() == null || user.getAccount().trim().isEmpty() || user.getAccount().matches("[0-9a-z]*") == false)
			throw new BizException("������Ϸ��˺ţ���ֹ���������ַ�");
		//��֤�绰
		if(user.getTel() == null || user.getTel().trim().isEmpty() || user.getTel().matches("[0-9]{11}") == false)
			throw new BizException("������11λ����ϵ�绰");
		//��֤����		
		if(user.getPwd() == null || user.getPwd().trim().isEmpty())
			throw new BizException("����������");
				
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
			throw new BizException("�������˺�");
		}
		if(user.getName() == null || user.getName().trim().isEmpty()){
			throw new BizException("����������");
		}			
		DBHelper.update("update user set account=? ,name=? ,tel=? where id=?", user.getAccount(),user.getName(),user.getTel(),user.getId());
		
	}



	public void delete(User user) throws BizException {
		int i =DBHelper.update("delete from user where id=?", user.getId());
		if(i == 0)
			throw new BizException("ɾ��ʧ��");
	}
	
	
	
	
	
	
	
	
}
