package com.yhert.project.common.util.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认树结构
 * 
 * @author Ricardo Li 2017年6月3日 下午11:20:21
 *
 * @param <T>
 *            数据
 */
public class DefaultTree<T> implements Tree<T> {
	/**
	 * 子节点
	 */
	private List<Tree<T>> childrens = new ArrayList<>();
	/**
	 * 当前节点
	 */
	private T node;
	/**
	 * 父节点
	 */
	private Tree<T> parent;

	public DefaultTree() {
		super();
	}

	public DefaultTree(T node) {
		super();
		this.node = node;
	}

	@Override
	public List<Tree<T>> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Tree<T>> childrens) {
		this.childrens = childrens;
	}

	@Override
	public T getNode() {
		return node;
	}

	public void setNode(T node) {
		this.node = node;
	}

	@Override
	public Tree<T> getParent() {
		return parent;
	}

	public void setParent(Tree<T> parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childrens == null) ? 0 : childrens.hashCode());
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		DefaultTree other = (DefaultTree) obj;
		if (childrens == null) {
			if (other.childrens != null)
				return false;
		} else if (!childrens.equals(other.childrens))
			return false;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("Tree\n");
		sb.append(print("-"));
		return sb.toString();
	}

	private String print(String lastStr) {
		StringBuilder sb = new StringBuilder();
		String nextNodeAdd = "";
		if (node != null || lastStr.length() > 2) {
			sb.append(lastStr + ">" + node + "\n");
			nextNodeAdd = "---";
		}
		for (Tree<T> tree : childrens) {
			@SuppressWarnings("unchecked")
			DefaultTree<T> dt = DefaultTree.class.cast(tree);
			sb.append(dt.print(lastStr + nextNodeAdd));
		}
		return sb.toString();
	}

}
