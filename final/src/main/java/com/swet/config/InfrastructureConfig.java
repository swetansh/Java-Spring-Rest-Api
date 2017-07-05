package com.swet.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Enable and scan Spring Data repositories.
 */
@Configuration
@EnableTransactionManagement
public class InfrastructureConfig {
	@Autowired
	private DataSource dataSource;

	// Get the property values
	@Autowired
	Environment env;

	// Spring factory bean to set up Spring with JPA
	private LocalContainerEntityManagerFactoryBean em;

	

	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		if (em == null) {
			em = new LocalContainerEntityManagerFactoryBean();
			em.setDataSource(dataSource);
			em.setPackagesToScan(new String[] {"com.swet.dto" });

			JpaVendorAdapter vendorAdapter = jpaVendorAdapter();
			em.setJpaVendorAdapter(vendorAdapter);

			Properties properties = new Properties();
			properties.put("hibernate.validator.apply_to_ddl",true);
			properties.put("hibernate.validator.autoregister_listeners",
				false);
			properties.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
			properties.put("hibernate.show_sql",
				true);
			properties.put("hibernate.format_sql",
				false);
			properties.put("hibernate.hbm2ddl.auto",
				"update");
			properties.put("hibernate.generate_statistics",
				false);

			em.setJpaProperties(properties);
		}

		return em;
	}
	
	@Bean(name="transactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(em.getObject());

		return transactionManager;
	}

	

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter vendorAdapter =
			new HibernateJpaVendorAdapter();

		return vendorAdapter;
	}

	
}
