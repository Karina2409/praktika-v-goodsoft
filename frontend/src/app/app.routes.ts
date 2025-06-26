import { Routes } from '@angular/router';
import { LoginPageComponent } from '@pages/login-page';
import { MainPageComponent } from '@pages/main-page';
import { canActivateGuest, canActivateUser } from '@services/auth';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginPageComponent, canActivate: [canActivateGuest] },
  { path: 'main', component: MainPageComponent, canActivate: [canActivateUser] },
];
