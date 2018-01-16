package com.cs.sync.service.impl;  
  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.sync.service.SyncService;

@Service
@Transactional
public class SyncServiceImpl implements SyncService
{  

	@Override
	public void download(String url1,String url2) throws Exception {
		
		String fileName = getFileName(url1);
		if(fileName == null){
			return;
		}
		
		RPCServiceClient serviceClient = new RPCServiceClient();  
        Options options = serviceClient.getOptions();  
        EndpointReference targetEPR = new EndpointReference(url1);  
        options.setTo(targetEPR);
        options.setTimeOutInMilliSeconds(180*1000);//设置超时(单位是毫秒)
        QName opAddEntry = new QName("http://tempuri1.org/", "downloadFile");  
        Object[] opAddEntryArgs = new Object[]{fileName};  
        
        System.out.println(new Date()+" 文件下载开始");  
        byte bytes[] = (byte[]) serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, new Class[]{byte[].class})[0];
        
        
        checkPath("C:/_DATA/");
        String path = "C:\\_DATA\\download"+new Date().getTime()+".zip";
        FileOutputStream fileOutPutStream = new FileOutputStream(path);  
         
        //将字节数组bytes中的数据，全部写入输出流fileOutPutStream中  
        fileOutPutStream.write(bytes);  
        fileOutPutStream.flush();
        fileOutPutStream.close();  
        System.out.println(new Date()+" 文件下载完成"); 
        upload(url2,path);
	}

	@Override
	public void upload(String url ,String path) throws Exception {
		RPCServiceClient serviceClient = new RPCServiceClient();  
        Options options = serviceClient.getOptions();  
        options.setTimeOutInMilliSeconds(180*1000);//设置超时(单位是毫秒)
        EndpointReference targetEPR = new EndpointReference(url);  
        options.setTo(targetEPR);
         
        //=================测试文件上传==================================  
        
        //String filePath = "D:\\我是下载的文件.zip";  
        FileInputStream fis = new FileInputStream(path);  
         
        // 创建保存要上传的图像文件内容的字节数组  
        byte[] buffer = new byte[fis.available()];  
          
        //将输入流fis中的数据读入字节数组buffer中  
        fis.read(buffer);
        
        //设置入参（1、文件名，2、文件字节流数组）  
        String fileName = "upload"+new Date().getTime()+".zip";
        Object[] opAddEntryArgs = new Object[]{fileName, buffer};  
          
        //返回值类型  
        Class<?>[] classes = new Class<?>[]{ String.class };  
          
        //指定要调用的方法名及WSDL文件的命名空间  
        QName opAddEntry = new QName("http://tempuri1.org/","uploadFile");  
          
        //关闭流  
        fis.close();  
       
        //执行文件上传  
        System.out.println(new Date()+" 文件上传开始");  
        String returnValue = (String) serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs, classes)[0];  
        System.out.println(new Date()+" 文件上传结束，返回值="+returnValue);
        
        
        if(returnValue!=null){
			String code = getValue("code",returnValue);
			if("1".equals(code)){
				 deleteDirectory(new File(path));
			}
		}
       
	}



	@Override
	public String getFileName(String url) throws Exception {
		String result = null;
		// 使用RPC方式调用WebService
					RPCServiceClient serviceClient = new RPCServiceClient();
					Options options = serviceClient.getOptions();
					// 指定调用WebService的URL
					EndpointReference targetEPR = new EndpointReference(url);
					options.setTo(targetEPR);
					options.setTimeOutInMilliSeconds(180*1000);//设置超时(单位是毫秒)
					// 在创建QName对象时，QName类的构造方法的第一个参数表示WSDL文件的命名空间名，也就是<wsdl:definitions>元素的targetNamespace属性值
					// // 指定要调用的getWorld方法及WSDL文件的命名空间.....
					QName opAddEntry = new QName("http://tempuri1.org/", "exportFile");
					//
					// 指定getGreeting方法的参数值，如果有多个，继续往后面增加即可，不用指定参数的名称
					Object[] opAddEntryArgs = new Object[] { };
					// 返回参数类型，这个和axis1有点区别
					// invokeBlocking方法有三个参数，其中第一个参数的类型是QName对象，表示要调用的方法名；
					// 第二个参数表示要调用的WebService方法的参数值，参数类型为Object[]；
					// 第三个参数表示WebService方法的返回值类型的Class对象，参数类型为Class[]。
					// 当方法没有参数时，invokeBlocking方法的第二个参数值不能是null，而要使用new Object[]{}
					// 如果被调用的WebService方法没有返回值，应使用RPCServiceClient类的invokeRobust方法，
					// 该方法只有两个参数，它们的含义与invokeBlocking方法的前两个参数的含义相同
					// 指定getGreeting方法返回值的数据类型的Class对象.....
					Class[] classes = new Class[] { String.class };
					// 调用getGreeting方法并输出该方法的返回值.......
					result = (String) serviceClient.invokeBlocking(opAddEntry,
							opAddEntryArgs, classes)[0];
					System.out.println(result);
					/*// 下面是调用getHelloWorld方法的代码
					opAddEntry = new QName("http://tempuri1.org/", "exportFile");
					System.out.println(serviceClient.invokeBlocking(opAddEntry,
							new Object[] {}, classes)[0]);*/
					if(result!=null){
						String code = getValue("code",result);
						if("1".equals(code)){
							return getValue("message",result);
						}
					}
					return null;
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
		
		/**
		 * 检查路径是否存在，不存在创建路径
		 * @param path
		 * @throws IOException
		 */
		private static void checkPath(String path) throws IOException {
			File tmpFile = new File(path);
			if (!tmpFile.exists()) {
				tmpFile.mkdirs();
				tmpFile.createNewFile();
			}
		}
		private  void deleteDirectory(File file) {  
	        if (file.isFile()) {// 表示该文件不是文件夹  
	            file.delete();  
	        } else {  
	            // 首先得到当前的路径  
	            String[] childFilePaths = file.list();  
	            for (String childFilePath : childFilePaths) {
	                File childFile = new File(file.getAbsolutePath() + "/" + childFilePath);  
	                deleteDirectory(childFile);  
	            }  
	            file.delete();  
	        }  
	    }
		
		public static void main(String[] args) {
			try {
				new SyncServiceImpl().download("http://localhost:8080/mot/services/syncData","http://localhost:8080/pmot/services/syncData");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}  