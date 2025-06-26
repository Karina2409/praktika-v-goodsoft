import { Routes } from '@angular/router';
import { LoginPageComponent } from '@pages/login-page';
import { MainPageComponent } from '@pages/main-page';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginPageComponent },
  { path: 'main', component: MainPageComponent },
];
