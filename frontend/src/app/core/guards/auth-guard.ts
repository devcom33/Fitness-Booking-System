import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../services/AuthService';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);
  const expectedRole = route.data['expectedRole'];

  if (authService.isTokenExpired()) {
    return router.parseUrl('/login');
  }
  const hasRole = !expectedRole || authService.getUserRoles().includes(expectedRole);

  if (!hasRole) return router.parseUrl('/unauthorized');

  return true;
};
