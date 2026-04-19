import { Routes } from "@angular/router";
import { authGuard } from "../../core/guards/auth-guard";
import { Classes } from "./classes/classes";
import { Clients } from "./clients/clients";
import { Trainers } from "./trainers/trainers";


export const adminRoutes: Routes = [
  {
    path: 'trainers',
    component: Trainers,
    canActivate: [authGuard],
    data: { expectedRole: 'ROLE_ADMIN' },
  },
  {
    path: 'clients',
    component: Clients,
    canActivate: [authGuard],
    data: { expectedRole: 'ROLE_ADMIN' },
  },
  {
    path: 'classes',
    component: Classes,
    canActivate: [authGuard],
    data: { expectedRole: 'ROLE_ADMIN' },
  },
];