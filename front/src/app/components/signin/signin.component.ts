import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/authentication/auth.service';

@Component({
  selector: 'app-signin.component',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent {
  usernameOrEmail: string="";
  password: string="";
  error403: boolean = false;

  constructor(private authService: AuthService) { }

  login() {
    this.authService.signin({usernameOrEmail: this.usernameOrEmail, password: this.password})
      .subscribe(
        data => {
          console.log(data);
        },
        error => {
          console.error('Erreur lors de la connexion', error);
          this.error403 = true;
        }
      );
  }

}
