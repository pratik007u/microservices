package com.demo.inventoryservice.inventoryservice.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="t_inventory")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Inventory {

    @Id
    private String id;
    private String skuCode;
    private Integer quantity;
}
