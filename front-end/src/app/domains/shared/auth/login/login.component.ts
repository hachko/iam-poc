import { Component } from '@angular/core';
import { FormsModule, NgModel } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { NgIf, CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin() {
    console.log('onLogin with usr/pwd : ', this.username, this.password);
    this.authService.login(this.username, this.password).subscribe({      
      next: () => this.router.navigate(['']),
      error: (err) => this.errorMessage = 'Invalid credentials'      
    })
  }
}
