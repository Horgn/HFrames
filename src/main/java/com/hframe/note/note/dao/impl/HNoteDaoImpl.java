package com.hframe.note.note.dao.impl;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.hframe.basic.base.BaseDaoImpl;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.util.StringUtils;
import com.hframe.note.note.dao.HNoteDao;
import com.hframe.note.note.entity.HNoteDTO;
import com.hframe.note.note.mapper.HNoteMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Repository
public class HNoteDaoImpl extends BaseDaoImpl<HNoteDTO> implements HNoteDao{
	
	@Autowired
	private HNoteMapper mapper;
	
	@PostConstruct
	public void init(){
		super.mapper = mapper;
	}

	@Override
	public List<HNoteDTO> getNoteNameByUser(String sUserId) {
		
		if(StringUtils.isEmpty(sUserId)){
			sUserId = RootUtils.getLoginUserId();
		}
		
		Example example = new Example(HNoteDTO.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("sCreator", sUserId);
		example.setOrderByClause("iOrder desc");
		example.selectProperties("sId", "sNoteTitle", "sCategoryId", "iOrder");
		
		return super.findList(example);
		
//		String sql = " select sId,sNoteTitle,sCategoryId,iOrder from "+getTableName()+" where iState=1 and sCreator='"+sUserId+"' order by iOrder desc  ";
//		return super.selectListBySql(sql);
	}

	@Override
	public List<HNoteDTO> getNoteNameByCategory(String sCategoryId) {
		
		if(StringUtils.isEmpty(sCategoryId)){
			throw new RuntimeException("参数不能为空");
		}
		
		Example example = new Example(HNoteDTO.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("sCategoryId", sCategoryId);
		example.setOrderByClause("iOrder desc");
		example.selectProperties("sId", "sNoteTitle", "sCategoryId", "iOrder");
		
		return super.findList(example);
		
//		String sql = " select sId,sNoteTitle,sCategoryId,iOrder from "+getTableName()+" where iState=1 and sCategoryId='"+sCategoryId+"' order by iOrder desc  ";
//		return super.selectListBySql(sql);
	}

	@Override
	@Async
	public void asyncAddReadCount(String sNoteId) {
		
		if(StringUtils.isEmpty(sNoteId)){
			return;
		}
		
		Lock lock = new ReentrantLock();// 锁对象
		lock.lock();//获得锁
		
		try {
			String sql = " UPDATE HN_Note SET iReadCount=iReadCount+1 WHERE sId='"+sNoteId+"' AND iState=1 ";
			super.updateBySql(sql);
		} finally {
			lock.unlock();//释放锁
		}
	}
	
	

}
