package com.yhert.project.common.util.tree;

import java.util.ArrayList;
import java.util.List;

import com.yhert.project.common.beans.Model;

/**
 * 默认树结构
 * 
 * @author Ricardo Li 2017年6月3日 下午11:20:21
 *
 * @param <T>
 *            数据
 */
public class DefaultTree<T> extends Model implements Tree<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
