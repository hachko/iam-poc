import { AfterViewChecked, Component, Injector, Type, ViewChild, ViewContainerRef } from '@angular/core';
import { CommonModule, NgIf } from '@angular/common';

@Component({
  selector: 'app-modal-host',
  standalone: true,
  imports: [NgIf, CommonModule],
  templateUrl: './modal-host.component.html',
  styleUrl: './modal-host.component.css'
})
export class ModalHostComponent implements AfterViewChecked{
  isOpen = false;
  @ViewChild('modalContent', {read: ViewContainerRef}) vcr!: ViewContainerRef;
  component: Type<any> | null = null;
  injector: Injector | null = null;

  ngAfterViewChecked() {
    // console.log('vcr length : ', this.vcr.length);
    console.log('isOpen : ', this.isOpen);
    console.log('component : ', this.component);
    if (this.isOpen && this.component && this.vcr.length === 0) {
      const compRef = this.vcr.createComponent(this.component, {injector: this.injector!});
      if( (compRef.instance as any).saved ) {
        (compRef.instance as any).saved.subscribe(() => this.close());
      }
    }
  }

  open(component: Type<any>, data?: any, mode: 'view' | 'edit' = 'view') {
    this.isOpen = true;
    this.component = component;
    this.injector = Injector.create({
      providers: [
        { provide: 'data', useValue: data },
        { provide: 'mode', useValue: mode }
      ]
    });    
  }

  close() {
    this.isOpen = false;
    this.component = null;
    this.injector = null;
    this.vcr.clear();
  }
}
