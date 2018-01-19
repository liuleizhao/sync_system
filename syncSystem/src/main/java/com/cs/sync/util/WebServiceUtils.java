package com.cs.sync.util;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

/**
 * WebService请求封装类
 * 
 * @author
 * 
 */
public class WebServiceUtils {

	public static Object sendRequest(String url, String namespace,
			String method, Object[] opAddEntryArgs, Class<?>[] classes) {
		Object result = null;
		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			EndpointReference targetEPR = new EndpointReference(url);
			options.setTo(targetEPR);
			options.setTimeOutInMilliSeconds(180 * 1000);
			QName opAddEntry = new QName(namespace, method);
			result = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs,
					classes)[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
