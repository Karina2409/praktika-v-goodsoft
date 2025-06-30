import { effect, inject, Injectable, signal, WritableSignal } from '@angular/core';
import { UserService } from '@services/user';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public userService = inject(UserService);

  public currentUserLogin: WritableSignal<string> = signal(
    sessionStorage.getItem('login') as string,
  );
  public isAuthorized: WritableSignal<boolean> = signal<boolean>(
    !!sessionStorage.getItem('authorized'),
  );

  public isAdmin: WritableSignal<boolean> = signal(false);

  private router = inject(Router);

  constructor() {
    effect(() => {
      const login = this.currentUserLogin();
      if (login) {
        this.userService.isAdmin(login).then((result) => this.isAdmin.set(result));
      } else {
        this.isAdmin.set(false);
      }
    });
  }

  public async login(login: string, password: string) {
    const users = await this.userService.findAllUsers();
    if (!users) return false;
    const user = users.find((user) => user.login === login && user.password === password);
    sessionStorage.setItem('authorized', String(!!user));
    if (user) {
      this.isAuthorized.set(true);
      sessionStorage.setItem('login', user.login);
      this.currentUserLogin.set(user.login);
      this.router.navigate(['main']);
    }
    return !!user;
  }

  public logout(): void {
    sessionStorage.removeItem('login');
    sessionStorage.removeItem('authorized');
    this.isAuthorized.set(false);
    this.currentUserLogin.set('');
    this.router.navigate(['login']);
  }
}
