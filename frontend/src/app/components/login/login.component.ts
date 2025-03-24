import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { LoginRequestDTO } from '../../DTO/login-request.dto';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule
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
    password: new FormControl('', { nonNullable: true })
  });

  onSubmit() {
    this.loginRequest.username = this.loginFormGroup.get('username')!.value;
    this.loginRequest.password = this.loginFormGroup.get('password')!.value;

    this.authService.login(this.loginRequest).subscribe(
      () => {
        this.router.navigate(['/']);
      },
      (error) => {
        console.error(error.error.error);
        this.invalidCredentials = true;
      }
    );
  }
}
