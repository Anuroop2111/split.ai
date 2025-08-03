package com.split.ai.split.service.repository.entity;

import com.split.ai.split.service.model.enums.Category;
import com.split.ai.split.service.model.enums.SubCategory;
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

    @Column(nullable = false)
    private SubCategory subCategory;

    @Column(nullable = false)
    private Category category;

    private String subCategoryImageUrl;

}
