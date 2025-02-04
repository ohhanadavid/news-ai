package com.user_accessor.user_accessor.DAL.languege;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString(includeFieldNames=true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@Embeddable
public class LanguageKey {
    
    @Column(name="email")
    @NonNull
    @Email
    private String email;
    @Column(name="language")
    @NonNull
    private String language;
    

}
