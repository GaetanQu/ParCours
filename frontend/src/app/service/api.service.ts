import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environment/environment.dev';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(
    private http: HttpClient
  ) { }

  // Get request
  public get(url: string): Observable<any> {
    return this.http.get(environment.apiUrl + "/" + url, { withCredentials: true });
  }

  // Post request
  public post(url: string, data: any): Observable<any> {
    return this.http.post(environment.apiUrl + "/" + url, data, { withCredentials: true });
  }
}
