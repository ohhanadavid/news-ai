package com.user_accessor.user_accessor.DAL.category;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
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
public class CategoryKey {
    
    @Column(name="email")
    @Email
    private String email;
    @Column(name="preference")
    private String preference;
    @Column(name="category")
    private String category;
}
