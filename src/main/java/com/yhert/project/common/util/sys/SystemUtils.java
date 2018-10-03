package com.yhert.project.common.util.sys;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

import com.sun.management.OperatingSystemMXBean;
import com.yhert.project.common.beans.Model;

/**
 * 系统相关数据获取
 * 
 * @author Ricardo Li 2017年1月2日 上午10:53:45
 *
 */
public class SystemUtils extends org.apache.commons.lang3.SystemUtils {
	/**
	 * 获得进程名称
	 * 
	 * @return 进程名称
	 */
	public static String getProcessName() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		return runtimeMXBean.getName();
	}

	/**
	 * 获得进程号
	 * 
	 * @return 进程号
	 */
	public static int getProcessId() {
		String processName = getProcessName();
		return Integer.valueOf(processName.split("@")[0]).intValue();
	}

	/**
	 * 获得系统当前状态信息（CPU，内存，线程，等）
	 * 
	 * @return 系统状态信息
	 */
	public static SystemState getSystemState() {
		SystemState state = new SystemState();

		Runtime runtime = Runtime.getRuntime();
		// 获取jvn内存使用情况
		state.setFreeMemory(runtime.freeMemory());
		state.setTotalMemory(runtime.totalMemory());
		state.setMaxMemory(runtime.maxMemory());
		state.setUsedMemory(state.getTotalMemory() - state.getFreeMemory());

		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		// 获取系统内存使用情况
		state.setFreePhysicalMemory(osmxb.getFreePhysicalMemorySize());
		state.setTotalPhysicalMemory(osmxb.getTotalPhysicalMemorySize());
		state.setUsedPhysicalMemory(state.getTotalPhysicalMemory() - state.getFreePhysicalMemory());
		// 获取系统交换空间使用情况
		state.setFreeSwapSpaceSize(osmxb.getFreeSwapSpaceSize());
		state.setTotalSwapSpaceSize(osmxb.getTotalSwapSpaceSize());
		state.setUsedSwapSpaceSize(state.getTotalSwapSpaceSize() - state.getFreeSwapSpaceSize());
		// 虚拟内存大小
		state.setVirtualMemorySize(osmxb.getCommittedVirtualMemorySize());

		// 获取系统线程使用情况
		RuntimeMXBean runmxb = ManagementFactory.getRuntimeMXBean();

		// jvn已运行时间
		state.setRunningTime(runmxb.getUptime());

		// 获得线程信息
		ThreadGroup parentsGroup = Thread.currentThread().getThreadGroup();
		while (parentsGroup.getParent() != null)
			parentsGroup = parentsGroup.getParent();
		state.setThreadSize(parentsGroup.activeCount());

		// 磁盘信息
		File[] files = File.listRoots();
		List<Disk> disks = new ArrayList<>();
		for (File file : files) {
			Disk disk = new Disk();
			disk.setName(file.getPath());
			disk.setFree(file.getFreeSpace());
			disk.setTotal(file.getTotalSpace());
			disks.add(disk);
		}
		state.setDisks(disks);
		return state;
	}

	public static class SystemState extends Model {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private long freeMemory; // 空闲内存
		private long usedMemory; // 使用中的内存
		private long totalMemory; // 总内存
		private long maxMemory; // 最大内存

		private long freePhysicalMemory; // 空闲物理内存
		private long totalPhysicalMemory; // 总物理内存
		private long usedPhysicalMemory; // 使用中的物理内存

		private long freeSwapSpaceSize; // 空闲的交换空间大小
		private long totalSwapSpaceSize; // 总交换空间大小
		private long usedSwapSpaceSize; // 使用中的交换空间大小

		private long virtualMemorySize; // 虚拟内存大小

		private int threadSize; // 线程数
		private long runningTime; // jvn已运行时间

		private List<Disk> disks; // 磁盘信息

		public int getThreadSize() {
			return threadSize;
		}

		public void setThreadSize(int threadSize) {
			this.threadSize = threadSize;
		}

		public long getFreeMemory() {
			return freeMemory;
		}

		public void setFreeMemory(long freeMemory) {
			this.freeMemory = freeMemory;
		}

		public long getUsedMemory() {
			return usedMemory;
		}

		public void setUsedMemory(long usedMemory) {
			this.usedMemory = usedMemory;
		}

		public long getFreePhysicalMemory() {
			return freePhysicalMemory;
		}

		public long getFreeSwapSpaceSize() {
			return freeSwapSpaceSize;
		}

		public void setFreeSwapSpaceSize(long freeSwapSpaceSize) {
			this.freeSwapSpaceSize = freeSwapSpaceSize;
		}

		public long getTotalSwapSpaceSize() {
			return totalSwapSpaceSize;
		}

		public void setTotalSwapSpaceSize(long totalSwapSpaceSize) {
			this.totalSwapSpaceSize = totalSwapSpaceSize;
		}

		public long getUsedSwapSpaceSize() {
			return usedSwapSpaceSize;
		}

		public void setUsedSwapSpaceSize(long usedSwapSpaceSize) {
			this.usedSwapSpaceSize = usedSwapSpaceSize;
		}

		public void setFreePhysicalMemory(long freePhysicalMemory) {
			this.freePhysicalMemory = freePhysicalMemory;
		}

		public long getTotalPhysicalMemory() {
			return totalPhysicalMemory;
		}

		public void setTotalPhysicalMemory(long totalPhysicalMemory) {
			this.totalPhysicalMemory = totalPhysicalMemory;
		}

		public long getUsedPhysicalMemory() {
			return usedPhysicalMemory;
		}

		public void setUsedPhysicalMemory(long usedPhysicalMemory) {
			this.usedPhysicalMemory = usedPhysicalMemory;
		}

		public long getTotalMemory() {
			return totalMemory;
		}

		public void setTotalMemory(long totalMemory) {
			this.totalMemory = totalMemory;
		}

		public long getMaxMemory() {
			return maxMemory;
		}

		public void setMaxMemory(long maxMemory) {
			this.maxMemory = maxMemory;
		}

		public long getVirtualMemorySize() {
			return virtualMemorySize;
		}

		public void setVirtualMemorySize(long virtualMemorySize) {
			this.virtualMemorySize = virtualMemorySize;
		}

		public long getRunningTime() {
			return runningTime;
		}

		public void setRunningTime(long runningTime) {
			this.runningTime = runningTime;
		}

		public List<Disk> getDisks() {
			return disks;
		}

		public void setDisks(List<Disk> disks) {
			this.disks = disks;
		}

	}

	/**
	 * 磁盘信息
	 * 
	 * @author Ricardo Li 2017年1月2日 下午12:45:31
	 *
	 */
	public static class Disk extends Model {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String name; // 磁盘信息
		private long free; // 空闲磁盘空间
		private long total; // 总磁盘空间

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public long getFree() {
			return free;
		}

		public void setFree(long free) {
			this.free = free;
		}

		public long getTotal() {
			return total;
		}

		public void setTotal(long total) {
			this.total = total;
		}

	}
}
