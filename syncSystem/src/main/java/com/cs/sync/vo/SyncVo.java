package com.cs.sync.vo;

import java.util.Map;

/**
 * webservice请求 实体
 * 
 * @author Administrator
 * 
 */
public class SyncVo {

	private String ip;

	private String account;

	private String password;

	private String url;

	private String namespace;

	private String[] method;

	private String[] jkid;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String[] getMethod() {
		return method;
	}

	public void setMethod(String[] method) {
		this.method = method;
	}

	public String[] getJkid() {
		return jkid;
	}

	public void setJkid(String[] jkid) {
		this.jkid = jkid;
	}

}
