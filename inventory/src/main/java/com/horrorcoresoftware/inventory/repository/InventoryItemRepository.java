package com.horrorcoresoftware.inventory.repository;

import com.horrorcoresoftware.inventory.models.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
}
