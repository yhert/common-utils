package com.yhert.project.common.util.tree;

import java.util.List;

/**
 * 树定义接口
 * 
 * @author Ricardo Li 2017年6月3日 下午11:16:22
 *
 * @param <T>
 *            类型
 */
public interface Tree<T> {
	/**
	 * 获得树子节点
	 * 
	 * @return 树子节点
	 */
	List<Tree<T>> getChildrens();

	/**
	 * 获得当前节点
	 * 
	 * @return 当前节点
	 */
	T getNode();

	/**
	 * 获得父节点
	 * 
	 * @return 父节点
	 */
	Tree<T> getParent();
}
