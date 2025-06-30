import { Component, computed, inject } from '@angular/core';
import { NgOptimizedImage } from '@angular/common';
import { AuthService } from '@services/auth';

@Component({
  selector: 'app-header',
  imports: [NgOptimizedImage],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  public authService = inject(AuthService);

  public isAuthenticated = computed(this.authService.isAuthorized);
  public userLogin = computed(this.authService.currentUserLogin);

  public logout(): void {
    this.authService.logout();
  }
}
