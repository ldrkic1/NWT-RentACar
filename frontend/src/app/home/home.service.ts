import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Answer } from '../models/Answer';
import { Question } from '../models/Question';
import { User } from '../models/User';
import { Role } from '../models/Role';
import { Router } from '@angular/router';

@Injectable({providedIn: 'root'})
export class HomeService {
    private apiBaseUrl = environment.apiBaseUrl;
    constructor(private http: HttpClient) {}

    public authenticate(username: string, password:string): Observable<Response> {
        return this.http.post<Response>(`${this.apiBaseUrl}/authenticate`, 
        {
            username:username, 
            password:password
        });
    }

  isAdminLoggedIn() {
    let user = sessionStorage.getItem("username");
    let role = sessionStorage.getItem("role");
    console.log(!(user === null));
    if(!(user === null) && !(role === null)) {
        return role === 'ROLE_ADMIN';
    }
    return false;
}

isClientLoggedIn() {
  let user = sessionStorage.getItem("username");
  let role = sessionStorage.getItem("role");
  console.log(!(user === null));
  if(!(user === null) && !(role === null)) {
      return role === 'ROLE_CLIENT';
  }
  return false;
}

logOut() {
    sessionStorage.removeItem("username");
    sessionStorage.removeItem("jwt");
    sessionStorage.removeItem("role");
    
  }
}
