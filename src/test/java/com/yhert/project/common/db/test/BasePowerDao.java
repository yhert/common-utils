package com.yhert.project.common.db.test;

import java.util.HashMap;

import com.yhert.project.common.db.dao.interfaces.BaseDao;

@SuppressWarnings("rawtypes")
public class BasePowerDao extends BaseDao<HashMap> {
	public static final String TABLE_NAME = "a_user";

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}
}
