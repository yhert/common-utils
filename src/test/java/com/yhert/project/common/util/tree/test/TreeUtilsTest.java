package com.yhert.project.common.util.tree.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.yhert.project.common.util.tree.Tree;
import com.yhert.project.common.util.tree.TreeUtils;

/**
 * 测试树形工具
 * 
 * @author Ricardo Li 2017年6月3日 下午11:10:12
 *
 */
public class TreeUtilsTest {

	@Test
	public void tree() throws Exception {
		System.out.println("测试构建树工具==>start");
		List<User> users = new ArrayList<>();
		users.add(new User("a", null, "a"));
		users.add(new User("b", "a", "b"));
		users.add(new User("c", "a", "c"));
		users.add(new User("d", "a", "d"));
		users.add(new User("e", null, "e"));
		users.add(new User("f", "e", "f"));
		users.add(new User("g", "e", "g"));
		users.add(new User("h", "b", "h"));
		users.add(new User("i", "b", "i"));
		// DefaultTree<User> tree = new DefaultTree<>(users.get(0));
		// tree.setNode(users.get(0));
		// DefaultTree<User> te = new DefaultTree<>(users.get(2));
		// tree.getChildrens().add(new DefaultTree<>(users.get(1)));
		// tree.getChildrens().add(te);
		// tree.getChildrens().add(new DefaultTree<>(users.get(3)));
		// te.getChildrens().add(new DefaultTree<>(users.get(4)));
		// te.getChildrens().add(new DefaultTree<>(users.get(5)));
		// System.out.println(tree);
		Tree<User> root = TreeUtils.buildTree(users, "id", "parentId");
		System.out.println("测试构建树工具==>success:" + root);
	}
}
