import { Component } from '@angular/core';
import { AuthService } from '../../../service/auth.service';
import { UserDTO } from '../../../model/user.dto';

@Component({
  selector: 'app-header',
  imports: [],
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
    if(this.authService.isAuthenticated()){
      this.authService.getUser().subscribe((user: UserDTO) => {
        this.user = user;
        console.log(this.user);
      });
    }
  }

  logout() {
    this.authService.logout();

    // Redirect to login
    window.location.href = '/login';
    this.user = null;
  }
}
