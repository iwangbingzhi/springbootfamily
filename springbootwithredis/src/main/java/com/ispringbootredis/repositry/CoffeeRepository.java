package com.ispringbootredis.repositry;

import com.ispringbootredis.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee,Long> {
}
