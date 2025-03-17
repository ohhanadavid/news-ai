package com.data_manager.data_manager.DAL.modol.category;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Table(name="category")
@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(includeFieldNames = true)
public class CategoryToDB {
    @EmbeddedId
    private CategoryKeyForDB category;

}
