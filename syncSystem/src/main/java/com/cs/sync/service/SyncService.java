package com.cs.sync.service;

import com.cs.sync.vo.SyncVo;

public interface SyncService {

	public void sync(SyncVo vo1,SyncVo vo2)throws Exception;
}
