import { HttpInterceptorFn } from '@angular/common/http';

const API_BASE_URL = 'http://localhost:8080/api';

export const apiUrlInterceptor: HttpInterceptorFn = (req, next) => {
  const apiReq = req.url.startsWith('http')
    ? req
    : req.clone({ url: `${API_BASE_URL}/${req.url}` });

  return next(apiReq);
};
