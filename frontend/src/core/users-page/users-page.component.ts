import { Component, inject } from '@angular/core';
import { TableModule } from 'primeng/table';
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

  public users = this.userService.users;

  constructor() {
    if (this.userService.users().length === 0) {
      this.userService.findAllUsers();
    }
  }

  public removeUser(login: string) {
    this.userService.removeUser(login);
  }
}
