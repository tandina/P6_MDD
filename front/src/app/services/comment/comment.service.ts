import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../authentication/auth.service';


@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private url = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getComments(articleId: string): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(`${this.url}/${articleId}/comments`, { headers });
  }
  publishComment(articleId: string, comment: {content: string}): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = this.authService.getCurrentUser().id;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`).set('userId', userId.toString());
    return this.http.post(`${this.url}/user/${articleId}/comments`, comment, { headers }); 
  }
}
