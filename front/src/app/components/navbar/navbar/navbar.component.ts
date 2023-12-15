import { Component, HostListener, ElementRef } from '@angular/core';
import { AuthService } from 'src/app/services/authentication/auth.service';
import { OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {
  isLoggedIn!: boolean;
  private subscription!: Subscription;
  isMenuOpen = false;

  constructor(public authService: AuthService,private eRef: ElementRef) { }

  ngOnInit() {
    this.subscription = this.authService.isLoggedIn.subscribe(
      (loggedIn) => {
        this.isLoggedIn = loggedIn;
      }
    );

    const user = this.authService.getCurrentUser();
    if (user) {
      this.isLoggedIn = true;
    } else {
      this.isLoggedIn = false;
    }
  }
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }  }

    @HostListener('document:click', ['$event'])
  clickout(event: any) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      this.isMenuOpen = false;  
    }
  }
}


