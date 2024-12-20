package com.horrorcoresoftware.inventory.dto;

import com.horrorcoresoftware.inventory.enums.UpdateType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InventoryUpdateDTO {
    private String productId;
    private Integer quantity;
    private String productName;
    private LocalDateTime timestamp;
    private UpdateType updateType; // Could be "STOCK_ADDED", "STOCK_REMOVED", etc.
}