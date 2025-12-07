import { Component, Injector, Type } from '@angular/core';
import { CommonModule, NgIf } from '@angular/common';
import { UserEditComponent } from "../../users/components/user-edit/user-edit.component";

@Component({
  selector: 'app-modal-host',
  standalone: true,
  imports: [NgIf, UserEditComponent, CommonModule],
  templateUrl: './modal-host.component.html',
  styleUrl: './modal-host.component.css'
})
export class ModalHostComponent {
  isOpen = false;
  component: Type<any> | null = null;
  injector: Injector | null = null;

  open(component: Type<any>, data?: any, mode: 'view' | 'edit' = 'view') {
    this.isOpen = true;
    this.component = component;
    this.injector = Injector.create({
      providers: [
        { provide: 'data', useValue: data },
        { provide: 'mode', useValue: mode }
      ]
    })
  }

  close() {
    this.isOpen = false;
    this.component = null;
    this.injector = null;
  }
}
