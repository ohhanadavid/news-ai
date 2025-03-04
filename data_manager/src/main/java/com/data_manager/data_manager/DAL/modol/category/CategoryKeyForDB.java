package com.data_manager.data_manager.DAL.modol.category;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString(includeFieldNames=true)
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CategoryKeyForDB {
    
    @Column(name="userID")
    private String userID;
    @Column(name="preference")
    private String preference;
    @Column(name="category")
    private String category;
}
