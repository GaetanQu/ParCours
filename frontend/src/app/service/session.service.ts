import { Injectable } from '@angular/core';

import { ApiService } from './api.service';
import { SessionDTO } from '../model/session.dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor(
    private apiService: ApiService,
  ) { }

  // Get all sessions
  public getAllSessions(): Observable<SessionDTO[]> {
    return this.apiService.get('sessions');
  }
}
