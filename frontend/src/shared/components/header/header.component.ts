import { Component, inject } from '@angular/core';
import { NgOptimizedImage } from '@angular/common';
import { AuthService } from '@services/auth';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { UserService } from '@services/user';

@Component({
  selector: 'app-header',
  imports: [NgOptimizedImage, RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  public authService = inject(AuthService);
  public userService = inject(UserService);

  public isAuthenticated = this.userService.isAuthenticated;
  public userLogin = this.userService.userLogin;
  public isAdmin = this.userService.isAdmin;

  public logout(): void {
    this.authService.logout();
  }
}
