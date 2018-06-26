package com.zj.boot_web.common.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.alibaba.fastjson.JSONObject;
import com.zj.boot_web.common.utils.AliOSS;
import com.zj.boot_web.common.utils.ComUtil;
import com.zj.boot_web.common.utils.Const;
import com.zj.boot_web.common.utils.OAuth2;
import com.zj.boot_web.common.utils.QrCode;


public class Threads extends Thread {
	
	private PageData userPd; // 用户信息
	private PageData data; // 数据
	
	private long sleep; // 休眠时间

	public Threads(PageData userPd, PageData data, long sleep) {
		super();
		this.userPd = userPd;
		this.data = data;
		this.sleep = sleep;
	}
	@Override
	public void run() {
		
		try {
			OAuth2 oAuth2 = new OAuth2();
			JSONObject accessToken = oAuth2.getAccessToken(Const.weChatAppID, Const.weChatAppSecret);
			String long_url = data.getString("long_url");
			String access_token = accessToken.getString("access_token");
			JSONObject object = oAuth2.getShortUrl(long_url, access_token);
			
			if (ComUtil.equals(object.getString("errmsg"), "ok")) {
				String imgUrl = "http://g.subaotec.com/ressources/img/static/wechat/qr-logo.jpg";
				String destPath = data.getString("destPath");
				String fileName = data.getString("fileName");
				
				Thread.sleep(sleep);
				String fileName2 = QrCode.encode(object.getString("short_url"), null, imgUrl, destPath, fileName, true);
				
				// 第1步、使用File类找到一个文件
		        File file = new File(destPath + fileName2); // 声明File对象
		        // 第2步、通过子类实例化父类对象
		        InputStream input = new FileInputStream(file); // 通过对象多态性，进行实例化
		        fileName = file.getName();
				String suffixName = fileName.substring(fileName.lastIndexOf("."));
				//String date = DateUtil.getYyyyMM();
		        //String uploadPath = "fuao/document/upload/qr/" + date + "/";
				String uploadPath = data.getString("uploadPath");
		        String key = uploadPath + fileName + suffixName;
		        AliOSS.uploadFileIO("subao", key, input);
			}
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
