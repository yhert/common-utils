package com.yhert.project.common.db.operate;

/**
 * 数据库操作
 * 
 * @author Ricardo Li 2018年2月8日 下午4:18:33
 *
 */
public interface DbExecution {
	/**
	 * 连接执行
	 * 
	 * @param callBack
	 *            回调
	 * @return 结果
	 */
	<T> T execution(ConnectionCallBack<T> callBack);
}
