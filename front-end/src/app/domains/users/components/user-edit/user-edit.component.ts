import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { User } from '../../model/user.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RoleAggregate } from '../../../roles/aggregate/role.aggregate';
import { Observable } from 'rxjs';
import { Role } from '../../../roles/model/role.model';
import { UserAggregate } from '../../aggregate/user.aggregate';

@Component({
  selector: 'app-user-edit',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './user-edit.component.html',
  styleUrl: './user-edit.component.css'
})
export class UserEditComponent implements OnInit {
  mode: 'view' | 'edit' = 'view';
  user?: User;
  userForm!: FormGroup;
  @Output() saved = new EventEmitter<User>();
  
  // TODO fetch them with aggregate / service
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
      password: [{value: this.user?.password, disabled: this.mode === 'view'}, Validators.required],
      roles: [{value: this.user?.roles, disabled: this.mode === 'view'}]
    });
    this.availableRoles$ = this.roleAggregate.allRoles;
    this.roleAggregate.loadRoles();
  }

  save(): void {
    if(this.mode === 'edit' && this.userForm.valid) {
      const udpatedUser = this.userForm.value;
      console.log('user to update : ', udpatedUser);
      if(this.user?.id) {
        udpatedUser.id = this.user.id;
        this.userAggregate.updateUser(udpatedUser);
      } else {
        this.userAggregate.addUser(udpatedUser);
      }      
      this.saved.emit(udpatedUser);
    }    
  }

  get roleNames(): string {
    return this.user?.roles?.map(role => role.name).join(' | ') ?? '';
  }

}
