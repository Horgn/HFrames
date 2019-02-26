package com.hframe.basic.common;

/**
 * 分页查询响应类<br>
 * 默认每页显示30条记录，最多1000条
 * @author Horgn黄小锤
 * @version v1.0
 * @date 2018-01-18 10:29:39
 */
public final class PageResult {
	
	
	/**默认每页显示记录数量*/
	private final Integer DEF_PAGESIZE = 30;
	/**最大查询记录数*/
	private final Integer MAX_PAGESIZE = 1000;
	

	/** 当前页(需要显示的页码) */
	private int pageCurrent = 1;
	/** 总记录数(表中有多少条记录) */
	private int rowCount;
	/** 总页数 */
	private int pageCount;
	/** 每页要显示的记录数 */
	private int pageSize = DEF_PAGESIZE;
	/** 取下页数据的起始记录 */
	private int startIndex;
	/** 获取到的记录数据 */
	private Object data;
	
	
	/** 排序字段 */
	private String orderField;
	/** 排序方向 */
	private String orderDirection;
	/** 查询字段 */
	private String searchField;
	/** 查询值 */
	private String searchValue;
	
	
	/**
	 * 分页查询类，默认每页显示30条记录，最多1000条
	 * 构造器 PageResult
	 */
	public PageResult(){ }
	
	
	/**
	 * 分页查询类，默认每页显示30条记录，最多1000条
	 * @author Horgn黄小锤
	 * @date 2018-10-23 14:53:18
	 * @param pageCurrent 当前页
	 * @param pageSize 每页显示数量
	 */
	public PageResult(Integer pageCurrent,Integer pageSize) {
		
		if(null == pageCurrent || pageCurrent <= 0){
			this.pageCurrent = 1;
		}else{
			this.pageCurrent = pageCurrent;
		}
		
		if(null == pageSize || pageSize <= 0){
			this.pageSize = DEF_PAGESIZE;
			
		}else if(pageSize > MAX_PAGESIZE){
			this.pageSize = MAX_PAGESIZE;
			
		}else{
			this.pageSize = pageSize;
		}
	}
	

	/** 根据当前页计算当前页记录的开始位置（下标） */
	public int getStartIndex() {
		return (pageCurrent - 1) * pageSize;
	}
	

	/** 获取总页数 */
	public int getPageCount() {
		
		// 在此方法中通过rowCount,pageSize计算总页数
		int pages = rowCount / pageSize;
		
		if (rowCount % pageSize != 0) {
			pages += 1;
		}
		return pages;
	}
	
	
	/**获取当前页*/
	public int getPageCurrent() {
		return pageCurrent;
	}

	
	/**设置当前页*/
	public void setPageCurrent(Integer pageCurrent) {
		
		if(null == pageCurrent || pageCurrent <= 0){
			this.pageCurrent = 1;
		}else{
			this.pageCurrent = pageCurrent;
		}
	}
	

	/**获取总记录数*/
	public int getRowCount() {
		return rowCount;
	}

	
	/**设置总记录数*/
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	
	/**获取每页显示数量*/
	public int getPageSize() {
		return pageSize;
	}

	
	/**设置每页显示数量*/
	public void setPageSize(Integer pageSize) {
		
		if(null == pageSize || pageSize <= 0){
			this.pageSize = DEF_PAGESIZE;
		}else if(pageSize > MAX_PAGESIZE){
			this.pageSize = MAX_PAGESIZE;
		}else{
			this.pageSize = pageSize;
		}
	}
	

	/**设置起始记录的下标（记录序号）*/
	public void setStartIndex(Integer startIndex) {
		
		if(null == startIndex || startIndex < 0){
			this.startIndex = 0;
		}else{
			this.startIndex = startIndex;
		}
	}
	

	/**获取数据*/
	public Object getData() {
		return data;
	}

	
	/**设置数据*/
	public void setData(Object data) {
		this.data = data;
	}

	
	/**获取总页数*/
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	
	@Override
	public String toString() {
		return "PageResult [pageCurrent=" + pageCurrent + ", rowCount=" + rowCount + ", pageCount=" + pageCount
				+ ", pageSize=" + pageSize + ", startIndex=" + startIndex + ", data=" + data + "]";
	}
	
	
	/**获取排序字段 */
	public String getOrderField() {
		return orderField;
	}
	

	/** 设置排序字段*/
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	

	/** 获取排序方向*/
	public String getOrderDirection() {
		return orderDirection;
	}
	

	/** 设置排序方向*/
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
	

	/**获取查询字段*/
	public String getSearchField() {
		return searchField;
	}
	

	/**设置查询字段*/
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	

	/** 获取查询值*/
	public String getSearchValue() {
		return searchValue;
	}
	

	/** 设置查询值*/
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

}
