import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { ThemeService } from "src/app/services/themes/themes.service";
import { ArticleService } from "src/app/services/article/article.service";
import { AuthService } from "src/app/services/authentication/auth.service";
import { Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { Location } from "@angular/common";

@Component({
  selector: "app-article.component",
  templateUrl: "./article.component.html",
  styleUrls: ["./article.component.css"],
})
export class ArticleComponent implements OnInit {
  articleForm: FormGroup;
  themes: any[] = [];
  userThemes: any[] = [];

  articles: any[] = [];
  username: string = "";
  showCreateArticleForm: boolean;
  isReversed = false;

  constructor(
    private fb: FormBuilder,
    private themeService: ThemeService,
    private articleService: ArticleService,
    private authService: AuthService,
    private router: Router,
    private location: Location
  ) {
    this.articleForm = this.fb.group({
      title: ["", Validators.maxLength(40)],
      content: ["", Validators.maxLength(500)],
      theme: {
        id: "",
      },
    });
    this.showCreateArticleForm = false;
  }
  //ternaire : boutton créer / annuler
  toggleCreateArticleForm() {
    this.showCreateArticleForm = !this.showCreateArticleForm;
  }
  render(): void {
    this.router
      .navigateByUrl("/articles", { skipLocationChange: true })
      .then(() => {
        console.log(decodeURI(this.location.path()));
        this.router.navigate([decodeURI(this.location.path())]);
      });
  }
  ngOnInit() {
    this.articleForm = this.fb.group({
      theme: "",
      title: "",
      content: "",
      userThemes:"",
    });

    this.authService.getUserInfo().subscribe((userInfo) => {
      console.log("User info:", userInfo);
      this.username = userInfo.username;
      this.userThemes= userInfo.themes;
      console.log('liste des themes de l/user',userInfo.themes)
      console.log('liste des themes ',this.userThemes)

    });

    this.themeService.getThemes().subscribe(
      (data) => {
        this.themes = data;
      },
      (error) => {
        console.error(error);
      }
    );

    this.articleService.getAllArticles().subscribe(
      (data) => {
        this.articles = data;
        console.log("getAllArticles", data);
      },
      (error) => {
        console.error(error);
      }
    );

    this.articleService.getAllArticles().subscribe(
      (data) => {
        this.articles = data.sort(
          (a: any, b: any) =>
            new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime()
        );
        console.log("getAllArticles", this.articles);
      },
      (error) => {
        console.error(error);
      }
    );
  }
  createArticle() {
    const formValue = this.articleForm.value;
    formValue.theme = { id: formValue.theme };

    this.articleService.createArticle(formValue).subscribe(
      (data) => {
        this.showCreateArticleForm = false;

        console.log("Article créé avec succès", data);
      },
      (error) => {
        console.error("Erreur lors de la création de l'article", error);
      }
    );
  }
  reverseArticlesOrder() {
    this.articles = this.articles.reverse();
    this.isReversed = !this.isReversed;
  }
}
