import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { LoginRequestDTO } from '../model/login-request.dto';
import { Observable, of, tap, catchError, map } from 'rxjs';
import { LoginResponseDTO } from '../model/login-response.dto';
import { UserDTO } from '../model/user.dto';
import { IsValidTokenResponseDTO } from '../model/is-valid-token-response.dto';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment/environment.dev';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  private readonly TOKEN_KEY = 'token';

  constructor(
    private http: HttpClient
  ) { }

  // Post request
  public post(endpoint: string, data: any): Observable<any> {
      return this.http.post(environment.backendUrl + endpoint, data, { withCredentials: true });
    }

  // Login
  public login(loginData: LoginRequestDTO, rememberMe: boolean): Observable<LoginResponseDTO> {
    return this.post('auth/login', loginData)
      .pipe(
        tap((response: LoginResponseDTO) => {
          // Save token
          if(response.token) {
            this.saveToken(response.token, rememberMe);
          }
        })
      );
  }

  // Save token to local storage
  public saveToken(token: string, rememberMe: boolean): void {
    if (rememberMe) {
      localStorage.setItem(this.TOKEN_KEY, token);
    }
    sessionStorage.setItem(this.TOKEN_KEY, token);
  }

  // Get token from local storage
  public getToken(): string | null {
    return sessionStorage.getItem(this.TOKEN_KEY);
  }

  // Logout
  public logout(): void {
    sessionStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.TOKEN_KEY);
  }

  // Get user
  public getUser(): Observable<UserDTO> {
    return this.post('auth/user', { token: this.getToken() });
  }

  // Check if user is logged in
  public isAuthenticated(): Observable<boolean> {
    const localToken:string | null = localStorage.getItem(this.TOKEN_KEY);
    // If token is in local storage, move it to session storage
    if(localToken !== null) {
      sessionStorage.setItem(this.TOKEN_KEY, localToken);
    }
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
    return this.post('auth/validate', { token: this.getToken() });
  }
}
