package net.ewant.pager;

import java.util.HashMap;
import java.util.Map;

public class PageParams{
	
	static final PageParams DEFAULT = new PageParams();
	
	public static final String DEFAULT_PAGE_NUM_NAME = "pageNum";
	public static final String DEFAULT_PAGE_SIZE_NAME = "pageSize";
	public static final String DEFAULT_PAGE_NAVIGATE_NUM_NAME = "navigateNum";
	public static final int DEFAULT_PAGE_NUM = 1;
	public static final int DEFAULT_PAGE_SIZE = 15;

	int pageNum = DEFAULT_PAGE_NUM;
	int pageSize = DEFAULT_PAGE_SIZE;
	int navigateNum;// 导航按钮个数

	Map<String, Object> conditions = new HashMap<String, Object>();

	public PageParams() {
	}
	
	public PageParams(int pageNum, int pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public PageParams(int pageNum, int pageSize, int navigateNum) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.navigateNum = navigateNum;
	}

	public int getNavigateNum() {
		return navigateNum;
	}

	public void setNavigateNum(int navigateNum) {
		this.navigateNum = navigateNum;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, Object> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	public PageParams addCondition(String key, Object value){
		conditions.put(key, value);
		return this;
	}
}
