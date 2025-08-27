import { Routes } from '@angular/router';
import { LoginPageComponent } from '@pages/login-page';
import { MainPageComponent } from '@pages/main-page';
import { UsersPageComponent } from '@pages/users-page';
import { AddUserPageComponent } from '@pages/add-user-page';
import { authGuard } from '@guards/auth-guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginPageComponent, canActivate: [authGuard] },
  { path: 'main', component: MainPageComponent, canActivate: [authGuard] },
  {
    path: 'users',
    canActivate: [authGuard],
    children: [
      { path: '', component: UsersPageComponent },
      { path: 'edit/:login', component: AddUserPageComponent },
      { path: 'add', component: AddUserPageComponent },
    ],
  },
];
