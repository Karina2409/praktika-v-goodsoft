import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { UserWithRole } from 'src/shared/models/user-with-role';
import { SessionStorageService } from 'src/shared/services/session-storage';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private httpClient = inject(HttpClient);
  private sessionStorage = inject(SessionStorageService);

  private _isAuthenticated = signal(Boolean(this.sessionStorage.get('authorized')));
  private _isAdmin = signal(Boolean(this.sessionStorage.get('isAdmin')));
  private _login = signal(this.sessionStorage.get('login') ?? '');

  public isAuthenticated = this._isAuthenticated.asReadonly();
  public isAdmin = this._isAdmin.asReadonly();
  public userLogin = this._login.asReadonly();

  private usersSignal = signal<UserWithRole[]>([]);

  public async findAllUsers(): Promise<UserWithRole[]> {
    if (this.usersSignal().length === 0) {
      const data = await firstValueFrom(this.httpClient.get<UserWithRole[]>('users'));
      this.usersSignal.set(data);
    }
    return this.usersSignal();
  }

  public async findUserByLogin(login: string) {
    return await firstValueFrom(this.httpClient.get<UserWithRole>(`users/user?login=${login}`));
  }

  public async addUser(user: UserWithRole) {
    return await firstValueFrom(this.httpClient.post<UserWithRole>('users/add', user));
  }

  public async updateUser(user: UserWithRole) {
    return await firstValueFrom(this.httpClient.post<UserWithRole>('users/edit', user));
  }

  public async removeUser(login: string) {
    const response = await firstValueFrom(
      this.httpClient.delete<{ message: string }>(`users/delete?login=${login}`),
    );

    this.usersSignal.update((users) => users.filter((u) => u.user.login !== login));

    return response;
  }

  public login(login: string, isAdmin: boolean) {
    this._isAuthenticated.set(true);
    this._login.set(login);
    this._isAdmin.set(isAdmin);
  }

  public logout() {
    this._isAuthenticated.set(false);
    this._login.set('login');
    this._isAdmin.set(false);
  }

  public get users() {
    return this.usersSignal.asReadonly();
  }
}
