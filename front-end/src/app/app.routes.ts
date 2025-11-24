import { Routes } from '@angular/router';
import { UserEditComponent } from './domains/users/components/user-edit/user-edit.component';
import { UsersAdminPageComponent } from './domains/users/components/users-admin-page/users-admin-page.component';

export const routes: Routes = [
    { path: '', component: UsersAdminPageComponent },
    { path: 'users', component: UserEditComponent },
    { path: 'users/:id', component: UserEditComponent }
];
