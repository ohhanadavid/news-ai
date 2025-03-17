package com.news_manger.news_manager.BL.servises;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ILanguageService {

    public List<String> getLanguagesCode();
}
