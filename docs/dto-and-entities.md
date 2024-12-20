# Data Models and DTOs in StockSync

This document outlines our data modeling patterns for the inventory management system, including both persistence entities and data transfer objects used in WebSocket communication.

## Domain Models

Our inventory system uses carefully structured domain models that capture the essential aspects of inventory management:

```java
@Entity
@Table(name = "inventory_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String productId;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private String productName;
    
    @Column(nullable = false)
    private LocalDateTime lastUpdated;
    
    // Version field for optimistic locking
    @Version
    private Long version;
    
    // Audit fields
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
```

## Data Transfer Objects

We use specific DTOs for WebSocket communication to ensure clean data transfer and version management:

```java
@Data
@Builder
public class InventoryUpdateDTO {
    private String productId;
    private Integer quantity;
    private String productName;
    private LocalDateTime timestamp;
    private String updateType;  // STOCK_ADDED, STOCK_REMOVED, etc.
    private Long version;       // For optimistic locking
    
    // Additional fields for update metadata
    private String updatedBy;
    private String updateReason;
    private boolean urgent;
}
```

## Response Models

Our system uses structured response models for consistent error handling and success responses:

```java
@Data
@Builder
public class InventoryResponse {
    private String status;         // SUCCESS, ERROR
    private String message;
    private LocalDateTime timestamp;
    private InventoryUpdateDTO data;
    
    // Helper methods for creating standard responses
    public static InventoryResponse success(InventoryUpdateDTO data) {
        return InventoryResponse.builder()
                .status("SUCCESS")
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }
    
    public static InventoryResponse error(String message) {
        return InventoryResponse.builder()
                .status("ERROR")
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
```

## Model Mapping Patterns

We use MapStruct for efficient model mapping between entities and DTOs:

```java
@Mapper(componentModel = "spring")
public interface InventoryMapper {
    
    InventoryUpdateDTO toDto(InventoryItem entity);
    
    // Custom mapping for updates
    @Mapping(target = "lastUpdated", source = "timestamp")
    InventoryItem toEntity(InventoryUpdateDTO dto);
    
    // Batch mapping methods
    List<InventoryUpdateDTO> toDtoList(List<InventoryItem> entities);
}
```

## Validation Patterns

We implement comprehensive validation using Jakarta Validation:

```java
@Data
public class InventoryUpdateDTO {
    @NotNull(message = "Product ID is required")
    private String productId;
    
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;
    
    @NotBlank(message = "Product name is required")
    private String productName;
    
    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;
    
    @Pattern(regexp = "STOCK_ADDED|STOCK_REMOVED|STOCK_ADJUSTED",
            message = "Invalid update type")
    private String updateType;
}
```

## Best Practices

When working with our data models:

1. Always use DTOs for WebSocket communication instead of entities
2. Implement proper validation at both DTO and entity levels
3. Use optimistic locking for concurrent update handling
4. Include audit fields for tracking changes
5. Keep DTOs focused and specific to their use cases

These patterns ensure consistent and reliable data handling throughout our real-time inventory system.