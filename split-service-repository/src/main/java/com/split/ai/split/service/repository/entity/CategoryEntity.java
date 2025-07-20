package com.split.ai.split.service.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    private UUID categoryId;

    // todo: Replace with Subcategory ENUM
    @Column(nullable = false)
    private String subCategory;

    // todo: Replace with Category ENUM
    @Column(nullable = false)
    private String category;

    private String subCategoryImageUrl;
}
