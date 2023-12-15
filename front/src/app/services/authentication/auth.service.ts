import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, of, BehaviorSubject } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";
import { Router } from "@angular/router";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private signinUrl = "http://localhost:8080/api/auth/signing";
  private signupUrl = "http://localhost:8080/api/auth/signup";
  private authUrl = "http://localhost:8080/api/user/auth";

  private loggedIn = new BehaviorSubject<boolean>(false);

  get isLoggedIn() {
    return this.loggedIn.asObservable();
  }
  constructor(private http: HttpClient, private router: Router) {}

  getCurrentUser() {
    const user = localStorage.getItem("user");
    console.log("User:", user ? JSON.parse(user) : null);
    return user ? JSON.parse(user) : null;
  }

  signin(credentials: {
    usernameOrEmail: string;
    password: string;
  }): Observable<any> {
    return this.http.post(this.signinUrl, credentials).pipe(
      tap((response: any) => {
        localStorage.setItem("token", response.token);
        localStorage.setItem("user", JSON.stringify(response.userInfo));
        this.loggedIn.next(true); 
        console.log("log de signin", response.token);
        this.router.navigate(["/themes"]);
      })
    );
  }

  signup(user: {
    username: string;
    password: string;
    email: string;
  }): Observable<any> {
    return this.http.post(this.signupUrl, user);
  }
  logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    this.loggedIn.next(false);
    this.router.navigate(["/login"]);
  }
  getUserInfo(): Observable<any> {
    const token = localStorage.getItem("token");

    if (!token) {
      return of(null);
    }

    return this.http
      .get(this.authUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .pipe(
        tap((response) => {
          console.log("Response from authUrl:", response);
          localStorage.setItem("user", JSON.stringify(response));
        }),
        catchError((error) => {
          console.error("Failed to get user info", error);
          return of(null);
        })
      );
  }
  isAuthenticated(): Observable<boolean> {
    const token = localStorage.getItem("token");

    if (!token) {
      return of(false);
    }

    return this.http
      .get(this.authUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .pipe(
        map((response) => {
          localStorage.setItem("user", JSON.stringify(response));
          console.log("Response from authUrl:", response);
          return !!response;
        }),
        catchError((error) => {
          console.error("Authentication failed", error);
          return of(false);
        })
      );
  }
}
