package com.data_manager.data_manager.DAL.modol.languege;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@ToString(includeFieldNames=true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@Embeddable
public class LanguageKey {
    
    @Column(name="userID")
    @NonNull
    private String userID;
    @Column(name="language")
    @NonNull
    private String language;
    

}
