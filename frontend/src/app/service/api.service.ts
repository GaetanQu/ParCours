import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environment/environment.dev';
import { LoggerService } from './logger.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(
    private http: HttpClient,
    private loggerService: LoggerService
  ) { }

  // Get request
  public get(url: string): Observable<any> {
    this.loggerService.info(("GET request to: " + url).toString(), []);
    return this.http.get(environment.apiUrl + "/" + url, { withCredentials: true });
  }

  // Post request
  public post(url: string, data: any): Observable<any> {
    this.loggerService.info(("POST request to: " + url).toString(), []);
    return this.http.post(environment.apiUrl + "/" + url, data, { withCredentials: true });
  }
}
