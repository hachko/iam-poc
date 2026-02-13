import { Component, OnInit, ViewChild } from '@angular/core';
import { User } from '../../model/user.model';
import { CommonModule } from '@angular/common';
import { UserAggregate } from '../../aggregate/user.aggregate';
import { Observable } from 'rxjs/internal/Observable';
import { ModalHostComponent } from '../../../shared/modal-host/modal-host.component';
import { UserEditComponent } from '../user-edit/user-edit.component';

@Component({
  selector: 'app-users-list',
  standalone: true,
  imports: [CommonModule, ModalHostComponent],
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.css'
})
export class UsersListComponent  implements OnInit {  
  users$!: Observable<User[]>;

  @ViewChild('modalHost') modalHost!: ModalHostComponent

  constructor(private userAggregate: UserAggregate) {};

  ngOnInit(): void {
    this.users$ = this.userAggregate.users;
    this.userAggregate.loadUsers();
  }  

  viewUser(user: User) {
    this.openModal('view', user);
  }

  editUser(user: User) {
    this.openModal('edit', user);
  }

  deleteUser(user: User) {
    console.log('Deleting user:', user);
    this.userAggregate.deleteUser(user.id);
  }

  addUser() {
    console.log('Adding new user');
    this.openModal('edit', {} as User)
  }

  openModal(mode: 'edit' | 'view', user: User) {
    this.modalHost.open(UserEditComponent, user, mode);
  }
}
