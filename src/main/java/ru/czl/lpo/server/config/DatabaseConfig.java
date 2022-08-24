package ru.czl.lpo.server.config;

import jakarta.persistence.Basic;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jca.support.LocalConnectionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableJpaRepositories("ru.czl.lpo.server.repository") // исп spring data на осн паттерна repository
@EnableTransactionManagement // возможность откатить транзакцию БД при критической ошибке
@PropertySource("classpath:db.properties") // путь к настройкам бд
@ComponentScan("ru.czl.lpo.server") // места репозиториев и конфигов
public class DatabaseConfig {

    @Resource
    private Environment env;  //для получения properties

    // для работы с бд,(фабрика) авт создание бинов на основе тб
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); // в качестве jpa провайдера исп Hibernate
        em.setJpaProperties(getHibernateProperties());

        return em;

    }

    @Bean
    public DataSource dataSource() { // для инициализации бина entity manager, cвязующее звено между бд и framework spring data(jdbc)
       BasicDataSource ds = new BasicDataSource();
       ds.setUrl(env.getRequiredProperty("db.url"));
       ds.setDriverClassName(env.getRequiredProperty("db.driver"));
       ds.setUsername(env.getRequiredProperty("db.username"));
       ds.setPassword(env.getRequiredProperty("db.password"));

       return ds;
    }

    private Properties getHibernateProperties() { // конф hibernate из hb.properties
        try {
            Properties properties = new Properties();
            InputStream is = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
            properties.load(is);

            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Can't find 'hibernate.properties' in project", e);
        }
    }

}
