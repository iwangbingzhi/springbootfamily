package com.springbootmongo;

import com.mongodb.client.result.UpdateResult;
import com.springbootmongo.converter.MoneyReadConverter;
import com.springbootmongo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
public class SpringbootmongoApplication implements ApplicationRunner {
    @Autowired
    private MongoTemplate mongoTemplate;


    public static void main(String[] args) {
        SpringApplication.run(SpringbootmongoApplication.class, args);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Coffee espresso = Coffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"),20.0))
                .createTime(new Date())
                .updateTime(new Date()).build();
        Coffee save = mongoTemplate.save(espresso);
        log.info("coffee {}",save);

        List<Coffee> list = mongoTemplate.find(query(where("name")
                            .is("espresso")),Coffee.class);
        log.info("coffee {} 单",list.size());
        list.forEach(c -> log.info("coffee {}",c));

        Thread.sleep(1000);
        UpdateResult result = mongoTemplate.updateFirst(query(where("name")
        .is("espresso")),new Update().set("price",Money.ofMajor(CurrencyUnit.of("CNY"),30))
                .currentDate("updateTime"),Coffee.class);
        log.info("updatecoffee coffee{} ",result.getModifiedCount());
        Coffee id = mongoTemplate.findById(save.getId(), Coffee.class);
        log.info("update result:{}",id);
        mongoTemplate.remove(id);

    }
}
