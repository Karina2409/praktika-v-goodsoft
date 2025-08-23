import { Component, inject } from '@angular/core';
import { TableModule } from 'primeng/table';
import { User } from '@models/user';
import { UserService } from '@services/user';
import { RoleComponent } from '@components/role';

@Component({
  selector: 'app-users-page',
  imports: [TableModule, RoleComponent],
  templateUrl: './users-page.component.html',
  styleUrl: './users-page.component.scss',
})
export class UsersPageComponent {
  private userService = inject(UserService);

  public users: User[] = [];

  constructor() {
    this.userService.findAllUsers().then((users) => {
      this.users = users;
    });
  }
}
