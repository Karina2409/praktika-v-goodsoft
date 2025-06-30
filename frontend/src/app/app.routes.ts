import { Routes } from '@angular/router';
import { LoginPageComponent } from '@pages/login-page';
import { MainPageComponent } from '@pages/main-page';
import { canActivateAdmin, canActivateGuest, canActivateUser } from '@services/auth';
import { UsersPageComponent } from '@pages/users-page';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginPageComponent, canActivate: [canActivateGuest] },
  { path: 'main', component: MainPageComponent, canActivate: [canActivateUser] },
  { path: 'users', component: UsersPageComponent, canActivate: [canActivateAdmin] },
];
