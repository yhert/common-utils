package com.yhert.project.common.db.buildJavaBean;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;

import com.yhert.project.common.beans.Param;
import com.yhert.project.common.conf.Configuration;
import com.yhert.project.common.db.operate.DbExecution;
import com.yhert.project.common.db.operate.impl.DataSourceDbExecution;
import com.yhert.project.common.util.BeanUtils;
import com.yhert.project.common.util.CommonFunUtils;
import com.yhert.project.common.util.DateUtils;
import com.yhert.project.common.util.StringUtils;
import com.yhert.project.common.util.TemplateUtils;
import com.yhert.project.common.util.db.Column;
import com.yhert.project.common.util.db.DBUtils;
import com.yhert.project.common.util.db.Table;
import com.yhert.project.common.util.file.FileUtils;
import com.yhert.project.common.util.source.SourceUtils;

/**
 * 通过数据库构建Javabean
 * 
 * <p>
 * 数据库表备注：|@description{[(..)]}定义描述信息
 * </p>
 * <p>
 * 数据库字段：|@example{[(...)]}定义示例
 * </p>
 * 
 * @author RicardoLi 2017年6月16日 下午2:36:55
 *
 */
public class BuildJavaBeanByDb {

	private static BasicDataSource basicDataSource;

	// @Test
	public static void buildJava() throws Exception {
		Configuration conf = new Configuration();
		conf.loadProperties("db/buildJavaBean/dbutils-buildjavabean.properties");
		System.out.println("加载配置完成");
		System.out.println(conf);
		System.out.println("开始执行构建Javabean");
		basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(conf.getString("jdbc.driver"));
		basicDataSource.setUrl(conf.getString("jdbc.url"));
		basicDataSource.setUsername(conf.getString("jdbc.username"));
		basicDataSource.setPassword(conf.getString("jdbc.password"));

		DbExecution dbExecution = new DataSourceDbExecution(basicDataSource);

		String tableName = conf.getString("param.tablename");
		if ("all".equals(StringUtils.trim(tableName))) {
			for (Table key : DBUtils.getDbOperate(dbExecution).getTableName("%")) {
				buildTmpl(dbExecution, key.getTableName(), key.getRemake(), conf);
			}
		} else {
			String tablecomment = conf.getString("param.tablecomment", "");
			String[] tns = tableName.split(",");
			String[] tcs = tablecomment.split("\\|\\|");
			for (int i = 0; i < tns.length; i++) {
				String tn = tns[i].trim();
				String tc = i < tcs.length ? tcs[i] : "";
				if (!tn.equals("")) {
					buildTmpl(dbExecution, tn, tc, conf);
				}
			}
		}
	}

