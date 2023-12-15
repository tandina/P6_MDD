package com.mvp.P6mvpapi.service;

import com.mvp.P6mvpapi.models.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeService {
    List<Theme> getAllThemes();
    Optional<Theme> getThemeById(Integer id);

}