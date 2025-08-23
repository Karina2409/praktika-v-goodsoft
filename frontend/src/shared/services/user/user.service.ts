import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { User } from '@models/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  public httpClient = inject(HttpClient);

  public async findAllUsers(): Promise<User[]> {
    return await firstValueFrom(this.httpClient.get<User[]>('assets/inMemoryUsers.json'));
  }

  public async findUserByLogin(login: string) {
    const users = await this.findAllUsers();
    if (!users) return;
    return users.find((user) => (user.login === login ? user : null));
  }

  public async isAdmin(login: string): Promise<boolean> {
    const user = await this.findUserByLogin(login);
    if (!user) return false;
    return user.roles.some((role) => role === 'Администратор');
  }
}
