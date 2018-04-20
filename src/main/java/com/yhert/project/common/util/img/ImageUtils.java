package com.yhert.project.common.util.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.yhert.project.common.excp.ImageException;

/**
 * 图片处理工具
 * 
 * @author Ricardo Li 2017年3月19日 下午3:32:36
 *
 */
public class ImageUtils {

	/**
	 * 构建二维码图片
	 * 
	 * @param content
	 *            内容
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param outFileName
	 *            输出文件名
	 */
	public static void buildQRCodeImage(String content, int width, int height, String outFileName) {
		buildQRCodeImage(content, width, height, outFileName, null, null);
	}

	/**
	 * 构建二维码图片
	 * 
	 * @param content
	 *            内容
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param outFileName
	 *            输出文件名
	 * @param onColor
	 *            颜色
	 * @param offColor
	 *            背景色
	 */
	public static void buildQRCodeImage(String content, int width, int height, String outFileName, Color onColor,
			Color offColor) {
		try {
			try (OutputStream outputStream = FileUtils.openOutputStream(new File(outFileName))) {
				buildQRCodeImage(content, width, height, outputStream, onColor, offColor);
			}
		} catch (ImageException e) {
			throw e;
		} catch (Exception e) {
			throw new ImageException("生成二维码失败", e);
		}
	}

	/**
	 * 构建二维码图片
	 * 
	 * @param content
	 *            内容
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param outputStream
	 *            输出流
	 */
	public static void buildQRCodeImage(String content, int width, int height, OutputStream outputStream) {
		buildQRCodeImage(content, width, height, outputStream, null, null);
	}

	/**
	 * 构建二维码图片
	 * 
	 * @param content
	 *            内容
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param outputStream
	 *            输出流
	 * @param onColor
	 *            颜色
	 * @param offColor
	 *            背景色
	 */
	public static void buildQRCodeImage(String content, int width, int height, OutputStream outputStream, Color onColor,
			Color offColor) {
		buildQRCodeImage(content, "PNG", width, height, outputStream, onColor, offColor);
	}

	/**
	 * 构建二维码图片
	 * 
	 * @param content
	 *            内容
	 * @param outFormat
	 *            输出文件类型（png）
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param outputStream
	 *            输出流
	 * @param onColor
	 *            颜色
	 * @param offColor
	 *            背景色
	 */
	public static void buildQRCodeImage(String content, String outFormat, int width, int height,
			OutputStream outputStream, Color onColor, Color offColor) {
		try {
			buildImage(content, BarcodeFormat.QR_CODE, outFormat, width, height, outputStream, onColor, offColor);
		} catch (Exception e) {
			throw new ImageException("生成二维码失败", e);
		}
	}

	/**
	 * 构建条形码图片
	 * 
	 * @param content
	 *            内容
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param outFileName
	 *            输出文件名
	 */
	public static void buildCodeImage(String content, int width, int height, String outFileName) {
		buildCodeImage(content, width, height, outFileName, null, null);
	}

	/**
	 * 构建条形码图片
	 * 
	 * @param content
	 *            内容
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param outFileName
	 *            输出文件名
	 * @param onColor
	 *            颜色
	 * @param offColor
	 *            背景色
	 */
	public static void buildCodeImage(String content, int width, int height, String outFileName, Color onColor,
			Color offColor) {
		try {
			try (OutputStream outputStream = FileUtils.openOutputStream(new File(outFileName))) {
				buildCodeImage(content, width, height, outputStream, onColor, offColor);
			}
		} catch (ImageException e) {
			throw e;
		} catch (Exception e) {
			throw new ImageException("生成二维码失败", e);
		}
	}

	/**
	 * 构建条形码图片
	 * 
	 * @param content
	 *            内容
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param outputStream
	 *            输出流
	 */
	public static void buildCodeImage(String content, int width, int height, OutputStream outputStream) {
		buildCodeImage(content, width, height, outputStream, null, null);
	}

