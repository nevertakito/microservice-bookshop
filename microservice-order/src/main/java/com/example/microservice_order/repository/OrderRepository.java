package com.example.microservice_order.repository;

import com.example.microservice_order.model.Order;
import com.example.microservice_order.model.OrderStatus;
import com.example.microservice_order.model.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    Iterable<Order> findAllByUsername(String username);
    Optional<Order> findByStatusAndUsername(OrderStatus status, String username);
    Optional<Order> findByIdAndUsername(int id, String username);
}
