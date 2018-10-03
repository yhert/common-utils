package com.yhert.project.common.util.img.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.yhert.project.common.util.img.ImageUtils;

/**
 * 图片测试
 * 
 * @author Ricardo Li 2017年3月19日 下午4:54:37
 *
 */
public class ImageUtilsTest {
	/**
	 * 生成二维码
	 * 
	 * @throws IOException 异常
	 */
	@Test
	public void buildQRCodeImage() {
		String outFileName = "temp/test/ImageUtilsTest/buildQRCodeImage/qrcode.png";
		File file = new File(outFileName);
		file.mkdirs();
		if (file.exists()) {
			file.delete();
		}
		ImageUtils.buildQRCodeImage("https://www.yhert.com", 300, 300, outFileName);
		file = new File(outFileName);
		assertTrue("生成条形码功能异常", file.exists());
		file.delete();
		System.out.println("生成二维码功能正常");
	}

	/**
	 * 测试条形码生成
	 * 
	 * @throws IOException 异常
	 */
	@Test
	public void buildCodeImage() {
		String outFileName = "temp/test/ImageUtilsTest/buildQRCodeImage/code.png";
		File file = new File(outFileName);
		file.mkdirs();
		if (file.exists()) {
			file.delete();
		}
		ImageUtils.buildCodeImage("634523452345", 500, 300, outFileName);
		file = new File(outFileName);
		assertTrue("生成条形码功能异常", file.exists());
		file.delete();
		System.out.println("生成条形码法功能正常");
	}
}
