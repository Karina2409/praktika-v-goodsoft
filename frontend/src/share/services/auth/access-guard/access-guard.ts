import { Router, UrlTree } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '@services/auth';

export const canActivateGuest = (): boolean | UrlTree => {
  const isLoggedIn = inject(AuthService).isAuthorized();
  if (!isLoggedIn) {
    return true;
  }
  return inject(Router).createUrlTree(['/main']);
};

export const canActivateUser = (): boolean | UrlTree => {
  const isLoggedIn = inject(AuthService).isAuthorized();
  if (isLoggedIn) {
    return true;
  }
  return inject(Router).createUrlTree(['/login']);
};

export const canActivateAdmin = (): boolean | UrlTree => {
  const isAdmin = inject(AuthService).isAdmin();
  if (isAdmin) {
    return true;
  }
  return inject(Router).createUrlTree(['/login']);
};
