package com.yhert.project.common.util.img.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片尺寸工具测试
 * 
 * @author RicardoLi 2018年8月20日 下午7:10:43
 *
 */
public class ImageSizeUtilsTest {
	private static String inFile = "temp/test/ImageSizeUtilsTest/input/img.jpg";

	/**
	 * 处理图片尺寸
	 * 
	 * @throws IOException 异常
	 */
	@Test
	public void testImgSize() throws IOException {
		String outFileName = "temp/test/ImageSizeUtilsTest/testImgSize/qrcode.png";
		File file = new File(outFileName);
		file.mkdirs();
		if (file.exists()) {
			file.delete();
		}
		Thumbnails.of(inFile).size(300, 300).rotate(90).toFile(outFileName);
		file = new File(outFileName);
		assertTrue("处理图片尺寸功能异常", file.exists());
		file.delete();
		System.out.println("处理图片尺寸功能正常");
	}
}
