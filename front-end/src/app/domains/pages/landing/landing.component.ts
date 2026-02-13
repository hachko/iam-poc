import { Component } from '@angular/core';
import { UsersListComponent } from "../../users/components/users-list/users-list.component";
import { AuthService } from '../../shared/auth/auth.service';
import { NgIf, CommonModule } from '@angular/common';
import { NonAdminComponent } from "../non-admin/non-admin.component";

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [UsersListComponent, CommonModule, NonAdminComponent],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent {
  constructor(public authService: AuthService) {}
}