	/**
	 * 构建条形码图片
	 * 
	 * @param content
	 *            内容
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param outputStream
	 *            输出流
	 * @param onColor
	 *            颜色
	 * @param offColor
	 *            背景色
	 */
	public static void buildCodeImage(String content, int width, int height, OutputStream outputStream, Color onColor,
			Color offColor) {
		buildCodeImage(content, "PNG", width, height, outputStream, onColor, offColor);
	}

	/**
	 * 构建条形码图片
	 * 
	 * @param content
	 *            内容
	 * @param outFormat
	 *            输出文件类型（png）
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param outputStream
	 *            输出流
	 * @param onColor
	 *            颜色
	 * @param offColor
	 *            背景色
	 */
	public static void buildCodeImage(String content, String outFormat, int width, int height,
			OutputStream outputStream, Color onColor, Color offColor) {
		try {
			buildImage(content, BarcodeFormat.CODE_128, outFormat, width, height, outputStream, onColor, offColor);
		} catch (Exception e) {
			throw new ImageException("生成条形码失败", e);
		}
	}

	/**
	 * 构建图片
	 * 
	 * @param content
	 *            内容
	 * @param format
	 *            格式
	 * @param outFormat
	 *            输出文件类型（png)
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param outputStream
	 *            输出流
	 * @param onColor
	 *            颜色
	 * @param offColor
	 *            背景色
	 * @throws Exception
	 *             内部异常
	 */
	private static void buildImage(String content, BarcodeFormat format, String outFormat, int width, int height,
			OutputStream outputStream, Color onColor, Color offColor) throws Exception {
		if (StringUtils.isEmpty(content)) {
			throw new IllegalArgumentException("内容content为空");
		}
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// BarcodeFormat.CODE_128; // 表示高密度数据， 字符串可变长，符号内含校验码
		// BarcodeFormat.CODE_39;
		// BarcodeFormat.CODE_93;
		// BarcodeFormat.CODABAR; // 可表示数字0 - 9，字符$、+、 -、还有只能用作起始/终止符的a,b,c
		// d四个字符，可变长度，没有校验位
		// BarcodeFormat.DATA_MATRIX;
		// BarcodeFormat.EAN_8;
		// BarcodeFormat.EAN_13;
		// BarcodeFormat.ITF;
		// BarcodeFormat.PDF417; // 二维码
		// BarcodeFormat.QR_CODE; // 二维码
		// BarcodeFormat.RSS_EXPANDED;
		// BarcodeFormat.RSS14;
		// BarcodeFormat.UPC_E; // 统一产品代码E:7位数字,最后一位为校验位
		// BarcodeFormat.UPC_A; // 统一产品代码A:12位数字,最后一位为校验位
		// BarcodeFormat.UPC_EAN_EXTENSION;
		int imgWidth = width;
		int imgHeight = height;
		BitMatrix matrix = new MultiFormatWriter().encode(content, format, imgWidth, imgHeight, hints);
		if (onColor == null) {
			onColor = new Color(MatrixToImageConfig.BLACK);
		}
		if (offColor == null) {
			offColor = new Color(MatrixToImageConfig.WHITE);
		}
		MatrixToImageConfig config = new MatrixToImageConfig(onColor.getRGB(), offColor.getRGB());
		BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix, config);
		if (BarcodeFormat.CODE_128.equals(format)) {
			Graphics gc = image.getGraphics();
			gc.setColor(offColor);
			int fontHeight = height / 4;
			gc.fillRect(0, height - fontHeight, width, fontHeight);
			gc.fillRect(0, 0, width, fontHeight);
			gc.setColor(onColor);
			int fontSize = Math.min(width / content.length(), fontHeight);
			gc.setFont(new Font("Default", 0, fontSize));
			int x = (width - content.length() * fontSize / 2) * 2 / 5;
			gc.drawString(content, x, (height - fontHeight) + fontSize);
		}
		ImageIO.write(image, outFormat, outputStream);
	}
}
