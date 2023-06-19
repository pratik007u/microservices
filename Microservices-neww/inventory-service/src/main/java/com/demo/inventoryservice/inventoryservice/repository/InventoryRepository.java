package com.demo.inventoryservice.inventoryservice.repository;

import com.demo.inventoryservice.inventoryservice.dto.InventoryResponse;
import com.demo.inventoryservice.inventoryservice.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InventoryRepository extends MongoRepository<Inventory, Long> {
   Inventory findBySkuCode(String skuCode);
}
