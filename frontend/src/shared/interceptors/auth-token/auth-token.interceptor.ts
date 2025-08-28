import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { SessionStorageService } from '@services/session-storage';

export const authTokenInterceptor: HttpInterceptorFn = (req, next) => {
  const sessionStorage = inject(SessionStorageService);
  const token = sessionStorage.get('token');

  if (!token) {
    return next(req);
  }

  const authReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`,
    },
  });

  return next(authReq);
};
