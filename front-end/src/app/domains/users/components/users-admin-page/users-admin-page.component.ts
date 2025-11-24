import { Component } from '@angular/core';
import { UsersListComponent } from '../users-list/users-list.component';
import { ModalHostComponent } from '../../../shared/modal-host/modal-host.component'; 

@Component({
  selector: 'app-users-admin-page',
  standalone: true,
  imports: [UsersListComponent, ModalHostComponent],
  templateUrl: './users-admin-page.component.html',
  styleUrl: './users-admin-page.component.css'
})
export class UsersAdminPageComponent {
  openAddUserModal() {
    console.log('App User Modal triggered');
  }  
}
