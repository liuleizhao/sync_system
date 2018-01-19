package com.cs.sync.timer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cs.sync.service.SyncService;
import com.cs.sync.service.impl.SyncServiceImpl;
import com.cs.sync.vo.SyncVo;

public class SyncTimer {
	private static Logger logger = LoggerFactory.getLogger(SyncTimer.class);

	private static SyncVo mot = null;
	private static SyncVo pmot = null;

	@Autowired
	private SyncService syncService;

	public SyncTimer() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(Thread.currentThread().getContextClassLoader().getResource("url.properties").getPath())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mot = new SyncVo();
		mot.setIp(prop.getProperty("m_ip"));
		mot.setAccount(prop.getProperty("m_account"));
		mot.setNamespace(prop.getProperty("m_namespace"));
		mot.setPassword(prop.getProperty("m_password"));
		mot.setUrl(prop.getProperty("m_url"));
		mot.setJkid(prop.getProperty("m_jkid").split(","));
		mot.setMethod(prop.getProperty("m_method").split(","));
		
		pmot = new SyncVo();
		pmot.setIp(prop.getProperty("p_ip"));
		pmot.setAccount(prop.getProperty("p_account"));
		pmot.setNamespace(prop.getProperty("p_namespace"));
		pmot.setPassword(prop.getProperty("p_password"));
		pmot.setUrl(prop.getProperty("p_url"));
		pmot.setJkid(prop.getProperty("p_jkid").split(","));
		pmot.setMethod(prop.getProperty("p_method").split(","));

		System.out.println("定时任务实例化");
	}

	public void mot2pmot() {
		if(isConnect(pmot.getIp())&&isConnect(mot.getIp())){
				try {
					syncService.sync(mot, pmot);
				} catch (Exception e) {
					e.printStackTrace();
					 logger.error(e.getMessage());
				}
		}
	}

	public void pmot2mot() {
		if(isConnect(pmot.getIp())&&isConnect(mot.getIp())){
			try {
				syncService.sync(pmot,mot);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}
	
    /**
    * 传入需要连接的IP，返回是否连接成功
    * @param remoteInetAddr
    * @return
    */
   public static boolean isConnect(String remoteInetAddr) {
       boolean reachable = false; 
       try {   
           InetAddress address = InetAddress.getByName(remoteInetAddr); 
           reachable = address.isReachable(5000);  
           } catch (Exception e) {  
        	   logger.error(e.getMessage());
           }  
       return reachable;
   }
   
   public static void main(String[] args) {
	   SyncVo mot = new SyncVo();
	   
		mot.setIp("127.0.0.1");
		mot.setAccount("sync");
		mot.setNamespace("http://tempuri1.org/");
		mot.setPassword("63ad9d34f3503826e5f649ae6b7ac92c");
		mot.setUrl("http://localhost:8080/mot/services/syncData");
		mot.setJkid(new String[]{"JK99","JK98","JK97"});
		mot.setMethod(new String[]{"exportFile","downloadFile","uploadFile"});
		
		SyncVo pmot = new SyncVo();
		pmot.setIp("192.168.1.101");
		pmot.setAccount("x");
		pmot.setNamespace("http://tempuri1.org/");
		pmot.setPassword("");
		pmot.setUrl("http://192.168.1.101:8080/pmot/services/syncData");
		pmot.setJkid(new String[]{"JK501","JK502","JK503"});
		pmot.setMethod(new String[]{"exportFile","downloadFile","uploadFile"});
  
		if(isConnect(pmot.getIp())&&isConnect(mot.getIp())){
			try {
				logger.info("外网导入专网开始");
				new SyncServiceImpl().sync(mot, pmot);
				logger.info("外网导入专网完成");
			} catch (Exception e) {
				logger.error("外网导入专网失败"+ e.getMessage());
				e.printStackTrace();
			}
		}else{
			logger.info("网络不通");
		}
   }
}