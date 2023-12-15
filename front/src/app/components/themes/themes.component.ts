import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { ThemeService } from 'src/app/services/themes/themes.service';
import { AuthService } from 'src/app/services/authentication/auth.service';
@Component({
  selector: 'app-theme',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.css']
})
export class ThemesComponent implements OnInit {
  themes: any;
  userId: string="";
  messages: { [key: string]: string } = {};

  constructor(private themeService: ThemeService, private authService: AuthService, private changeDetector: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.authService.getUserInfo().subscribe(userInfo => {
      console.log('User info:', userInfo);
      this.userId = userInfo.id;
      console.log('User ID:', this.userId);    });
    this.themeService.getThemes().subscribe(
      data => {
        this.themes = data;
        console.log(data)
      },
      error => {
        console.error(error);
      }
    );
    
  }
  subscribeToTheme(themeId: string) {

    this.themeService.subscribe(this.userId, themeId).subscribe(
      data => {
        this.messages[themeId] = data; 
        console.log('Abonnement réussi', data);
      },
      error => {
        this.messages[themeId] = error;
        console.error( error);
        this.changeDetector.detectChanges(); 
      }
    );

  }
}