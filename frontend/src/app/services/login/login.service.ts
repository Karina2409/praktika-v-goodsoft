import { inject, Injectable } from '@angular/core';
import { UserService } from '@services/user';
import { User } from '@models/user';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  public userService = inject(UserService);
  public currentUser: User | null = null;

  public login(login: string, password: string) {
    const user = this.userService
      .users()
      .find((user) => user.login === login && user.password === password);
    this.currentUser = user ?? null;
    return !!user;
  }
}
