package com.mvp.P6mvpapi.service.impl;

import com.mvp.P6mvpapi.models.Article;
import com.mvp.P6mvpapi.models.Theme;
import com.mvp.P6mvpapi.models.User;
import com.mvp.P6mvpapi.repository.ArticleRepository;
import com.mvp.P6mvpapi.service.ArticleService;
import com.mvp.P6mvpapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private UserService userService;

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> getArticleById(Integer id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article createArticle(Article article) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
    article.setUsername(username);
        return articleRepository.save(article);
    }

    @Override
    public List<Article> getArticlesByUserThemes(User user) {
        List<Theme> themes = userService.getThemesByUser(user);
        List<Article> articles = new ArrayList<>();
        for (Theme theme : themes) {
            articles.addAll(articleRepository.getArticlesByTheme(theme));
        }
        return articles;
    }
}