import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { inject } from '@angular/core';
import { UserService } from '@services/user';
import { Observable, of } from 'rxjs';

export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot,
): Observable<boolean | UrlTree> => {
  const userService = inject(UserService);
  const router = inject(Router);

  const isAuthenticated = userService.isAuthenticated;
  const isAdmin = userService.isAdmin;

  if (!isAuthenticated()) {
    return of(state.url === '/login' ? true : router.createUrlTree(['/login']));
  }

  if (state.url === '/login') {
    return of(router.createUrlTree(['/main']));
  }

  if (state.url.startsWith('/users') && !isAdmin()) {
    return of(router.createUrlTree(['/main']));
  }

  return of(true);
};
