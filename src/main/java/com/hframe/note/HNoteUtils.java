package com.hframe.note;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hframe.note.category.service.HCategoryService;
import com.hframe.note.note.service.HNoteService;

/**
 * 小锤笔记工具类
 * @author Horgn黄小锤
 * @date 2019年2月9日 下午8:48:51
 * @version V1.0
 */
@Component
public class HNoteUtils {
	
	/**小锤笔记常量池*/
	public static final class Constants {
		
		/**笔记可见状态*/
		public static final class HNote_Show_Type {
			/**所以有可见*/
			public static final Integer All = 0;
			/**好友可见*/
			public static final Integer Friend = 1;
			/**仅自己可见*/
			public static final Integer Myself = 2;
		}
		
		/**我对好友的状态*/
		public static final class HFriend_Outter_State {
			/**正常，可见*/
			public static final Integer Normal = 1;
			/**不看好友的动态*/
			public static final Integer Invisible = 0;
		}
		
		/**好友对我的可见状态*/
		public static final class HFriend_Inner_State {
			/**正常，可见*/
			public static final Integer Normal = 1;
			/**不让好友看我的动态*/
			public static final Integer Invisible = 0;
		}
		
	}
	
	public static HNoteService NoteService;
	public static HCategoryService CategroyService;
	
	@Autowired
	private HNoteService nService;
	@Autowired
	private HCategoryService cService;
	
	@PostConstruct
	public void init(){
		NoteService = nService;
		CategroyService = cService;
	}

}
