import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { User } from '@models/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  public httpClient = inject(HttpClient);

  private usersSignal = signal<User[]>([]);

  public async findAllUsers(): Promise<User[]> {
    if (this.usersSignal().length === 0) {
      const data = await firstValueFrom(this.httpClient.get<User[]>('assets/inMemoryUsers.json'));
      this.usersSignal.set(data);
    }
    return this.usersSignal();
  }

  public async findUserByLogin(login: string) {
    const users = await this.findAllUsers();
    return users.find((user) => user.login === login);
  }

  public async isAdmin(login: string): Promise<boolean> {
    const user = await this.findUserByLogin(login);
    if (!user) return false;
    return user.roles.includes('Администратор');
  }

  public async addUser(user: User): Promise<void> {
    const users = await this.findAllUsers();
    this.usersSignal.set([...users, user]);
  }

  public async updateUser(user: User): Promise<void> {
    const users = await this.findAllUsers();
    const index = users.findIndex((u) => u.login === user.login);
    if (index > -1) {
      const updated = [...users];
      updated[index] = user;
      this.usersSignal.set(updated);
    }
  }

  public async removeUser(login: string): Promise<void> {
    const users = await this.findAllUsers();
    this.usersSignal.set(users.filter((u) => u.login !== login));
  }

  public get users() {
    return this.usersSignal.asReadonly();
  }
}
