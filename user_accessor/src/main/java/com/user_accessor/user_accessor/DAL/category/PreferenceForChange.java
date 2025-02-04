package com.user_accessor.user_accessor.DAL.category;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceForChange {
    private String category;
    private String oldPreference;
    private String newPreference;
}
