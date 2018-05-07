package com.tomo.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.tomo.common.DaoFactory;
import com.tomo.dao.ShopDao;
import com.tomo.entity.Shop;

/**
 * @author YHX
 * @date�� ���ڣ�2014-2-25 ʱ�䣺����1:25:11
 */
public class shopAdd extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		DecimalFormat df  = new DecimalFormat("#.00Ԫ");
		Shop shop = new Shop();
		// �����ļ��ϴ�
		if (ServletFileUpload.isMultipartContent(request)) { // ����Ƿ�Ϊmultipart������
			try {
				ServletFileUpload upload = new ServletFileUpload(
						new DiskFileItemFactory());

				List<FileItem> items = upload.parseRequest(request); // ��������

				int size = items == null ? 0 : items.size();
				String period=null;
				String subprice=null;
				for (int i = 0; i < size; i++) {
					
				
					FileItem item = (FileItem) items.get(i); // ��ȡ������Ϣ���е�ÿһ�����ݡ�
					if (item.isFormField()) {// �������ͨ����Ŀ
						if(item.getFieldName().equals("shopType")){
						    shop.setShopType(new String(item.getString().getBytes("ISO8859-1"), "UTF-8"));
						}
						  else if (item.getFieldName().equals("shopname")) {
							shop.setShopname(new String(item.getString().getBytes("ISO8859-1"), "UTF-8"));
						} else if (item.getFieldName().equals("userName")) {
							shop.setUserName(new String(item.getString().getBytes("ISO8859-1"), "UTF-8"));
						} else if (item.getFieldName().equals("description")) {
							shop.setDescription(new String(item.getString().getBytes("ISO8859-1"), "UTF-8"));
						} else if (item.getFieldName().equals("period")) {
							
								period = new String(item.getString().getBytes("ISO8859-1"), "UTF-8");
						}  else if (item.getFieldName().equals("price")) {
							//System.out.println("jiage");
								//shop.setPrice((df.format(Double.valueOf(item.getString()))));	
							  subprice = df.format(Double.valueOf(item.getString()));
						} else if (item.getFieldName().equals("userPhone")) {
							shop.setUserPhone(item.getString());
						} else if (item.getFieldName().equals("category")) {
							shop.setCategory(new String(item.getString().getBytes("ISO8859-1"), "UTF-8"));
						}else if (item.getFieldName().equals("picture")) {
							shop.setPicture(new String(item.getString().getBytes("ISO8859-1"), "UTF-8"));
						}
						shop.setPrice(subprice+period);	
					} else { // �ļ�
						String fileName = item.getName();// ����ϴ����ļ�ȫ·����
						// ��ȡ��׺��"aaa.bbb.png"
						System.out.println(fileName);
//						String str = fileName.substring(fileName
//								.lastIndexOf("/"+1));

						String contentType = item.getContentType();
						long sizeInBytes = item.getSize();

						InputStream uploadedStream = null;
						OutputStream os = null;
						try {
							uploadedStream = item.getInputStream();
							// ʹ��IO������������

							// ��ȡWeb�����Ŀ¼��ָ��Ŀ¼��ȫ·����
							File basePath = new File(this.getServletContext()
									.getRealPath("/images"));
							File dest = new File(basePath, fileName);
							os = new FileOutputStream(dest);

							IOUtils.copy(uploadedStream, os);
						} catch (Exception e) {
							response.getWriter().print("����ʧ��");
							e.printStackTrace();
						} finally {
							IOUtils.closeQuietly(uploadedStream);
							IOUtils.closeQuietly(os);
						}
					}
				}
				Date date = new Date();
				shop.setPut_time(date);
				ShopDao dao = DaoFactory.getInstance("shopDao",
						ShopDao.class);
				dao.save(shop);
				response.getWriter().print("�����ɹ�");
			} catch (Exception e) {
				response.getWriter().print("����ʧ��");
				e.printStackTrace();
			}
		} else {
			response.getWriter().print("����ʧ��");
		}

	}
}