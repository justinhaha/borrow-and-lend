/**
 * 
 */
package com.tomo.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.tomo.common.DbHelper;
import com.tomo.dao.ShopDao;
import com.tomo.dao.impl.common.BaseDaoImpl;
import com.tomo.entity.Shop;
import com.tomo.entity.common.PageModel;

/**
 * @author YHX
 * @date： 日期：2014-1-6 时间：下午3:07:18
 */
public class ShopDaoImpl extends BaseDaoImpl<Shop> implements ShopDao {
	@Override
	protected String getPKName() {
		return "shopId";
	}

	
	// 带条件的查询
	public PageModel<Shop> findByPager(String condition, String string,
			int pageSize, int pageNo) {
		//String search=null;
		PageModel<Shop> pm = new PageModel<Shop>(pageSize, pageNo);
		String sql2 = "";
		if (condition.equals("username")) {
			sql2 = "SELECT * FROM " + getTableName()
					+ " where  username = ?  ORDER BY " + getPKName()
					+ " DESC limit ?,?";
		} else if(condition.equals("category")){
			
			sql2 = "SELECT * FROM " + getTableName()
					+ " where shopType = "+"'变卖'"+" and category = ?   ORDER BY " + getPKName()
					+ " DESC limit ?,?";
			        System.out.println(sql2);
		}else {
			string = "%" + string + "%";		      
		      sql2 = "SELECT * FROM " + getTableName()
		              + " where shopType = "+"'变卖'"+" and shopname LIKE  ?  ORDER BY " + getPKName()
		              + " DESC limit ?,?";
		      System.out.println(string+" "+sql2);
		    }
			
		
			      
		    
		
		  
		Object[] paramValues = { string, (pageNo - 1) * pageSize, pageSize };
		Connection conn = null;
		try {
			conn = DbHelper.getConn();
			long count = total();
			if (count > 0) {
				pm.setRecordCount(count);
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
	//删除指定条件的数据
		public void delete(String username,int shopid) {
			Connection conn = null;
			if(!username.equals("") && username != null){
				String sql = "DELETE FROM "+ getTableName() + " WHERE username=?";
				try {
					conn = DbHelper.getConn();
					qr.update(conn, sql, username);
				} catch (SQLException e) {
					e.printStackTrace();
				} finally{
					DbHelper.close(conn);
				}
			}else {
				String sql = "DELETE FROM "+ getTableName() + " WHERE shopid=?";
				try {
					conn = DbHelper.getConn();
					qr.update(conn, sql,shopid);
				} catch (SQLException e) {
					e.printStackTrace();
				} finally{
					DbHelper.close(conn);
				}
			}
		}
	public PageModel<Shop> findByPager(String condition, String string,
			String aim,int pageSize, int pageNo) {
		//String search=null;
		PageModel<Shop> pm = new PageModel<Shop>(pageSize, pageNo);
		String sql2 = "";
		if (condition.equals("username")) {
			sql2 = "SELECT * FROM " + getTableName()
					+ " where  username = ?  ORDER BY " + getPKName()
					+ " DESC limit ?,?";
		} else if(condition.equals("category")){
			
			sql2 = "SELECT * FROM " + getTableName()
					+ " where shopType = "+"'出租'"+" and category = ?   ORDER BY " + getPKName()
					+ " DESC limit ?,?";
			        System.out.println(aim);
		}else if(condition.equals("search")) {
			string = "%" + string + "%";		      
		      sql2 = "SELECT * FROM " + getTableName()+" where  shopType = "+"'出租'"+" and shopname LIKE  ?  ORDER BY " + getPKName()
		              + " DESC limit ?,?";
		    }
		else{
			string = "出租";
			sql2 = "SELECT * FROM " + getTableName()+" where  shopType = ? "+"   ORDER BY " + getPKName()
            + " DESC limit ?,?";
		}
			
		
			      
		    
		
		  
		Object[] paramValues = { string, (pageNo - 1) * pageSize, pageSize };
		Connection conn = null;
		try {
			conn = DbHelper.getConn();
			long count = total();
			if (count > 0) {
				pm.setRecordCount(count);
				pm.setData(qr.query(conn, sql2, beanListHandler, paramValues));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbHelper.close(conn);
		}
		return pm;
	}


}
