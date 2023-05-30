package com.food.delivery.services;

import com.food.delivery.dto.RequisitionDTO;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface RequisitionService {

    void buyFood(Principal principal, Long foodId);

    List<RequisitionDTO> getAllOrders();

}
