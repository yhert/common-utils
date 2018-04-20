package com.yhert.project.common.util.img.test;

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
	@Test
	public void buildQRCodeImage() throws IOException {
		ImageUtils.buildQRCodeImage("https://www.yhert.com", 300, 300,
				"temp/test/ImageUtilsTest/buildQRCodeImage/qrcode.png");
		System.out.println("生成二维码功能正常");
	}

	@Test
	public void buildCodeImage() throws IOException {
		ImageUtils.buildCodeImage("634523452345", 500, 300, "temp/test/ImageUtilsTest/buildQRCodeImage/code.png");
		System.out.println("生成条形码法功能正常");
	}
}
