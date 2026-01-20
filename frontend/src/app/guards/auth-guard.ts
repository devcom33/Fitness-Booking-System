import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');

  if (token) {
    try {
      const DecodedToken: any = jwtDecode(token);
    } catch (e) {
      console.error('Invalid token format');
    }
  }

  return router.parseUrl('/login');
};
