import { Component, computed, inject } from '@angular/core';
import { NgOptimizedImage } from '@angular/common';
import { AuthService } from '@services/auth';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [NgOptimizedImage, RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  public authService = inject(AuthService);

  public isAuthenticated = computed(this.authService.isAuthorized);
  public userLogin = computed(this.authService.currentUserLogin);
  public isAdmin = computed(this.authService.isAdmin);

  public logout(): void {
    this.authService.logout();
  }
}
