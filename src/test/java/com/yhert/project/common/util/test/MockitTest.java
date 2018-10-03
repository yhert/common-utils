package com.yhert.project.common.util.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;

/**
 * 学习mockito测试
 * 
 * @author lirenjie
 *
 */
public class MockitTest {

	/**
	 * then测试
	 */
	@Test
	@SuppressWarnings("rawtypes")
	public void then() {
		ArrayList list = mock(ArrayList.class);
		when(list.get(0)).thenReturn("test");
		assertTrue("mockito测试有误", "test".equals(list.get(0)));
		verify(list).get(0);
	}
}
