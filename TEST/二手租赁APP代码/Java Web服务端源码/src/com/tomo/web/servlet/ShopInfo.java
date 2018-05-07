package com.tomo.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.tomo.common.DaoFactory;
import com.tomo.dao.ShopDao;
import com.tomo.dao.UsersDao;
import com.tomo.entity.ItemList;
import com.tomo.entity.Shop;
import com.tomo.entity.Users;
import com.tomo.entity.common.PageModel;

public class ShopInfo extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String pn = request.getParameter("pageNo");
		//查询的用户名
		String value = request.getParameter("value");
		//查询条件，根据分类查询还是根据用户名查询
		String condition = request.getParameter("condition");
		//查询本校的还是查询全部信息
		String school = request.getParameter("school");
		String aim = request.getParameter("aim");
		System.out.println(aim);
		//int aim = Integer.valueOf(aimm).intValue(); 
		int pageSize = 10;
		int pageNo = 1;
		PageModel<Shop> pm;
		List<ItemList> itemLists = new ArrayList<ItemList>();
		if (pn != null && !"".equals(pn)) {
			pageNo = Integer.parseInt(pn);
		}
		//商品的dao工厂，加载mysql的数据库驱动，跟数据库建立连接
		ShopDao shopDao = DaoFactory.getInstance("shopDao", ShopDao.class);
		if(aim.equals("sell")){
			pm = shopDao.findByPager(condition,value,pageSize, pageNo);
		}
		else if(aim.equals("rent")){
			 pm = shopDao.findByPager(condition,value,aim,pageSize, pageNo);
			 //System.out.println(aim);
		}
		else {
		    pm = shopDao.findByPager(condition,value,pageSize, pageNo);
		    //System.out.println(aim);
		}
		
		
		
		List<Shop> list = pm.getData();
		if (list != null) {
			for (Shop shop : list) {
				//用户的dao工厂，加载mysql的数据库驱动，跟数据库建立连接
				UsersDao usersDao = DaoFactory.getInstance("usersDao",
						UsersDao.class);
				String sql = "select * from users where username = ?";
				String paramValues = shop.getUserName();
				Users user = usersDao.findUnique(sql, paramValues);
				ItemList itemList = new ItemList();
				itemList.setCategory(shop.getCategory());
				System.out.println(user.getCourt());
				itemList.setCourt(user.getCourt());
				itemList.setDescription(shop.getDescription());
				itemList.setPicture(shop.getPicture());
				itemList.setPrice(shop.getPrice());
				itemList.setPut_time(shop.getPut_time());
				itemList.setSchool(user.getSchool());
				itemList.setShopname(shop.getShopname());
				itemList.setShopId(shop.getShopId());
				itemList.setUserName(user.getUserName());
				itemList.setUserPhone(shop.getUserPhone());
				itemList.setUserPhone(shop.getUserPhone());
				if(school.equals("") || school ==null){
					itemLists.add(itemList);
				}else if(school.equals(itemList.getSchool())){
					itemLists.add(itemList);
				}else {
					System.out.println("schoollll"+school);
				}
			}
		}
		PageModel<ItemList> pm2 = new PageModel<ItemList>();
		pm2.setData(itemLists);
		pm2.setPageNo(pm.getPageNo());
		pm2.setPageSize(pm.getPageSize());
		pm2.setRecordCount(pm.getRecordCount());
		//把java对象包装成json对象，通过json发送
		JSONObject jsonObject = new JSONObject(pm2);
		response.setContentType("text/html;charset=UTF-8");
		//response.getWriter().println(value);
		response.getWriter().println(jsonObject.toString());
		response.getWriter().flush();
		
	}
}
