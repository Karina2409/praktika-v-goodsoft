import { Component, input } from '@angular/core';

@Component({
  selector: 'app-role',
  imports: [],
  templateUrl: './role.component.html',
  styleUrl: './role.component.scss',
})
export class RoleComponent {
  public role = input.required<string>();
}
