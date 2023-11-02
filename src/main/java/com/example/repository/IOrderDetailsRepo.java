package com.example.repository;

import com.example.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDetailsRepo extends JpaRepository<OrderDetails , Integer> {

}
