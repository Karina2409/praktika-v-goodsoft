import { Component, computed, inject } from '@angular/core';
import { NgOptimizedImage } from '@angular/common';
import { AuthService } from '@services/auth';
import { Router } from '@angular/router';

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
  public isAdmin = computed(this.authService.isAdmin);

  private router = inject(Router);

  public logout(): void {
    this.authService.logout();
  }

  public goToMain(): void {
    this.router.navigate(['main']);
  }

  public goToUsers(): void {
    this.router.navigate(['users']);
  }
}
