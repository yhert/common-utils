package com.yhert.project.common.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 压测工具
 * 
 * @author Ricardo Li 2018年1月4日 上午10:55:11
 *
 */
public abstract class AbstractStressTesting {
	private int threadCount = 100;
	private CountDownLatch startLatch = new CountDownLatch(1);
	private CountDownLatch endLath;

	public AbstractStressTesting() {
		super();
	}

	public AbstractStressTesting(int threadCount) {
		super();
		this.threadCount = threadCount;
		endLath = new CountDownLatch(threadCount);
	}

	public void start(final Runnable runnable) {
		AtomicLong sumLong = new AtomicLong(0);
		AtomicInteger successTime = new AtomicInteger(0);
		AtomicInteger faildTime = new AtomicInteger(0);
		for (int i = 0; i < threadCount; i++) {
			new Thread(() -> {
				try {
					startLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					long start = System.currentTimeMillis();
					runnable.run();
					long time = System.currentTimeMillis() - start;
					successTime.addAndGet(1);
					sumLong.addAndGet(time);
					System.out.println("=====================" + Thread.currentThread().getName() + "===========" + time);
				} catch (Exception e) {
					e.printStackTrace();
					faildTime.addAndGet(1);
				} finally {
					endLath.countDown();
				}
			}).start();
		}
		System.out.println("==========run");
		startLatch.countDown();
		try {
			endLath.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long avgTime = sumLong.get() / successTime.get();
		System.out.println("=====================end=====================");
		System.out.println("====================report start====================");
		System.out.println("total   time:\t" + threadCount);
		System.out.println("success time:\t" + successTime.get());
		System.out.println("faild   time:\t" + faildTime.get());
		System.out.println("avg     time:\t" + avgTime);
		System.out.println("=====================report end=====================");

	}
}
