import { Component } from '@angular/core';
import { AuthService } from '../../shared/auth/auth.service';

@Component({
  selector: 'app-non-admin',
  standalone: true,
  imports: [],
  templateUrl: './non-admin.component.html',
  styleUrl: './non-admin.component.css'
})
export class NonAdminComponent {
  constructor(public authService: AuthService) {}
}
