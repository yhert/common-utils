package com.yhert.project.common.util.file;

import java.io.IOException;
import java.io.OutputStream;

/**
 * IO流操作工具
 * 
 * @author Ricardo Li
 *
 */
public class IOUtils extends org.apache.commons.io.IOUtils {
	/**
	 * 汇总输出流，将写出到每一个输出流
	 * 
	 * @param outs
	 *            输出流
	 * @return 汇总输出流
	 */
	public static OutputStream outputStream2AllOutput(final OutputStream... outs) {
		OutputStream outputStream = new OutputStream() {

			@Override
			public void write(int b) throws IOException {
				for (OutputStream out : outs) {
					out.write(b);
				}
			}

			@Override
			public void write(byte[] b) throws IOException {
				for (OutputStream out : outs) {
					out.write(b);
				}
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				for (OutputStream out : outs) {
					out.write(b, off, len);
				}
			}

			@Override
			public void flush() throws IOException {
				for (OutputStream out : outs) {
					out.flush();
				}
			}

			@Override
			public void close() throws IOException {
				IOException e = null;
				for (OutputStream out : outs) {
					try {
						out.close();
					} catch (IOException ex) {
						e = ex;
						ex.printStackTrace();
					}
				}
				if (e != null) {
					throw e;
				}
			}
		};
		return outputStream;
	}
}
