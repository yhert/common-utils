package com.yhert.project.common.util.file;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 处理md5,sha1等算法的编码
 * 
 * @author Ricardo Li
 *
 */
public class DigesterUtils extends DigestUtils {
	/**
	 * MD5
	 */
	public static final String MD5 = "MD5";
	/**
	 * SHA-256
	 */
	public static final String SHA_256 = "SHA-256";
	/**
	 * SHA-384
	 */
	public static final String SHA_384 = "SHA-384";
	/**
	 * SHA-512
	 */
	public static final String SHA_512 = "SHA-512";
	/**
	 * SHA
	 */
	public static final String SHA = "SHA";

	/**
	 * 获得编码计算器
	 * 
	 * @param algorithm
	 *            编码
	 * @return 计算器
	 */
	public static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 获得MessageDigest的输出流计算器，完成后通过调用digest.digest()获得计算结果
	 * 
	 * @param digest
	 *            计算器
	 * @return 输出流
	 */
	public static OutputStream getDigestOutputStream(final MessageDigest digest) {
		return new OutputStream() {

			@Override
			public void write(byte[] b) throws IOException {
				digest.update(b);
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				digest.update(b, off, len);
			}

			@Override
			public void write(int b) throws IOException {
				digest.update((byte) b);
			}
		};
	}
}
