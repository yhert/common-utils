dao
@[spackagepath]/dao/impl/@[javaname]DaoImpl.java
false
package ${package}.dao.impl;

import org.springframework.stereotype.Repository;

import ${package}.beans.${javaname};
import ${package}.dao.${javaname}Dao;
import cn.yuncaitong.mall.auxiliary.dao.AbstractDao;

/**
 * ${tableComment}持久化层
 * 
 * @author ${author} ${newdate}
 *
 */
@Repository("${cname}Dao")
public class ${javaname}DaoImpl extends AbstractDao<${javaname}> implements ${javaname}Dao {
}
