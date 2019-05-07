package com.ispringbootredis.service;

import com.ispringbootredis.model.Coffee;
import com.ispringbootredis.repositry.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
@Transactional
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    public List<Coffee> findAllCoffee(){
        return coffeeRepository.findAll();
    }

    public Optional<Coffee> findOneCoffee(String name){
        ExampleMatcher matcherCoffee =
                ExampleMatcher.matching().withMatcher("name", exact().ignoreCase());
        Optional<Coffee> coffee =
                coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build()
                ,matcherCoffee));
        log.info("find coffee{}",coffee);
        return coffee;
    }
}
