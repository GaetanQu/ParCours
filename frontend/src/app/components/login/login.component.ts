import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { LoginRequestDTO } from '../../model/login-request.dto';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';
import { HeaderComponent } from "../partials/header/header.component";

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    HeaderComponent
],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  // Services
  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  // Arguments
  loginRequest: LoginRequestDTO = {} as LoginRequestDTO;
  invalidCredentials: boolean = false;

  loginFormGroup = new FormGroup({
    username: new FormControl('', { nonNullable: true }),
    password: new FormControl('', { nonNullable: true }),
    rememberMe: new FormControl(false)
  });

  onSubmit() {
    this.loginRequest.username = this.loginFormGroup.get('username')!.value;
    this.loginRequest.password = this.loginFormGroup.get('password')!.value;

    this.authService.login(this.loginRequest).subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (error) => {
        switch (error.status) {
          case 401:
            this.invalidCredentials = true;
            break;
          default:
            console.error(error);
            break;
        }
      }
    });
  }
}
