import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { User } from '../../model/user.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RoleAggregate } from '../../../roles/aggregate/role.aggregate';
import { Observable } from 'rxjs';
import { Role } from '../../../roles/model/role.model';
import { UserAggregate } from '../../aggregate/user.aggregate';

@Component({
  selector: 'app-user-edit',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  templateUrl: './user-edit.component.html',
  styleUrl: './user-edit.component.css'
})
export class UserEditComponent implements OnInit {
  mode: 'view' | 'edit' = 'view';
  user?: User;
  userForm!: FormGroup;
  @Output() saved = new EventEmitter<User>();
  
  userRoles: Role[] = [];
  selectedAvailableRole: Role | null = null;
  availableRoles$!: Observable<Role[]>;

  constructor(
    private formBuilder: FormBuilder,
    private roleAggregate: RoleAggregate,
    private userAggregate: UserAggregate,
    @Inject('data') user: User,
    @Inject('mode') mode: 'view' | 'edit'
  ) {
    this.user = user;
    this.mode = mode;
  }

  ngOnInit(): void {
    this.userForm = this.formBuilder.group({
      username: [{value: this.user?.username, disabled: this.mode === 'view'}, Validators.required],
      email: [{value: this.user?.email, disabled: this.mode === 'view'}, [Validators.required, Validators.email]],
      password: [{value: this.user?.password, disabled: this.mode === 'view'}, Validators.required]
    });    
    
    this.roleAggregate.loadRoles();
    
    // Initialize user roles
    if (this.user?.roles) {
      this.userRoles = [...this.user.roles];
    }
    this.availableRoles$ = this.roleAggregate.getAvailableRoles(this.userRoles);
  }

  addRole(): void {
    if (this.selectedAvailableRole && !this.userRoles.find(r => r.id === this.selectedAvailableRole?.id)) {
      this.userRoles = [...this.userRoles, this.selectedAvailableRole];
      this.selectedAvailableRole = null;
      this.availableRoles$ = this.roleAggregate.getAvailableRoles(this.userRoles);
    }
  }

  removeRole(role: Role): void {
    this.userRoles = this.userRoles.filter(r => r.id !== role.id);
    this.availableRoles$ = this.roleAggregate.getAvailableRoles(this.userRoles);
  }

  save(): void {
    if(this.mode === 'edit' && this.userForm.valid) {
      const updatedUser = {
        ...this.userForm.value,
        roles: this.userRoles
      };
      if(this.user?.id) {
        updatedUser.id = this.user.id;
        this.userAggregate.updateUser(updatedUser);
      } else {
        this.userAggregate.addUser(updatedUser);
      }      
      this.saved.emit(updatedUser);
    }    
  }
}