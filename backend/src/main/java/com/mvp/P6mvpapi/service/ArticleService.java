package com.mvp.P6mvpapi.service;

import com.mvp.P6mvpapi.models.Article;
import com.mvp.P6mvpapi.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ArticleService {
    List<Article> getAllArticles();
    Optional<Article> getArticleById(Integer id);
    Article createArticle(Article article);

    List<Article> getArticlesByUserThemes(User user);
}