package com.hframe.basic.common;

/**
 * 系统常量池
 * @author Horgn黄小锤
 * @date 2019年1月19日 上午9:23:55
 * @version V1.0
 */
public class SysConstants {
	
	/**通用数据记录状态*/
	public static class RecordStatus {
		/**正常状态*/
		public static final Integer Normal = 1;
		/**删除状态*/
		public static final Integer Delete = 0;
	}
	
	/**系统菜单类型*/
	public static class MenuTypes {
		/**主菜单 */
		public static final Integer Main_Menu = 0;
		/**菜单分组*/
		public static final Integer Group_Menu = 1;
		/**菜单（页面）*/
		public static final Integer Page_Menu = 2;
		/**按钮/链接/表单/元素*/
		public static final Integer Element_Menu = 3;
	}
	
	/**数据字典最顶级父类等级*/
	public static final Integer Dict_Super_Lev = 0;
	/**数据库字典最大级数*/
	public static final Integer Dict_Max_Lev = 3;
	/**系统数据字典类型*/
	public static class DictTypes {
		/**系统字典*/
		public static final Integer System_Dict = 1;
		/**用户字典*/
		public static final Integer User_Dict = 2;
	}
	
	/**部门最高等级：0*/
	public static final Integer Dept_Super_Lev = 0;
	/**部门最低等级：5*/
	public static final Integer Dept_Min_Lev = 5;
	/**部门等级*/
	public static class DepartmentLevs {
		public static final Integer Dept_Super = 0;
		public static final Integer Dept_Son_1 = 1;
		public static final Integer Dept_Son_2 = 2;
		public static final Integer Dept_Son_3 = 3;
		public static final Integer Dept_Son_4 = 4;
		public static final Integer Dept_Son_5 = 5;
	}
	

}
