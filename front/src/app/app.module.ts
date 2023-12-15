import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from "@angular/forms";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { SignupComponent } from "./components/signup/signup.component";
import { SigninComponent } from "./components/signin/signin.component";
import { ThemesComponent } from "./components/themes/themes.component";
import { ArticleComponent } from "./components/article/article.component";
import { CommentComponent } from "./components/comment/comment.component";
import { HomeComponent } from "./components/home/home/home.component";
import { NavbarComponent } from "./components/navbar/navbar/navbar.component";
import { UserComponent } from "./components/user/user.component";
import { ArticleDetailComponent } from "./components/article-detail/article-detail.component";

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    SigninComponent,
    ThemesComponent,
    ArticleComponent,

    CommentComponent,
    HomeComponent,
    NavbarComponent,
    UserComponent,
    ArticleDetailComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
