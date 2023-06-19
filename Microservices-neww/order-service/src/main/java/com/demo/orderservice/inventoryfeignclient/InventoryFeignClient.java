package com.demo.orderservice.inventoryfeignclient;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "inventory-service")
public interface InventoryFeignClient {

    @GetMapping("/api/inventory/{sku-code}")
    ResponseEntity<?>  getInventory(@PathVariable("sku-code") String skuCode);

}
