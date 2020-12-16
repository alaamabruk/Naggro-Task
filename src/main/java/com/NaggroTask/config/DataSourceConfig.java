package com.NaggroTask.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class DataSourceConfig {

	@Value("${db.conectionstring}")
	private String connectionString;

	@Bean
	public DataSource createDataSource() throws Exception {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setJdbcUrl(connectionString);
		ds.setDriverClass("net.ucanaccess.jdbc.UcanaccessDriver");
		return ds;
	}

}
