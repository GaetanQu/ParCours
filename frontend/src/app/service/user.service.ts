import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { UserDTO } from '../model/user.dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private authService: AuthService
  ) { }

  public getUserDTO(): UserDTO {
    const currentUser: UserDTO = {} as UserDTO;
    const token = this.authService.getToken();
    if (token) {
      const payload = token.split('.')[1];
      const decodedPayload = atob(payload);
      return JSON.parse(decodedPayload);
    }
    return currentUser;
  }
}
