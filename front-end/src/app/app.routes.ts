import { Routes } from '@angular/router';
import { UserEditComponent } from './domains/users/components/user-edit/user-edit.component';
import { UsersAdminPageComponent } from './domains/users/components/users-admin-page/users-admin-page.component';
import { LandingComponent } from './domains/pages/landing/landing.component';
import { LoginComponent } from './domains/shared/auth/login/login.component';
import { NonAdminComponent } from './domains/pages/non-admin/non-admin.component';
import { AuthGuard } from './domains/shared/auth/auth.guard';

export const routes: Routes = [
    { path: '', component: LandingComponent },
    { path: 'login', component: LoginComponent },
    { path: 'non-admin', component: NonAdminComponent, canActivate: [AuthGuard] },
    { 
        path: 'users', component: UsersAdminPageComponent, 
        canActivate: [AuthGuard], data: {roles: ['ADMIN']}
    },
    { 
        path: 'users/:id', component: UserEditComponent, 
        canActivate: [AuthGuard], data: {roles: ['ADMIN']}
    }
];
