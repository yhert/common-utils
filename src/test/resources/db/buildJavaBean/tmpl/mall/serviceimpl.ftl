service
@[spackagepath]/service/impl/@[javaname]ServiceImpl.java
false
package ${package}.service.impl;

import org.springframework.stereotype.Service;

import ${package}.service.${javaname}Service;

/**
 * ${tableComment}服务层
 * 
 * @author ${author} ${newdate}
 *
 */
@Service("${cname}Service")
public class ${javaname}ServiceImpl implements ${javaname}Service {
}
