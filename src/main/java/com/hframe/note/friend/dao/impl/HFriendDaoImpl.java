package com.hframe.note.friend.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hframe.basic.base.BaseDaoImpl;
import com.hframe.basic.root.user.dao.UserDao;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.util.StringUtils;
import com.hframe.note.HNoteUtils.Constants.HFriend_Inner_State;
import com.hframe.note.friend.dao.HFriendDao;
import com.hframe.note.friend.entity.HFriendDTO;
import com.hframe.note.friend.mapper.HFriendMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Repository
public class HFriendDaoImpl extends BaseDaoImpl<HFriendDTO> implements HFriendDao{
	
	@Autowired
	private HFriendMapper mapper;
	@Autowired
	private UserDao userDao;
	
	@PostConstruct
	public void init(){
		super.mapper = mapper;
	}

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseDaoImpl#checkExist(com.hframe.basic.base.BaseDTO)
	 */
	@Override
	public boolean checkExist(HFriendDTO entity) {
		
		if(null == entity){
			throw new RuntimeException("实体参数不能为空");
		}
		
		Example example = new Example(HFriendDTO.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("sUserId", entity.getsUserId());
		criteria.andEqualTo("sFriendId", entity.getsFriendId());
		
		if(StringUtils.isNotEmpty(entity.getsId())){
			Criteria criteria2 = example.createCriteria();
			criteria2.andNotEqualTo("sId", entity.getsId());
			example.and(criteria2);
		}
		
		int count = 0;
		try {
			count = super.findCount(example);
		} catch (Exception e) {
			doExceptionMsg(e, "校验数据重复性失败");
		}
		if(count > 0){
			return true;
		}
		return false;
	}
	
	
	@Override
	public List<UserDTO> getFriendsByUser(String sUserId, Integer... states) {
		
		if(StringUtils.isEmpty(sUserId)){
			throw new RuntimeException("参数不能为空");
		}
		
		List<Object> userIds = getFriendIdsByUser(sUserId, states);
		return userDao.getUsersByIds(userIds);
	}
	

	@Override
	public List<Object> getFriendIdsByUser(String sUserId, Integer... states) {
		if(StringUtils.isEmpty(sUserId)){
			throw new RuntimeException("参数不能为空");
		}
		
		StringBuilder sql = new StringBuilder(" SELECT sFriendId FROM "+getTableName()+" WHERE sUserId='"+sUserId+"' AND iState=1 ");
		if(null != states && states.length > 0){
			if(states.length == 1 && null != states[0]){
				sql.append(" AND iOutterState=" + states[0]);
			}
			if(states.length >= 2 && null != states[1]){
				sql.append(" AND iInnerState=" + states[1]);
			}
		}
		
		return super.selectOneColumnBySql(sql.toString());
	}
	

	@Override
	public Boolean checkFriendOutterState(String sUserId, String friendId) {
		
		if(StringUtils.isEmpty(sUserId,friendId)){
			throw new RuntimeException("参数不能为空");
		}
		
		HFriendDTO friendDTO = new HFriendDTO();
		friendDTO.setsUserId(friendId);
		friendDTO.setsFriendId(sUserId);
		
		HFriendDTO find = super.find(friendDTO);
		if(null == find){
			return false;
		}
		if(HFriend_Inner_State.Normal == find.getiInnerState()){
			return true;
		}
		
		return false;
	}
	

	@Override
	public List<Object> getFriendOutterState(String sUserId, List<Object> friendIds) {
		
		if(StringUtils.isEmpty(sUserId)){
			throw new RuntimeException("参数不能为空");
		}
		if(null == friendIds || friendIds.size() <= 0){
			return new ArrayList<>();
		}
		
		StringBuilder sql = new StringBuilder(" SELECT sUserId FROM "+getTableName()+" WHERE iState=1 AND sFriendId='"+sUserId+"' AND iInnerState=1 AND sUserId IN ( ");
		for(int i=0; i<friendIds.size(); i++){
			if(i == 0){
				sql.append( friendIds.get(i) + ",");
			}else{
				sql.append( friendIds.get(i));
			}
		}
		sql.append(" ) ");
		
		return super.selectOneColumnBySql(sql.toString());
	}

}
