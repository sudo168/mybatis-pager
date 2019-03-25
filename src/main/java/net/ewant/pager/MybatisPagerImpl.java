package net.ewant.pager;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Repository
public class MybatisPagerImpl<T> extends SqlSessionDaoSupport implements MybatisPager<T> {
	
	private boolean initSqlSession;
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		if(!this.initSqlSession){
			super.setSqlSessionFactory(sqlSessionFactory);
			this.initSqlSession = true;
		}
	}
	
	private Class<?> getActualType(Class<?> clazz) {
		Type genericSuperclass = clazz.getGenericSuperclass();
		if (genericSuperclass instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
		}else if(!Object.class.equals(clazz)){
			return getActualType((Class<?>)genericSuperclass);
		}
		return clazz;
	}
	
	/**
	 * 获取泛型类对应的sql命名空间
	 * @param sqlId
	 * @return
	 */
	private String getNameSpace(Class type, String sqlId) {
		if(sqlId.contains(".")){
			return sqlId;
		}
        String prefix = sqlIdPrefix();
        if(prefix == null){
            prefix = type.getName() + "Mapper";
		}
		return prefix + "." + sqlId;
	}
	
	public List<T> selectList(Object parameter) {
		return selectList(DEFAULT_QUERY_LIST_ID ,parameter);
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<T> selectList(String sqlId,Object parameter) {
		return selectList(getActualType(getClass()),sqlId, parameter, null);
	}
	
	public <E> List<E> selectList(Object parameter,Class<E> type) {
		return selectList(DEFAULT_QUERY_LIST_ID, parameter, type);
	}

	@SuppressWarnings("unchecked")
	public <E> List<E> selectList(String sqlId, Object parameter,Class<E> type) {
		return selectList(type, sqlId, parameter, null);
	}
	
	@SuppressWarnings({ "rawtypes" })
	private List selectList(Class type, String sqlId, Object parameter, RowBounds rowBounds) {
		if(rowBounds == null){
			return this.getSqlSession().selectList(getNameSpace(type, sqlId), parameter);
		}
        //PageHelper.startPage(rowBounds.getOffset(), rowBounds.getLimit(), false);
		//return this.getSqlSession().selectList(getNameSpace(type, sqlId), parameter);
        return this.getSqlSession().selectList(getNameSpace(type, sqlId), parameter, rowBounds);
	}
	
	public Pager<T> selectPage(PageParams parameter) {
		return selectPage(DEFAULT_QUERY_PAGE_ID ,parameter);
	}
	
	@SuppressWarnings({ "unchecked" })
	public Pager<T> selectPage(String sqlId, PageParams parameter) {
		PageParams pageParams = getPageParams(parameter);
		return newPager(selectList(getActualType(getClass()), sqlId, parameter, new RowBounds(pageParams.getPageNum(), pageParams.getPageSize())), pageParams);
	}
	
	public <E> Pager<E> selectPage(PageParams parameter, Class<E> type) {
		return selectPage(DEFAULT_QUERY_LIST_ID, parameter, type);
	}

	@SuppressWarnings("unchecked")
	public <E> Pager<E> selectPage(String sqlId, PageParams parameter, Class<E> type) {
		PageParams pageParams = getPageParams(parameter);
		return newPager(selectList(type, sqlId, parameter, new RowBounds(pageParams.getPageNum(), pageParams.getPageSize())), pageParams);
	}
	
	@SuppressWarnings("rawtypes")
	private PageParams getPageParams(Object parameter) {
		PageParams pageParams;
		if(parameter == null){
			pageParams = PageParams.DEFAULT;
		}else if(parameter instanceof PageParams){
			pageParams = (PageParams) parameter;
		}else if(parameter instanceof Pageable){
			pageParams = ((Pageable)parameter).getPageParams();
			if(pageParams == null){
				pageParams = PageParams.DEFAULT;
			}
		}else if(parameter instanceof Map){
			Object objectNum = ((Map)parameter).get(PageParams.DEFAULT_PAGE_NUM_NAME);
			Object objectSize = ((Map)parameter).get(PageParams.DEFAULT_PAGE_SIZE_NAME);
			Object objectNavi = ((Map)parameter).get(PageParams.DEFAULT_PAGE_NAVIGATE_NUM_NAME);
			if(objectNum == null && objectSize == null && objectNavi == null){
				pageParams = PageParams.DEFAULT;
			}else{
				pageParams = new PageParams();
				if(objectNum != null){
					pageParams.setPageNum(Integer.parseInt(objectNum.toString()));
				}
				if(objectSize != null){
					pageParams.setPageSize(Integer.parseInt(objectSize.toString()));
				}
				if(objectNavi != null){
					pageParams.setNavigateNum(Integer.parseInt(objectNavi.toString()));
				}
			}
		}else{
			pageParams = PageParams.DEFAULT;
		}
		return pageParams;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Pager newPager(List list, PageParams pageParams){
		int navigatePages = pageParams.getNavigateNum();
		if(navigatePages == 0){
			return new Pager(list);
		}
		return new Pager(list, navigatePages);
	}

	public String sqlIdPrefix() {
		return null;
	}
}
