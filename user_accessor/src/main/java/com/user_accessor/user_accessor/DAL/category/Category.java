package com.user_accessor.user_accessor.DAL.category;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Table(name="category")
@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
    @EmbeddedId
    private  CategoryKey category;

}
