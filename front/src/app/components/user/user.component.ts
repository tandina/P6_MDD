import { Component,ChangeDetectorRef,OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from 'src/app/services/authentication/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-component',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit{
  id: string="";
  messages: { [key: string]: string } = {};

  constructor(private http: HttpClient, private authService: AuthService, private changeDetector: ChangeDetectorRef, private router: Router) { }

  user = {
    email: '',
    username: ''
  };
  themes: any[] = [];
  
  ngOnInit() {
    this.authService.getUserInfo().subscribe(userInfo => {
      console.log('User info:', userInfo);
      this.id = userInfo.id;
      this.themes = userInfo.themes;
      this.user.username = userInfo.username;
      this.user.email = userInfo.email;
      console.log('User ID:', this.id);    });  }
  
  updateUser() {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const url = `http://localhost:8080/api/user/auth/${this.id}/update`;
    this.http.put(url, this.user, { headers })
      .subscribe(
        data => {
          console.log('Mise à jour réussie', data);
          this.router.navigate(['/login']);
        },
        error => console.error('Erreur lors de la mise à jour de l\'utilisateur:', error)
      );
  }
  unsubscribe(themeId: string){
    const token = localStorage.getItem('token');
    const url = `http://localhost:8080/api/user/auth/${this.id}/themes/${themeId}/unsubscribe`;
    this.http.delete(url, { 
      headers: {
        'Authorization': `Bearer ${token}`
      }
    }).subscribe(
      () => {
        this.messages[themeId] = 'Désabonnement réussi';
        console.log('Désabonnement réussi');
        this.themes = this.themes.filter(theme => theme.id !== themeId);

      },
      error => {
        this.messages[themeId] = 'Erreur lors du désabonnement: ' + error.message;
        console.error(this.messages[themeId]);
    }
    );
  }

}
