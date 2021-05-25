import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { User } from '../models/User';
@Injectable({
    providedIn: 'root'
})

export class ClientService{
    private apiServerUrl=environment.apiBaseUrl;

    constructor(private http: HttpClient){}

    public getClients(): Observable<User[]> {
        return this.http.get<User[]>(`${this.apiServerUrl}/users/users/clients`);
      }
}