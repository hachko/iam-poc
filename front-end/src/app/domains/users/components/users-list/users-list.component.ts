import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user.model';
import { CommonModule } from '@angular/common';
import { UserAggregate } from '../../aggregate/user.aggregate';
import { Observable } from 'rxjs/internal/Observable';

@Component({
  selector: 'app-users-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.css'
})
export class UsersListComponent  implements OnInit {  
  users$!: Observable<User[]>;

  constructor(private userAggregate: UserAggregate) {};

  ngOnInit(): void {
    this.users$ = this.userAggregate.users;
    this.userAggregate.loadUsers();
  }
  
  users: User[] = [
    { id: 1, username: 'Alice Dupont', email: 'alice@example.com', roles: [] },
    { id: 2, username: 'Bob Martin', email: 'bob@example.com', roles: [] },
    { id: 3, username: 'Charlie Durand', email: 'charlie@example.com', roles: [] }
  ];

  viewUser(user: User) {
    console.log('Viewing user:', user);
  }

  editUser(user: User) {
    console.log('Editing user:', user);
  }

  deleteUser(user: User) {
    console.log('Deleting user:', user);
  }

  addUser() {
    console.log('Adding new user');
  }
}
