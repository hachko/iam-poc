import { Component } from '@angular/core';
import { HeaderComponent } from "../../shared/header/header.component";
import { UsersListComponent } from "../../users/components/users-list/users-list.component";
import { AuthService } from '../../shared/auth/auth.service';
import { NgIf, CommonModule } from '@angular/common';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [HeaderComponent, UsersListComponent, CommonModule],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent {
  constructor(public authService: AuthService) {}
}
