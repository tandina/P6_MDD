package com.mvp.P6mvpapi.service;

import com.mvp.P6mvpapi.models.Article;
import com.mvp.P6mvpapi.models.Comment;
import com.mvp.P6mvpapi.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByArticle(Article article) {
        return commentRepository.findByArticle(article);
    }
}