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
export class VehicleService {
  private apiBaseUrl = environment.apiBaseUrl;
  private category: Category = {
    id: 0,
    description:'',
    vehicles: new Set<Vehicle>()
  };


  constructor(private http: HttpClient) {}
  public getVehicles(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(`${this.apiBaseUrl}/vehicles/vehicle/all`)
  }
  public addVehicle(vehicle: Vehicle, u:string): Observable<Vehicle> {
    vehicle.category.description=u;
    console.log(vehicle);
    return this.http.post<Vehicle>(`${this.apiBaseUrl}/vehicles/vehicle`, vehicle);
  }
  public getCategory(description: string): any {
    return this.http.get<Category>(`${this.apiBaseUrl}/vehicles/category/bydescription?description=${description}`)
  }
  public editVehicle(vehicle: Vehicle, u:string): Observable<any> {
    vehicle.category.description=u;
    return this.http.put<any>(`${this.apiBaseUrl}/vehicles/vehicle/update`, vehicle);
  }
  public deleteVehicle(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiBaseUrl}/vehicles/vehicle?id=${id}`);
  }

  public addReservation(reservation: Reservation): Observable<Reservation> {
    console.log(reservation);
    return this.http.post<Reservation>(`${this.apiBaseUrl}/vehicles/reservation`, reservation);
  }
}
