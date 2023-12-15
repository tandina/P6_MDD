import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private url = 'http://localhost:8080/api/auth/themes';

  constructor(private http: HttpClient) { }

  getThemes(): Observable<any> {
    const token = localStorage.getItem('token');
    console.log('Token envoyé:', token);
    return this.http.get(this.url, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
  }
  subscribe(userId: string, themeId: string) {
    const token = localStorage.getItem('token');

    const url = `http://localhost:8080/api/user/auth/${userId}/themes/${themeId}/subscribe`;
    return this.http.post(url, {}, { 
      headers: {
        'Authorization': `Bearer ${token}`
      },
      responseType: 'text'
    }).pipe(
      catchError((error) => {
        return throwError(error.error);
      })
    );
  }
}