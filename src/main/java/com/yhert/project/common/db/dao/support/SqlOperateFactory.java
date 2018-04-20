package com.yhert.project.common.db.dao.support;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yhert.project.common.db.operate.DbExecution;
import com.yhert.project.common.excp.dao.DBOperateException;
import com.yhert.project.common.util.StringUtils;
import com.yhert.project.common.util.db.DBUtils;

/**
 * sqlOperate工程类
 * 
 * @author Ricardo Li 2017年8月1日 下午6:09:59
 *
 */
public class SqlOperateFactory {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 数据库执行对象
	 */
	private DbExecution dbExecution;

	public DbExecution getDbExecution() {
		return dbExecution;
	}

	public void setDbExecution(DbExecution dbExecution) {
		this.dbExecution = dbExecution;
	}

	/**
	 * 处理对象
	 */
	private static BaseSqlOperate baseSqlOperate = null;

	/**
	 * 通过数据库类型获得SQL操作器
	 * 
	 * @return SQL操作器
	 */
	public SqlOperate getSqlOperate() {
		if (baseSqlOperate == null) {
			String dbType = DBUtils.getDbType(dbExecution);
			if (StringUtils.isEmpty(dbType)) {
				log.warn("未设置数据类型，默认作为mysql数据库连接操作");
				dbType = "mySql";
			}
			BaseSqlOperate sqlOperate = null;
			switch (dbType.toLowerCase()) {
			case "mysql":
				sqlOperate = new MySqlSqlOperate(dbExecution);
				break;
			case "postgresql":
				sqlOperate = new PostgreSqlOperate(dbExecution);
				break;
			}
			if (ObjectUtils.allNotNull(sqlOperate)) {
				baseSqlOperate = sqlOperate;
				return sqlOperate;
			}
			throw new DBOperateException("数据库类型不被支持：" + dbType);
		} else {
			return baseSqlOperate;
		}
	}
}
