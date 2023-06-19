package com.demo.orderservice.service;

import com.demo.orderservice.dto.InventoryResponse;
import com.demo.orderservice.dto.OrderLineItemsDto;
import com.demo.orderservice.dto.OrderRequest;
import com.demo.orderservice.dto.OrderResponse;
import com.demo.orderservice.inventoryfeignclient.InventoryFeignClient;
import com.demo.orderservice.model.Order;
import com.demo.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient webClient;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    private InventoryFeignClient inventoryResponse;

    public Boolean placeOrder(OrderRequest orderRequest) {
        Boolean result;
        Order order = Order.builder()
                .price(orderRequest.getPrice())
                .orderNumber((UUID.randomUUID()).toString())
                .skuCode(orderRequest.getSkuCode())
                .quantity(orderRequest.getQuantity())
                .build();


        // call Inventory service, and place order if product is in
        //stock
        //        InventoryResponse inventoryResponse = webClientBuilder.build().get()
        //                .uri("http://inventory-service/api/inventory/" + order.getSkuCode())
        //                .retrieve()
        //                .bodyToMono(InventoryResponse.class)
        //                .block();
        ResponseEntity<?> response = inventoryResponse.getInventory(order.getSkuCode().toString());

        if (response.getBody() != null) {
            orderRepository.save(order);
            result = true;
        } else {
            result = false;
            throw new IllegalArgumentException("Product is not in stock, please try again later");

        }
        return result;
    }

    private Order mapToDto(OrderLineItemsDto orderLineItemsDto) {
        Order orderLineItems = new Order();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

    public List<OrderResponse> getAllOrder() {

        List<Order> orderList = orderRepository.findAll();
        return orderList.stream().map(this::mapToOrderResponse).toList();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderNumber(order.getOrderNumber())
                .skuCode(order.getSkuCode())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .build();
    }
}
