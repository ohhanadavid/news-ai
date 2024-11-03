package com.user_accessor.user_accessor.DAL.languege;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Accessors(chain=true)
@Table(name="language")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Language {
    @EmbeddedId
    LanguageKey languageKey;
    @Column(name="languageCode")
    private String languageCode;

}
