import { Component, inject, signal } from '@angular/core';
import { TableModule } from 'primeng/table';
import { User } from '@models/user';
import { UserService } from '@services/user';
import { RoleComponent } from '@components/role';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-users-page',
  imports: [TableModule, RoleComponent, RouterLink],
  templateUrl: './users-page.component.html',
  styleUrl: './users-page.component.scss',
})
export class UsersPageComponent {
  private userService = inject(UserService);

  public users = signal<User[]>([]);

  constructor() {
    if (this.userService.users() !== null) {
      this.users.set(this.userService.users());
    } else {
      this.userService.findAllUsers().then((users) => {
        this.users.set(users);
      });
    }
  }

  public removeUser(login: string) {
    this.userService.removeUser(login).then(() => {
      this.users.set(this.userService.users());
    });
  }
}
