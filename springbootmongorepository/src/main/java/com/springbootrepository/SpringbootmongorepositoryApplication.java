package com.springbootrepository;

import com.springbootrepository.converter.MoneyReadConverter;
import com.springbootrepository.model.Coffee;
import com.springbootrepository.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
@EnableMongoRepositories
@Slf4j
public class SpringbootmongorepositoryApplication implements CommandLineRunner {
    @Autowired
    private CoffeeRepository coffeeRepository;


    public static void main(String[] args) {
        SpringApplication.run(SpringbootmongorepositoryApplication.class, args);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
    }

    @Override
    public void run(String... args) throws Exception {
        Coffee espress = Coffee.builder()
                .name("espress")
                .price(Money.of(CurrencyUnit.of("CNY"),20.0))
                .createTime(new Date())
                .updateTime(new Date()).build();

        Coffee latte = Coffee.builder()
                .name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"),30.0))
                .createTime(new Date())
                .updateTime(new Date()).build();
        coffeeRepository.insert(Arrays.asList(espress,latte));
        coffeeRepository.findAll(Sort.by("name")).forEach(c ->log.info("coffee list{}",c));


        Thread.sleep(1000);
        latte.setPrice(Money.of(CurrencyUnit.of("CNY"),35.0));
        latte.setUpdateTime(new Date());
        coffeeRepository.save(latte);
        coffeeRepository.findByName("latte").forEach(c -> log.info("latte coffee {} ",c));

        coffeeRepository.deleteAll();
    }
}
