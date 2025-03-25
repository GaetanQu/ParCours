import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { LoginRequestDTO } from '../model/login-request.dto';
import { Observable, of, tap, catchError, map } from 'rxjs';
import { LoginResponseDTO } from '../model/login-response.dto';
import { UserDTO } from '../model/user.dto';
import { IsValidTokenResponseDTO } from '../model/is-valid-token-response.dto';

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
        })
      );
  }

  // Save token to local storage
  public saveToken(token: string): void {
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
  public isAuthenticated(): Observable<boolean> {
    if (this.getToken()) {
      return this.isValidToken().pipe(
        map((response: IsValidTokenResponseDTO) => response.isValid),
        catchError(() => of(false))
      );
    }
    return of(false);
  }

  // Check if token is valid
  public isValidToken(): Observable<IsValidTokenResponseDTO> {
    return this.apiService.post('auth/validate', { token: this.getToken() });
  }
}
