import { inject, Injectable, signal, WritableSignal } from '@angular/core';
import { UserService } from '@services/user';
import { User } from '@models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public userService = inject(UserService);
  public currentUser: WritableSignal<User | null> = signal(null);
  public isAuthorized: WritableSignal<boolean> = signal<boolean>(
    !!sessionStorage.getItem('authorized'),
  );

  public login(login: string, password: string) {
    const user = this.userService
      .users()
      .find((user) => user.login === login && user.password === password);
    this.currentUser.set(user ?? null);
    sessionStorage.setItem('authorized', String(!!user));
    this.isAuthorized.set(true);
    return !!user;
  }
}
