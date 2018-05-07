/**
 * 
 */
package com.tomo.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tomo.common.DbHelper;
import com.tomo.dao.MessageDao;
import com.tomo.dao.impl.common.BaseDaoImpl;
import com.tomo.entity.ItemList;
import com.tomo.entity.Message;
//import com.tomo.entity.Messages;
import com.tomo.entity.common.PageModel;

/**
 * @author YHX
 * @date： 日期：2014-1-6 时间：下午3:12:22
 */
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao {
	

	@Override
	protected String getPKName() {
		return "messageId";
	}

	@Override
	public PageModel<Message> findByPager(String username, int pageSize,int pageNo) {
		    PageModel<Message> messages = new PageModel<Message>();
			List<Map<String,Object>> receivernames = null;
			//PageModel<Message> pm = new PageModel<Message>(pageSize, pageNo);

			String sql2 = "SELECT DISTINCT receivename FROM " + getTableName() + "  where  username = ?  ORDER BY "
					+ getPKName() + " DESC limit ?,?";
					
			Object[] paramValues = {username, (pageNo - 1) * pageSize, pageSize };
			Connection conn = null;
			try {
				conn = DbHelper.getConn();
				long count = total();
				if (count > 0) {
					//pm.setRecordCount(count);
					//pm.setData(qr.query(conn, sql2, beanListHandler, paramValues));
					receivernames = this.getListMap(sql2, paramValues);
					System.out.println(receivernames.toString());
					//PageModel<Message> aa = new PageModel();
					List<Message> bb = new ArrayList<Message>();
					for(Map<String,Object> map:receivernames)
					{
						for (String k : map.keySet())
					      {
					        System.out.println(k + " : " + map.get(k));
					      
					        String receivename = (String) map.get(k);
					        System.out.println("测试" + " : " + receivename);
					        Message msg = firstMsg(username,receivename);
							bb.add(msg);
					      }
					    }
					messages.setData(bb);
					}
				
						
						
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DbHelper.close(conn);
			}
			return messages;
		}
		
	
	public PageModel<Message> findByPager(String username,String receivename) {
		PageModel<Message> pm = new PageModel<Message>();

		String sql2 = "(SELECT * FROM "+ getTableName() + "  where  username = ? and receivename = ? ) UNION " 
				+" (SELECT * FROM " + getTableName() + "  where  username = ? and receivename = ? ) ORDER BY "
				+ getPKName() + " ASC ";
		Object[] paramValues = {username,receivename,receivename,username};
		Connection conn = null;
		try {
			conn = DbHelper.getConn();
			long count = total();
			if (count > 0) {
				pm.setRecordCount(count);
				pm.setReceivename(receivename);
				pm.setData(qr.query(conn, sql2, beanListHandler, paramValues));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbHelper.close(conn);
		}
		return pm;
	}

	@Override
	public void delete(String username) {
		Connection conn = null;
		// delete from 表名 wher 主键列名=值
		String sql = "DELETE FROM "+ getTableName() + " where  receivename =?";
		try {
			conn = DbHelper.getConn();
			qr.update(conn, sql, username);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DbHelper.close(conn);
		}
	}
	public Message firstMsg(String username,String receivename){
		Message msg = new Message();
		String sql2 = "(SELECT * FROM "+ getTableName() + "  where  username = ? and receivename = ? ) UNION " 
				+" (SELECT * FROM " + getTableName() + "  where  username = ? and receivename = ? ) ORDER BY "
				+ getPKName() + " DESC limit ? ";
		Object[] paramValues = {username,receivename,receivename,username,1};
		Connection conn = null;
		try {
			conn = DbHelper.getConn();
			long count = total();
			if (count > 0) {
				msg = qr.query(conn, sql2, beanHandler, paramValues);
				msg.setReceivename(receivename);
				msg.setUsername(username);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbHelper.close(conn);
		}
		return msg;
	}
	
    
}
