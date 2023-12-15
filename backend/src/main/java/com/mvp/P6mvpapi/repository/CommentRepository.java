package com.mvp.P6mvpapi.repository;

import com.mvp.P6mvpapi.models.Article;
import com.mvp.P6mvpapi.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByArticle(Article article);
}
