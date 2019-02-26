package com.hframe.note.note.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hframe.basic.base.BaseServiceImpl;
import com.hframe.basic.common.SysConstants;
import com.hframe.basic.exception.PageException;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.root.dict.entity.DictionaryDTO;
import com.hframe.basic.util.DateUtils;
import com.hframe.basic.util.SecurityUtils;
import com.hframe.basic.util.StringUtils;
import com.hframe.note.HNoteUtils;
import com.hframe.note.HNoteUtils.Constants.HFriend_Outter_State;
import com.hframe.note.HNoteUtils.Constants.HNote_Show_Type;
import com.hframe.note.friend.dao.HFriendDao;
import com.hframe.note.note.dao.HNoteDao;
import com.hframe.note.note.entity.HNoteDTO;
import com.hframe.note.note.service.HNoteService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class HNoteServiceImpl extends BaseServiceImpl<HNoteDTO> implements HNoteService{
	
	@Autowired
	private HNoteDao dao;
	@Autowired
	private HFriendDao friendDao;
	
	@PostConstruct
	public void init(){
		super.dao = dao;
	}
	

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseServiceImpl#findById(java.lang.String)
	 */
	@Override
	public HNoteDTO findById(String sId) {
		
		if(StringUtils.isEmpty(sId)){
			throw new RuntimeException("参数不能为空");
		}
		
		HNoteDTO note = dao.findById(sId);
		if(null == note){
			throw new RuntimeException("笔记不存在");
		}
		
		//增加访问量
		dao.asyncAddReadCount(sId);
		
		return note;
	}


	@Override
	public List<HNoteDTO> getNoteNameByUser(String sUserId) {
		
		if(StringUtils.isEmpty(sUserId)){
			sUserId = RootUtils.getLoginUserId();
		}
		return dao.getNoteNameByUser(sUserId);
	}
	

	@Override
	public List<HNoteDTO> getNoteNameByCategory(String sCategoryId) {
		
		if(StringUtils.isEmpty(sCategoryId)){
			throw new RuntimeException("参数不能为空");
		}
		return dao.getNoteNameByCategory(sCategoryId);
	}
	

	@Override
	public List<HNoteDTO> getFriendsNodesByUser(String sUserId) {
		
		if(StringUtils.isEmpty(sUserId)){
			throw new RuntimeException("参数不能为空");
		}
		List<Object> friendsByUser = friendDao.getFriendIdsByUser(sUserId,HFriend_Outter_State.Normal);//获取我的所有好友id
		List<Object> userIds = friendDao.getFriendOutterState(sUserId, friendsByUser);//获取所有对我开放的好友id
		
		Example example = new Example(HNoteDTO.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andIn("sCreator", userIds);
		criteria.andNotEqualTo("iShowType", HNote_Show_Type.Myself);
		example.setOrderByClause(" dCreateDate DESC ");
			
		return dao.findList(example);
	}
	

	@Override
	public List<HNoteDTO> getNoteByEveryone() {
		
		Example example = new Example(HNoteDTO.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("iShowType", HNote_Show_Type.All);
		example.setOrderByClause(" dCreateDate DESC ");
		
		return dao.findList(example);
	}


	@Override
	public List<HNoteDTO> getNotesByFriend(String sUserId, String sFriendId) {
		
		if(StringUtils.isEmpty(sFriendId)){
			throw new RuntimeException("参数不能为空");
		}
		
		if(!friendDao.checkFriendOutterState(sUserId, sFriendId)){
			return new ArrayList<>();
		}
		
		Example example = new Example(HNoteDTO.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sCreator", sFriendId);
		criteria.andNotEqualTo("iShowType", HNote_Show_Type.Myself);
		
		return dao.findList(example);
	}


	@Override
	public HNoteDTO getShareNote(String sNoteId) {
		
		if(StringUtils.isEmpty(sNoteId)){
			throw new PageException("参数不正确");
		}
		
		String param = SecurityUtils.Crypto.decode(sNoteId);
		sNoteId = param.substring(0, 8);
		
		HNoteDTO note = new HNoteDTO();
		note.setsId(sNoteId);
		note.setiState(SysConstants.RecordStatus.Normal);
		note = HNoteUtils.NoteService.find(note);
		
		if(null == note){
			throw new PageException("笔记不存在");
		}
		//访问量增加
		dao.asyncAddReadCount(sNoteId);
		
		//非开放性笔记，链接有效期只有7天
		if(HNote_Show_Type.Myself == note.getiShowType() || HNote_Show_Type.Friend == note.getiShowType()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String dstr = param.substring(8);
			try {
				Date date = sdf.parse(dstr);
				Date now = new Date();
				
				Calendar calendar = Calendar.getInstance();
				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(date);
				
				calendar.set(Calendar.DAY_OF_MONTH, calendar2.get(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.MONTH, calendar2.get(Calendar.MONTH));
				calendar.set(Calendar.YEAR, calendar2.get(Calendar.YEAR));
				Integer days = DateUtils.betweenDays(calendar.getTime(),now);
				
				DictionaryDTO dictionaryValue = RootUtils.DictionaryService.getDictionaryValue("HorgnNote.NoteConfig", 2, 8);//获取数据库字典中配置的笔记有效期
				int len = 7;
				if(null != dictionaryValue){
					len = Integer.valueOf(dictionaryValue.getsDictValue());
				}
				
				if(days > len){
					throw new PageException("链接已失效");
				}
			} catch (ParseException e) {
				throw new PageException("链接不正确");
			}
		}
		
		return note;
	}


}
