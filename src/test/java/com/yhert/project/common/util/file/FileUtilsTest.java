package com.yhert.project.common.util.file;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.junit.Test;

import com.yhert.project.common.util.file.FileUtils.InputStreamTempFileDealCallback;

public class FileUtilsTest {
	@Test
	public void testInput() {
		FileUtils.inputStreamTempFileDeal(new ByteArrayInputStream("时间".getBytes()),
				new InputStreamTempFileDealCallback() {
					@Override
					public void apply(File file) {
						System.out.println(file.getPath().replaceAll("\\\\", "/"));
						System.out.println(file);
					}
				});
	}
}
