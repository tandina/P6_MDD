package com.mvp.P6mvpapi.service.impl;

import com.mvp.P6mvpapi.models.Theme;
import com.mvp.P6mvpapi.repository.ThemeRepository;
import com.mvp.P6mvpapi.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    @Override
    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    @Override
    public Optional<Theme> getThemeById(Integer id) {
        return themeRepository.findById(id);
    }
}