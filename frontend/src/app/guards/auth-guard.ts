import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');

  if (token) {
    try {
      const decodedToken: any = jwtDecode(token);
      console.log('Decoded token:', decodedToken);
      console.log('Expires at:', new Date(decodedToken.exp * 1000));
      console.log('Current time:', new Date());

      const isExpired = decodedToken.exp * 1000 < Date.now();
      console.log('Is expired?', isExpired);

      if (!isExpired) {
        console.log('Token valid - allowing access');
        return true;
      }
    } catch (e) {
      console.error('Invalid token format');
    }
  }

  return router.parseUrl('/login');
};
