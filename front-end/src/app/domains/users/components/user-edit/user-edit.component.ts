import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { User } from '../../model/user.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

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
  @Output() saved = new EventEmitter<void>();
  
  // TODO fetch them with aggregate / service
  availableRoles = ['USER','ADMIN'];

  constructor(
    private formBuilder: FormBuilder,
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
      roles: [{value: this.user?.roles, disabled: this.mode === 'view'}]
    });
  }

  save(): void {
    if(this.mode === 'edit' && this.userForm.valid) {
      const udpatedUser = this.userForm.value;
      // TODO user agregate and service to persist
      this.saved.emit();
    }    
  }

  get roleNames(): string {
    return this.user?.roles?.map(role => role.name).join(' | ') ?? '';
  }

}
