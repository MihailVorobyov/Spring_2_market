package com.geekbrains.geekmarketwinter.repositories;

import com.geekbrains.geekmarketwinter.entities.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