	private static void buildTmpl(DbExecution dbExecution, String tableName, String tableComment, Configuration conf)
			throws Exception {
		tableComment = tableComment == null ? tableName : tableComment;
		System.out.println("开始构建[" + tableComment + "(" + tableName + ")]数据");
		Param param = Param.getParam();
		param.put("param", conf.getConfig());
		Param randomParam = Param.getParam();
		for (int i = 0; i < 100; i++) {
			randomParam.put("uuid" + i, CommonFunUtils.getUUID(i));
		}
		param.put("random", randomParam);
		param.put("tablename", tableName);
		param.put("utablename", tableName.toUpperCase());
		String cname = StringUtils.camelName(tableName);
		param.put("cname", cname);
		String javaname = cname.substring(0, 1).toUpperCase() + cname.substring(1);
		param.put("javaname", javaname);
		Map<String, String> bmap = BuildDbUtils.analysisStringByTmp(tableComment);
		String tc = bmap.get("@@");
		param.put("tableComment", tc);
		bmap.put("@description", StringUtils.isEmpty(bmap.get("@description")) ? tc : bmap.get("@description"));
		bmap.remove("@@");
		param.putAll(bmap);
		String spackage = tableName.split("_")[0];
		param.put("spackage", spackage);
		String packagepath = conf.getString("param.package");
		param.put("package", packagepath);
		param.put("spackagepath", packagepath.replaceAll("\\.", "/"));
		param.put("newdate", DateUtils.dateFormat(new Date(), "yyyy年MM月dd日 HH:mm:ss"));
		param.put("author", conf.getString("param.author"));
		param.put("schema", conf.getString("param.schema"));
		param.put("urlPrefix", conf.getString("param.url.prefix"));
		param.put("url", tableName.replaceAll("_", "/"));
		param.put("ucUrlPrefix", conf.getString("param.url.prefix").replace("/", "_").toUpperCase());
		param.put("ucTableName", tableName.toUpperCase());
		List<MyColumn> pks = dealColumn(dbExecution, tableName, tableComment, param);
		if (pks == null) {
			return;
		}

		String tmplpath = conf.getString("tmpl.filepath");
		File outfilepath = new File(conf.getString("out.filepath"));
		if (!outfilepath.exists()) {
			outfilepath.mkdirs();
		}

		/**
		 * 扫描文件
		 */
		List<File> ftlFiles = FileUtils.scanFile(new File(tmplpath), ".ftl");
		for (File f : ftlFiles) {
			List<String> fcs = FileUtils.readLines(f, "UTF-8");
			if (fcs.size() < 3) {
				System.out.println("文件[" + f + "]的内容配置有误");
			}
			// 名称
			String name = fcs.get(0).trim();
			// 输出路径
			String pathTemp = fcs.get(1).trim();
			// 是否检测pk
			boolean pk = CommonFunUtils.parseBoolean(fcs.get(2));
			pathTemp = TemplateUtils.templatedeal(pathTemp, param);
			fcs.remove(0);
			fcs.remove(0);
			fcs.remove(0);
			String ftl = StringUtils.join(fcs, "\n") + "\n";
			if (conf.getBoolean("param.builder." + name, true)) {
				boolean run = false;
				if (pk) {
					if (pks.size() <= 0) {
						System.out.println(
								"表[" + tableComment + "(" + tableName + ")]无主键，不自动创建" + name + "，请确定表结构是否存在异常");
					} else if (pks.size() > 1) {
						System.out.println("表[" + tableComment + "(" + tableName + ")]有多个主键，不自动创建" + name);
					} else {
						MyColumn pkColumn = pks.get(0);
						if (!String.class.equals(pkColumn.getType())) {
							System.out.println(
									"表[" + tableComment + "(" + tableName + ")]的主键不为String类型，不自动创建" + name + "");
						} else {
							param.put("pk", pkColumn);
							run = true;
						}
					}
				} else {
					run = true;
				}
				if (run) {
					File javaFile = new File(outfilepath, pathTemp);
					String cnt = TemplateUtils.freemarkerTmpl2Str(ftl, param);
					if (javaFile.getName().endsWith(".java")) {
						cnt = SourceUtils.defaultFormat(cnt);
					}
					FileUtils.writeStringToFile(javaFile, cnt, "utf-8");
					System.out.println("构建[" + tableComment + "(" + tableName + ")]的“" + name + "”数据完成");
				}
			}
		}
		System.out.println("构建[" + tableComment + "(" + tableName + ")]完成");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List<MyColumn> dealColumn(DbExecution dbExecution, String tableName, String tableComment,
			Param param) throws SQLException {
		Connection connection = basicDataSource.getConnection();
		Table table = DBUtils.getDbOperate(dbExecution).getColumn(tableName).get(0);
		if (table.getType() == 2) {
			return null;
		}
		param.put("catalog", table.getCatalog());
		if (!StringUtils.isEmpty(table.getSchema())) {
			param.put("schema", table.getSchema());
		}
		if (!StringUtils.isEmpty(StringUtils.trim(table.getRemake()))) {
			Map<String, String> bmap = BuildDbUtils.analysisStringByTmp(StringUtils.trim(table.getRemake()));
			String tc = bmap.get("@@");
			param.put("tableComment", tc);
			bmap.put("@description", StringUtils.isEmpty(bmap.get("@description")) ? tc : bmap.get("@description"));
			bmap.remove("@@");
			param.putAll(bmap);
		}
		List<Column> columns = table.getColumns();
		connection.close();

		List<MyColumn> tcolumns = new ArrayList<>();
		Boolean hasDate = false;
		boolean hasBigDecimal = false;
		for (Column column : columns) {
			if (column.getType() == null) {
				System.out.println("构建[" + tableComment + "(" + tableName + ")]时发现有字段的类型未获得，将无法构建当前代码，" + column);
				return null;
			}
		}
		for (Column column : columns) {
			MyColumn tc = BeanUtils.copyObject(column, MyColumn.class);
			tc.setRemarks(tc.getRemarks() == null ? "" : tc.getRemarks());
			if (column.getType().isAssignableFrom(Date.class)) {
				hasDate = true;
			}
			if (column.getType().isAssignableFrom(BigDecimal.class)) {
				hasBigDecimal = true;
			}
			tc.setUname(column.getColumnName().toUpperCase());
			tc.setCname(StringUtils.camelName(column.getColumnName()));
			tc.setUcname(tc.getCname().substring(0, 1).toUpperCase() + tc.getCname().substring(1));
			tc.setTypeName(column.getType().getSimpleName());
			if (column.getType().equals(String.class)) {
				tc.setNeedSize(true);
			}
			tcolumns.add(tc);
		}
		param.put("hasDate", hasDate);
		param.put("hasBigDecimal", hasBigDecimal);
		List<Map<String, Object>> cols = new ArrayList<>();
		for (MyColumn myColumn : tcolumns) {
			Map map = BeanUtils.copyObject(myColumn, Map.class);
			Map<String, String> bmap = BuildDbUtils.analysisStringByTmp(myColumn.getRemarks());
			String tc = bmap.get("@@");
			myColumn.setRemarks(tc);
			map.put("remarks", tc);
			bmap.put("@example", StringUtils.isEmpty(bmap.get("@example")) ? "" : bmap.get("@example"));
			bmap.remove("@@");
			map.putAll(bmap);
			cols.add(map);
		}
		param.put("columns", cols);
		List<MyColumn> pks = new ArrayList<>();
		for (MyColumn column : tcolumns) {
			if (column.isPk()) {
				pks.add(column);
			}
		}
		param.put("pks", pks);
		return pks;
	}

	public static void main(String[] args) throws Exception {
		buildJava();
	}
}
