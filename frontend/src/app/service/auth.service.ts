import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { LoginRequestDTO } from '../DTO/login-request.dto';
import { firstValueFrom, lastValueFrom, Observable, tap } from 'rxjs';
import { LoginResponseDTO } from '../DTO/login-response.dto';
import { UserDTO } from '../model/user.dto';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  private readonly TOKEN_KEY = 'token';

  constructor(
    private apiService: ApiService
  ) { }


  // Login
  public login(loginData: LoginRequestDTO): Observable<LoginResponseDTO> {
    return this.apiService.post('auth/login', loginData)
      .pipe(
        tap((response: LoginResponseDTO) => {
          // Save token
          if(response.token) {
            this.saveToken(response.token);
          }
          // Debug
          console.log(response);
        })
      );
  }

  // Save token to local storage
  public saveToken(token: string):void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  // Get token from local storage
  public getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  // Logout
  public logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  // Get user
  public getUser(): Observable<UserDTO> {
    return this.apiService.post('auth/user', { token: this.getToken() });
  }

  // Check if user is logged in
  public isAuthenticated(): boolean {
    return this.getToken() !== null;
  }
}
