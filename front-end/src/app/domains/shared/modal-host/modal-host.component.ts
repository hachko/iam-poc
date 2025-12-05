import { Component, Type } from '@angular/core';
import { CommonModule, NgIf } from '@angular/common';
import { UserEditComponent } from "../../users/components/user-edit/user-edit.component";

@Component({
  selector: 'app-modal-host',
  standalone: true,
  imports: [NgIf, UserEditComponent],
  templateUrl: './modal-host.component.html',
  styleUrl: './modal-host.component.css'
})
export class ModalHostComponent {
  isOpen = false;
  component: any;
  data: any;
  mode: 'view' | 'edit'  = 'view';
  open(component: Type<any>, data?: any, mode: 'view' | 'edit' = 'view') {
    this.isOpen = true;
    this.component = component;
    this.data = data;
    this.mode = mode;
  }

  close() {
    this.isOpen = false;
  }
}
