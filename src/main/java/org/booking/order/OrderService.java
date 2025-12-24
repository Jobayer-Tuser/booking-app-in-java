package org.booking.order;

import lombok.RequiredArgsConstructor;
import org.booking.auth.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderInterface {

    private final AuthService authService;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Override
    public List<OrderDto> getAllOrders()
    {
        var user = authService.getCurentUser();
        var orders = orderRepository.getAllOrderByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }
}
