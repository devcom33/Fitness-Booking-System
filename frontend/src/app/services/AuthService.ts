import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  router = inject(Router);

  //private authStatus = new BehaviorSubject;

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');

    this.router.navigate(['/login']);

    console.log('User logged out successfully');
  }

  getUserRoles(): string[] {
    const token = this.getToken();

    if (!token) return [];

    try {
      const decoded: any = jwtDecode(token);

      return decoded.roles || [];
    } catch {
      return [];
    }
  }

  isTokenExpired(): boolean {
    const token = this.getToken();

    if (!token) return true;

    const decoded: any = jwtDecode(token);

    return decoded.exp * 1000 < Date.now();
  }
}
