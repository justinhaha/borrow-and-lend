/**
 * 
 */
package com.tomo.entity;

import java.util.Date;

/**
 * @author YHX
 * @date： 日期：2014-1-6 时间：下午2:46:33
 */
public class Shop {
	private int shopId;
	private String shopname;
	private String description;
	private String userName;
	private String userPhone;
	private String category;
	private String picture;
	private String price;
	private Date put_time;
	private String shopType;

	
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
		System.out.println("shopelse"+price);
		
	}
	public Date getPut_time() {
		return put_time;
	}
	public void setPut_time(Date put_time) {
		this.put_time = put_time;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	@Override
	public String toString() {
		return "Shop [shopId=" + shopId + ", shopname=" + shopname
				+ ", description=" + description + ", userName=" + userName
				+ ", userPhone=" + userPhone + ", category=" + category
				+ ", picture=" + picture + ", price=" + price + ", put_time="
				+ put_time + "]";
	}
	
}
