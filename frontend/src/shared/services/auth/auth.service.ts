import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { UserService } from '@services/user';
import { SessionStorageService } from '@services/session-storage';
import { LoginResponse } from '@models/login-response';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private httpClient = inject(HttpClient);
  private router = inject(Router);
  private userService = inject(UserService);
  private sessionStorage = inject(SessionStorageService);

  public async login(login: string, password: string) {
    const loginResponse = await firstValueFrom(
      this.httpClient.post<LoginResponse>('auth/login', { login, password }),
    );

    const user = loginResponse.user;
    const token = loginResponse.token;

    this.sessionStorage.set('authorized', true);
    this.sessionStorage.set('login', user.login);
    this.sessionStorage.set('token', token);

    const isAdmin = await this.isAdmin(user.login);
    this.sessionStorage.set('isAdmin', isAdmin);

    this.userService.login(login, isAdmin);

    this.router.navigate(['main']);
    return true;
  }

  public logout(): void {
    this.sessionStorage.clear();

    this.userService.logout();

    this.router.navigate(['login']);
  }

  private async isAdmin(login: string): Promise<boolean> {
    const user = await this.userService.findUserByLogin(login);
    return user.roles.includes('Администратор');
  }
}
