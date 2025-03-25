import { Component } from '@angular/core';
import { AuthService } from '../../../service/auth.service';
import { UserDTO } from '../../../model/user.dto';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [
    RouterModule
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  // Services
  constructor(
    private authService: AuthService
  ) {}

  // Arguments
  user: UserDTO | null = null;

  ngOnInit() {
    this.loadUser();
  }

  loadUser() {
    this.authService.isAuthenticated().subscribe(
      {
        next: (isAuthenticated) => {
          if (isAuthenticated) {
            this.authService.getUser().subscribe((user: UserDTO) => {
              this.user = user;
            });
          }
        }
      }
    );
  }

  logout() {
    this.authService.logout();

    // Redirect to login
    window.location.href = '/login';
    this.user = null;
  }
}
