import { Routes } from '@angular/router';
import { LoginPageComponent } from '@pages/login-page';
import { MainPageComponent } from '@pages/main-page';
import { canActivateAdmin, canActivateGuest, canActivateUser } from '@services/auth';
import { UsersPageComponent } from '@pages/users-page';
import { AddUserPageComponent } from '@pages/add-user-page';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginPageComponent, canActivate: [canActivateGuest] },
  { path: 'main', component: MainPageComponent, canActivate: [canActivateUser] },
  {
    path: 'users',
    canActivate: [canActivateAdmin],
    children: [
      { path: '', component: UsersPageComponent },
      { path: 'edit/:login', component: AddUserPageComponent },
      { path: 'add', component: AddUserPageComponent },
    ],
  },
];
