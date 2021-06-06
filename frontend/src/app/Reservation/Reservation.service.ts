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
import {Registered} from "../models/Registered";

@Injectable({
  providedIn: 'root'
})
export class ReservationService{
  private apiBaseUrl = environment.apiBaseUrl;
  constructor(private http: HttpClient) {}
  public getReservationsById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiBaseUrl}/vehicles/reservation/registered?id=${id}`);
  }
  public getRegistered(description: string | null): Observable<Registered> {
    return this.http.get<Registered>(`${this.apiBaseUrl}/vehicles/reservation/username?username=${description}`);
  }
  public getReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiBaseUrl}/vehicles/reservation/all`);
  }
  public deleteReservation(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiBaseUrl}/vehicles/reservation?id=${id}`);
  }
}
