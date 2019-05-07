package com.ispringbootredis.service;

import com.ispringbootredis.model.Coffee;
import com.ispringbootredis.model.CoffeeOrder;
import com.ispringbootredis.model.OrderState;
import com.ispringbootredis.repositry.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CoffeeOrderService {
    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;
    public CoffeeOrder createOrder(String customer,Coffee...coffee){
        CoffeeOrder order = CoffeeOrder.builder().customer(customer)
                .items(new ArrayList<>(Arrays.asList(coffee)))
                .state(OrderState.INIT)
                .build();
        CoffeeOrder save = coffeeOrderRepository.save(order);
        log.info("新的订单{}",save);
        return save;
    }

    public boolean updateOrder(CoffeeOrder order,OrderState state){
        if (state.compareTo(order.getState())<=0) {
            log.warn("错误的订单状态{},{}",state,order.getState());
            return false;
        }
        order.setState(state);
        coffeeOrderRepository.save(order);
        log.info("升级订单{}",order);
        return true;
    }
}
