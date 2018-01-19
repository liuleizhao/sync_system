package com.cs.sync.service.impl;  
  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.sync.service.SyncService;
import com.cs.sync.util.WebServiceUtils;
import com.cs.sync.vo.SyncVo;

@Service
@Transactional
public class SyncServiceImpl implements SyncService
{  
	private static Logger logger = LoggerFactory.getLogger(SyncServiceImpl.class);

	@Override
	public void sync(SyncVo vo1,SyncVo vo2) throws Exception {
		
		logger.info("开始导出："+vo1.getUrl());
		Object[] opAddEntryArgs_1 = new Object[] {vo1.getJkid()[0],vo1.getAccount(),vo1.getPassword()};
		Class<?>[] classes_1 = new Class<?>[] {String.class};
        String url_1 = vo1.getUrl();
        String namespace_1 = vo1.getNamespace();
        String method_1 = vo1.getMethod()[0];
		
		String result_1 = (String) WebServiceUtils.sendRequest(url_1, namespace_1, method_1, opAddEntryArgs_1, classes_1);
		System.out.println(result_1);
		if (result_1 == null||!"1".equals(getValue("code", result_1))) {
			logger.info("导出失败："+vo1.getUrl()+" 接口："+method_1+" 结果："+getValue("message", result_1));
			return;
		}
		
		String fileName = getValue("message", result_1);
		
		logger.info("开始下载："+vo1.getUrl()+" 文件名： "+fileName);
		Object[] opAddEntryArgs_2 = new Object[]{vo1.getJkid()[1],vo1.getAccount(),vo1.getPassword(),fileName};  
		Class<?>[] classes_2 = new Class[]{byte[].class};
        String url_2 = vo1.getUrl();
        String namespace_2 = vo1.getNamespace();
        String method_2 = vo1.getMethod()[1];
		
		byte bytes[] = (byte[]) WebServiceUtils.sendRequest(url_2, namespace_2, method_2, opAddEntryArgs_2, classes_2);
        
		if(bytes== null||bytes.length== 0){
			logger.info("下载失败："+vo1.getUrl()+" 接口："+method_2+" 文件名： "+fileName);
			return;
		}
		
		logger.info("开始上传："+vo2.getUrl());
        Object[] opAddEntryArgs_3 = new Object[]{vo2.getJkid()[2],vo2.getAccount(),vo2.getPassword(),fileName, bytes};  
        Class<?>[] classes_3 = new Class<?>[]{ String.class };  
        String url_3 = vo2.getUrl();
        String namespace_3 = vo2.getNamespace();
        String method_3 = vo2.getMethod()[2];
        
		String result_2 = (String)WebServiceUtils.sendRequest(url_3, namespace_3, method_3, opAddEntryArgs_3, classes_3);
		logger.info("上传结果："+vo2.getUrl()+" 接口："+method_3+" 结果："+getValue("message", result_2));
	}

	
	// 获取对应节点中的内容
	public static String getValue(String colName, String xmlDoc) {
		int startIndex = -1;
		int endIndex = -1;
		startIndex = xmlDoc.indexOf("<" + colName + ">");
		endIndex = xmlDoc.indexOf("</" + colName + ">");
		if (startIndex >= 0 && endIndex >= 0) {
			return xmlDoc.substring(startIndex + colName.length() + 2, endIndex);
		} else {
			return null;
		}
	}
}  