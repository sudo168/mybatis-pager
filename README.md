# mybatis-pager

本案例使用的是RowBounds方式分页 ,offsetAsPageNum需为true才能得到正确的分页结果。
需要count查询则需要配置: 
````
pagehelper:
  offsetAsPageNum: true
  rowBoundsWithCount: true
  autoRuntimeDialect: true
