package com.cs.sync.service;



public interface SyncService {
	
	
	public String getFileName(String url) throws Exception;
	
	public void download(String url1,String url2) throws Exception;
	
	public void upload(String url ,String path) throws Exception;
}
