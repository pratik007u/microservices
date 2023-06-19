package com.demo.inventoryservice.inventoryservice.service;

import com.demo.inventoryservice.inventoryservice.dto.InventoryResponse;
import com.demo.inventoryservice.inventoryservice.model.Inventory;
import com.demo.inventoryservice.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public InventoryResponse isInStock(String skuCode){
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode);
        if(inventory!=null){
            InventoryResponse inventoryResponse=InventoryResponse.builder()
                    .id(inventory.getId())
                    .skuCode(inventory.getSkuCode())
                    .quantity(inventory.getQuantity())
                    .build();
            return inventoryResponse;
        }
        return null;
    }

    public void saveStock() {
        Inventory inventory = Inventory.builder()
                .id(UUID.randomUUID().toString())
                .skuCode("Iphone_14")
                .quantity(100)
                .build();
        inventoryRepository.save(inventory);

    }
}
