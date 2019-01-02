package net.ewant.pager;

import java.util.List;

public interface MybatisPager<T> {
	
	String DEFAULT_QUERY_LIST_ID = "selectList";
	String DEFAULT_QUERY_PAGE_ID = "selectPage";

	/**
	 * 列表查询，所有符合条件的数据
	 * @param parameter
	 * @return
	 */
	List<T> selectList(Object parameter);
	/**
	 * 列表查询，所有符合条件的数据
	 * @param sqlId
	 * @param parameter
	 * @return
	 */
	List<T> selectList(String sqlId, Object parameter);
	/**
	 * 列表查询，所有符合条件的数据
	 * @param <E>
	 * @param parameter
	 * @param type
	 * @return
	 */
	<E> List<E> selectList(Object parameter, Class<E> type);
	/**
	 * 列表查询，所有符合条件的数据
	 * @param <E>
	 * @param sqlId
	 * @param parameter
	 * @param type
	 * @return
	 */
	<E> List<E> selectList(String sqlId, Object parameter, Class<E> type);
	/**
	 * 分页查询，所有符合条件的数据
	 * @param parameter
	 * @return
	 */
	Pager<T> selectPage(PageParams parameter);
	/**
	 * 分页查询，所有符合条件的数据
	 * @param sqlId
	 * @param parameter
	 * @return
	 */
	Pager<T> selectPage(String sqlId, PageParams parameter);
	/**
	 * 分页查询，所有符合条件的数据
	 * @param parameter
	 * @param type
	 * @return
	 */
	<E> Pager<E> selectPage(PageParams parameter, Class<E> type);
	/**
	 * 分页查询，所有符合条件的数据
	 * @param sqlId
	 * @param parameter
	 * @param type
	 * @return
	 */
	<E> Pager<E> selectPage(String sqlId, PageParams parameter, Class<E> type);

	/**
	 * sqlId 前缀，默认使用泛型T的class全名做前缀
	 * @return null时使用默认前缀
	 */
	String sqlIdPrefix();
}
