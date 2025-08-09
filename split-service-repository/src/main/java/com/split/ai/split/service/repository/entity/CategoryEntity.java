package com.split.ai.split.service.repository.entity;

import com.split.ai.split.service.model.enums.CATEGORY;
import com.split.ai.split.service.model.enums.SUB_CATEGORY;
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
    private SUB_CATEGORY subCategory;

    @Column(nullable = false)
    private CATEGORY category;

    private String subCategoryImageUrl;

}
