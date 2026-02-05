import { Component } from '@angular/core';
import { HeaderComponent } from "../../shared/header/header.component";
import { AuthService } from '../../shared/auth/auth.service';

@Component({
  selector: 'app-non-admin',
  standalone: true,
  imports: [HeaderComponent],
  templateUrl: './non-admin.component.html',
  styleUrl: './non-admin.component.css'
})
export class NonAdminComponent {
  constructor(public authService: AuthService) {}
}
