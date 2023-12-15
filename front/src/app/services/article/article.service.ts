import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { AuthService } from "../authentication/auth.service";

@Injectable({
  providedIn: "root",
})
export class ArticleService {
  private url = "http://localhost:8080/api/auth/article/create";
  private allArticleUrl = "http://localhost:8080/api/auth/article";

  constructor(private http: HttpClient, private authService: AuthService) {}

  createArticle(article: any): Observable<any> {
    const token = localStorage.getItem("token");
    const userId = this.authService.getCurrentUser().id;
    console.log(`Token: ${token}`);
    console.log(`UserId: ${userId}`);
    const headers = new HttpHeaders()
      .set("Authorization", `Bearer ${token}`)
      .set("userId", userId.toString());
    return this.http.post(this.url, article, { headers });
  }

  getAllArticles(): Observable<any> {
    const token = localStorage.getItem("token");
    console.log("Token envoyé:", token);
    const userId = this.authService.getCurrentUser().id;

    const headers = new HttpHeaders()
      .set("Authorization", `Bearer ${token}`)
      .set("userId", userId.toString());
    return this.http.get(this.allArticleUrl, { headers });
  }

  getArticle(id: string): Observable<any> {
    const token = localStorage.getItem("token");
    const headers = new HttpHeaders().set("Authorization", `Bearer ${token}`);
    return this.http.get(`${this.allArticleUrl}/${id}`, { headers });
  }
}
