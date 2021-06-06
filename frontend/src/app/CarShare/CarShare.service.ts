import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {Answer} from "../models/Answer";
import {Vehicle} from "../models/Vehicle";
import {Question} from "../models/Question";
import {Category} from "../models/Category";
import {User} from "../models/User";
import {Role} from "../models/Role";
import {Reservation} from "../models/Reservation";
import {CarShare} from "../models/CarShare";

@Injectable({
  providedIn: 'root'
})
export class CarShareService {
  constructor(private http: HttpClient) {}
  private apiBaseUrl = environment.apiBaseUrl;
  public getCarShares(): Observable<CarShare[]> {
    return this.http.get<CarShare[]>(`${this.apiBaseUrl}/vehicles/carshare/all`)
  }
  public deleteCarShare(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiBaseUrl}/vehicles/carshare/${id}`);
  }
  public addReservation(reservation: CarShare): Observable<CarShare> {
    return this.http.post<CarShare>(`${this.apiBaseUrl}/vehicles/carshare`, reservation);
  }
}
