import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { SignupComponent } from "./components/signup/signup.component";
import { SigninComponent } from "./components/signin/signin.component";
import { ThemesComponent } from "./components/themes/themes.component";
import { ArticleComponent } from "./components/article/article.component";
import { HomeComponent } from "./components/home/home/home.component";
import { CommentComponent } from "./components/comment/comment.component";
import { UserComponent } from "./components/user/user.component";
import { ArticleDetailComponent } from "./components/article-detail/article-detail.component";
import { AuthGuard } from "./AuthGuard";
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: "signup", component: SignupComponent },
  { path: "user", component: UserComponent, canActivate: [AuthGuard] },
  { path: "login", component: SigninComponent },
  { path: "themes", component: ThemesComponent, canActivate: [AuthGuard] },
  { path: "articles", component: ArticleComponent, canActivate: [AuthGuard] },
  { path: 'articles/:id', component: ArticleDetailComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
