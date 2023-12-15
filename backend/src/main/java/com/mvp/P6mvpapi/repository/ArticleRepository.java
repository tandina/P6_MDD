package com.mvp.P6mvpapi.repository;

import com.mvp.P6mvpapi.models.Article;
import com.mvp.P6mvpapi.models.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> getArticlesByTheme(Theme theme);
}