package org.booking.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderInterface orderInterface;

    @GetMapping
    public List<OrderDto> getAllOrders()
    {
       return orderInterface.getAllOrders();
    }
}
