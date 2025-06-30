import { inject, Injectable, signal, WritableSignal } from '@angular/core';
import { UserService } from '@services/user';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public currentUserLogin: WritableSignal<string> = signal(
    sessionStorage.getItem('login') as string,
  );
  public isAuthorized: WritableSignal<boolean> = signal<boolean>(
    !!sessionStorage.getItem('authorized'),
  );

  private userService = inject(UserService);
  private router = inject(Router);

  public login(login: string, password: string) {
    const user = this.userService
      .users()
      .find((user) => user.login === login && user.password === password);
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
