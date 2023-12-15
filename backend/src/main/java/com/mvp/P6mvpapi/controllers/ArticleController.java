package com.mvp.P6mvpapi.controllers;

import com.mvp.P6mvpapi.models.Article;
import com.mvp.P6mvpapi.models.Comment;
import com.mvp.P6mvpapi.models.Theme;
import com.mvp.P6mvpapi.models.User;
import com.mvp.P6mvpapi.repository.ArticleRepository;

import com.mvp.P6mvpapi.service.ArticleService;
import com.mvp.P6mvpapi.service.CommentService;
import com.mvp.P6mvpapi.service.ThemeService;
import com.mvp.P6mvpapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor

public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    private ThemeService themeService;


    @Autowired
    private CommentService commentService;

    @GetMapping("auth/article")
    public ResponseEntity<List<Article>> getAllArticles(@RequestHeader("userId") Integer userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(new ArrayList<Article>());
        }
        List<Article> articles = articleService.getArticlesByUserThemes(user);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("auth/article/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Integer id) {
        Optional<Article> article = articleService.getArticleById(id);
        if (article.isPresent()) {
            return ResponseEntity.ok(article.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("auth/article/create")
    public ResponseEntity<?> createArticle(@RequestBody Article article, @RequestHeader("userId") Integer userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("L'utilisateur n'existe pas");
        }
        Set<Theme> userThemes = user.getThemes();
        boolean isSubscribed = userThemes.stream().anyMatch(theme -> theme.getId().equals(article.getTheme().getId()));
        if (!isSubscribed) {
            return ResponseEntity.badRequest().body("L'utilisateur n'est pas abonné au thème de l'article");
        }
        article.setUser(user);
        try {
            return ResponseEntity.ok(articleService.createArticle(article));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création de l'article");
        }
    }

    @PostMapping("auth/user/{articleId}/comments")
    public ResponseEntity<?> createComment(@RequestHeader("userId") Integer userId, @PathVariable Integer articleId, @RequestBody Comment comment) {
        Optional<User> user = Optional.ofNullable(userService.findById(userId));
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("L'utilisateur n'existe pas");
        }
        Optional<Article> article = articleService.getArticleById(articleId);
        if (!article.isPresent()) {
            return ResponseEntity.badRequest().body("L'article n'existe pas");
        }
        comment.setArticle(article.get());
        comment.setUser(user.get());
        comment.setUsername(user.get().getUsername());
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping("auth/{articleId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByArticleId(@PathVariable Integer articleId) {
        Optional<Article> article = articleService.getArticleById(articleId);
        if (!article.isPresent()) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        List<Comment> comments = commentService.getCommentsByArticle(article.get());
        return ResponseEntity.ok(comments);
    }
}
