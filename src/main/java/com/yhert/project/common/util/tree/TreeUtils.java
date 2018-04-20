package com.yhert.project.common.util.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yhert.project.common.util.BeanUtils;

/**
 * 创建树结构的工具
 * 
 * @author Ricardo Li 2017年6月3日 下午11:15:29
 *
 */
public class TreeUtils {
	/**
	 * 构建树形结构（未找到父节点的节点将作为根节点返回）
	 * 
	 * @param datas
	 *            原始数据
	 * @param idName
	 *            ID字段名称
	 * @param parentIdName
	 *            父级关联ID字段名称
	 * @return 根节点
	 */
	@SuppressWarnings("unchecked")
	public static <T> Tree<T> buildTree(List<T> datas, String idName, String parentIdName) {
		// 准备id映射器
		Map<Object, Tree<T>> idMap = new HashMap<>();
		for (T t : datas) {
			idMap.put(BeanUtils.getPropertyValue(t, idName), new DefaultTree<T>(t));
		}
		// 处理节点
		DefaultTree<T> root = new DefaultTree<>();
		List<Tree<T>> roots = root.getChildrens();
		for (T t : datas) {
			Object id = BeanUtils.getPropertyValue(t, idName);
			Object parentId = BeanUtils.getPropertyValue(t, parentIdName);
			Tree<T> nodeTree = idMap.get(id);
			if (parentId != null) {
				Tree<T> parent = idMap.get(parentId);
				if (parent != null) {
					parent.getChildrens().add(nodeTree);
					DefaultTree.class.cast(nodeTree).setParent(parent);
					continue;
				}
			}
			roots.add(nodeTree);
		}
		return root;
	}
}
