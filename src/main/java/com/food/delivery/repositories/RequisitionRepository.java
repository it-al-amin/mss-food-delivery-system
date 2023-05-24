package com.food.delivery.repositories;

import com.food.delivery.entities.Requisition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequisitionRepository extends JpaRepository<Requisition, Long> {
}
