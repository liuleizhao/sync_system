package com.cs.sync.timer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cs.common.utils.DateUtils;
import com.cs.sync.service.SyncService;
import com.cs.sync.service.impl.SyncServiceImpl;


public class SyncTaskTimer {
	private static Logger logger = LoggerFactory.getLogger(SyncTaskTimer.class);
	
	private static String motUrl = "http://localhost:8080/mot/services/syncData";
	private static String pmotUrl = "http://localhost:8080/pmot/services/syncData";
	
	@Autowired
	private SyncService syncService;
	
	public SyncTaskTimer() {
		
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(Thread.currentThread().getContextClassLoader().getResource("url.properties").getPath())));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//返回获取的值
		motUrl = prop.getProperty("motUrl");
		pmotUrl = prop.getProperty("pmotUrl");
		
		System.out.println(DateUtils.getDateTime()+"::定时任务实例化");
		logger.info(DateUtils.getDateTime()+"::定时任务实例化");
	}
	
	public void mot2pmot(){
		try {
			logger.info(DateUtils.getDateTime()+"::外网导入专网开始");
			syncService.download(motUrl,pmotUrl);
			logger.info(DateUtils.getDateTime()+"::外网导入专网完成");
		} catch (Exception e) {
			logger.error(DateUtils.getDateTime()+"::外网导入专网失败" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void pmot2mot(){
		try {
			logger.info(DateUtils.getDateTime()+"::专网导出外网开始");
			syncService.download(pmotUrl,motUrl);
			logger.info(DateUtils.getDateTime()+"::专网导出外网完成");
		} catch (Exception e) {
			logger.error(DateUtils.getDateTime()+"::专网导出外网失败" + e.getMessage());
			e.printStackTrace();
		}
	}

}
