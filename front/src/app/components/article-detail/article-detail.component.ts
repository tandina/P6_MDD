import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from 'src/app/services/article/article.service';
import { AuthService } from 'src/app/services/authentication/auth.service';
import { CommentService } from 'src/app/services/comment/comment.service';
import { FormBuilder, FormGroup } from '@angular/forms';



@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.css']
})
export class ArticleDetailComponent implements OnInit {
  article: any;
  username: string="";
  comments: any[]= [];
  commentForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
    private authService: AuthService,
    private fb: FormBuilder,
    private commentService: CommentService
  ) { this.commentForm = this.fb.group({
    content: '',
  });}

  ngOnInit() {
    this.commentForm = this.fb.group({
      content: ''
    });
    const id = this.route.snapshot.paramMap.get('id');
    if (id !== null) {
      this.articleService.getArticle(id).subscribe(article => {
        if (article !== null) {
          this.article = article;
          this.username = article.username;
          this.authService.getUserInfo().subscribe(userInfo => {
            if (userInfo !== null) {
              this.username = userInfo.username;
            }
          });
          this.commentService.getComments(id).subscribe(comments => {
            this.comments = comments;
            console.log(this.comments);
          });  
        }
      });
    }
  }
  postComment() {
    if (this.commentForm && this.article && this.article.id && this.commentForm.get('content')?.value) {
      const content = this.commentForm.get('content')?.value;
      this.commentService.publishComment(this.article.id, {content}).subscribe(
        response => {
        console.log('Commentaire publié avec succès', response);
      },
      error => {
        console.error('Erreur lors de la publication du commentaire', error);
      }
    );
  }
}
}
