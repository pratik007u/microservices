package com.demo.inventoryservice.inventoryservice.controller;


import com.demo.inventoryservice.inventoryservice.dto.InventoryResponse;
import com.demo.inventoryservice.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse isInStock(@PathVariable("sku-code") String skuCode){

        InventoryResponse inventoryResponse= inventoryService.isInStock(skuCode);
        return inventoryResponse;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
     public String stock()
    {
        inventoryService.saveStock();
        return "Stock Inserted Successfully";
    }
}
