package com.mvp.P6mvpapi.controllers;

import com.mvp.P6mvpapi.exceptions.ThemeNotFoundException;
import com.mvp.P6mvpapi.models.Theme;
import com.mvp.P6mvpapi.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ThemeController {
    @Autowired
    private final ThemeRepository themeRepository;

    @GetMapping("auth/themes")
    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    @GetMapping("auth/themes/{id}")
    public ResponseEntity<Theme> getThemeById(@PathVariable Integer id) {
        Theme theme = themeRepository.findById(id)
            .orElseThrow(() -> new ThemeNotFoundException("Theme not found with id :" + id));
        return ResponseEntity.ok().body(theme);
    }
}
