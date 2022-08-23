package ru.czl.lpo.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@Configuration
@EnableJpaRepositories("ru.czl.lpo.server.repository") // исп spring data на осн паттерна repository
@EnableTransactionManagement // возможность откатить транзакцию БД при критической ошибке
@PropertySource("classpath:db.properties") // путь к настройкам бд
@ComponentScan("ru.czl.lpo.server") // места репозиториев и конфигов
public class DatabaseConfig {

    @Resource
    private Environment env;  //для получения properties
}
