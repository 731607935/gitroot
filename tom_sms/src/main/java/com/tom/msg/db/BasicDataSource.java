package com.tom.msg.db;


import com.tom.msg.util.ConfigUtil;

public class BasicDataSource extends org.apache.commons.dbcp.BasicDataSource {

	/*public BasicDataSource() {
		super();
		this.setDriverClassName(ConfigUtil
				.getString("data-source.driver-class-name"));
		this.setUrl(ConfigUtil.getString("data-source.db-protocol") + "//"
				+ ConfigUtil.getString("data-source.db-host") + ":"
				+ ConfigUtil.getString("data-source.db-port") + "/"
				+ ConfigUtil.getString("data-source.db-name")
				+ ConfigUtil.getString("data-source.db-param"));
		this.setUsername(ConfigUtil.getString("data-source.username"));
		this.setPassword(ConfigUtil.getString("data-source.password"));
		this.setMaxActive(ConfigUtil.getInt("data-source.max-active"));
		this.setMaxIdle(ConfigUtil.getInt("data-source.max-idle"));
		this.setMaxWait(ConfigUtil.getInt("data-source.max-wait"));
		this.setInitialSize(ConfigUtil.getInt("data-source.initial-size"));
		this.setPoolPreparedStatements(ConfigUtil.getBoolean("data-source.pool-prepared-statements"));
		this.setValidationQuery(ConfigUtil.getString("data-source.validation-query"));
		this.setRemoveAbandoned(ConfigUtil.getBoolean("data-source.remove-abandoned"));
		this.setRemoveAbandonedTimeout(ConfigUtil.getInt("data-source.remove-abandoned-timeout"));
		this.setTestWhileIdle(ConfigUtil.getBoolean("data-source.test-while-idle"));
		this.setTimeBetweenEvictionRunsMillis(ConfigUtil.getInt("data-source.time-between-eviction-runs-millis"));
		this.setMinEvictableIdleTimeMillis(ConfigUtil.getInt("data-source.min-evictable-idle-time-millis"));
		this.setDefaultAutoCommit(ConfigUtil.getBoolean("data-source.default-autocommit"));
	}*/
}
