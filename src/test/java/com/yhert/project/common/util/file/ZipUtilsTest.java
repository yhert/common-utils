package com.yhert.project.common.util.file;

public class ZipUtilsTest {
//	@Test
	public void zip() {
//		String path = "V:\\OutTmp\\tomcat\\www\\action.log.2017-06-01.log";
//		String path2 = "V:\\OutTmp\\tomcat\\www\\dao.log.2017-06-06.log";
		String path3 = "V:\\OutTmp\\tomcat\\sss\\test";
		String out = "V:\\OutTmp\\tomcat\\sss\\zip.zip";
//		ZipUtils.zip(out, path3);
		ZipUtils.unzip(out, path3);
	}
}
