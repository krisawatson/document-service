package com.watsonsoftware.document.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Data
@NoArgsConstructor
@PropertySource(
        "${DOC_DB_PROPERTIES:classpath:properties/db.properties}")
@EnableJpaRepositories(
        basePackages = "com.watsonsoftware.document.repository"
)
public class DocumentDatabaseConfiguration {

    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "db-properties")
    @ConfigurationProperties("doc.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "database")
    @ConfigurationProperties("doc.datasource")
    public DataSource dataSource() {
        return dataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);

        final DataSource dataSource = dataSource();
        final LocalContainerEntityManagerFactoryBean factoryBean = new
                LocalContainerEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitName("entityManager");
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan("com.watsonsoftware.document.model.entity");
        factoryBean.setJpaPropertyMap(jpaPropertyMap());

        return factoryBean;
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    private Map jpaPropertyMap() {
        final HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("doc.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",
                env.getProperty("doc.hibernate.dialect"));
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);

        return properties;
    }
}
