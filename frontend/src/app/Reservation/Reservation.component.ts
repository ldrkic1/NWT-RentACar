import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import {Vehicle} from "../models/Vehicle";

import {NgForm} from "@angular/forms";
import {Question} from "../models/Question";
import {Category} from "../models/Category";
import {HomeService} from "../home/home.service";
import {Reservation} from "../models/Reservation";
import {CarShareService} from "../CarShare/CarShare.service";
import {CarShare} from "../models/CarShare";
import {User} from "../models/User";
import {Role} from "../models/Role";
import {ReservationService} from "./Reservation.service";
import {Registered} from "../models/Registered";

@Component({ templateUrl: 'Reservation.component.html' })
export class ReservationComponent implements OnInit{
  ngOnInit() {
    if(this.homeService.isClientLoggedIn())
      this.getReservations();
    else(this.homeService.isAdminLoggedIn())
      this.getReservations2();
  }
  public category: Category = {
    id: 0,
    description: "string",
    vehicles: new Set<Vehicle>()
  }
  public vehicle: Vehicle = {
    id: 0,
    model: "",
    brojSjedista: 3,
    potrosnja: 3,
    url: "",
    category: this.category
  }
  public user: User = {
    id: 0,
    firstName: "",
    lastName: "",
    username: "",
    email: "",
    password: "",
    roles: new Set<Role>()
  }
  public deleteReservation: Reservation = {
    id: 0,
    brojRezervacije: 0,
    reservationStart: new Date(),
    reservationEnd: new Date(),
    carShare: true,
    registered: this.user,
    vehicle: this.vehicle
  }
  public reservations: Reservation[] = [];
  public registered: Registered = {
    id:0,
    firstName:"",
    lastName:"",
    username:""
  }
  constructor(private reservationService: ReservationService, public homeService: HomeService) {}
  public getReservations(): void {
    let user = sessionStorage.getItem("username");
      this.reservationService.getRegistered(user).subscribe(
        (response: Registered) => {
          this.registered = response;
          console.log(response);
          this.reservationService.getReservationsById(response.id).subscribe(
            (response: Reservation[]) => {
              this.reservations = response;
              console.log(response);
            },
            (error: HttpErrorResponse) => {
              console.warn(error);
            }
          );
        },
        (error: HttpErrorResponse) => {
          console.warn(error);
        }
      );
  }
  public getReservations2(): void {
    this.reservationService.getReservations().subscribe(
      (response: Reservation[]) =>{
        this.reservations = response;
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        console.warn(error);
      }
    );
  }
  public onDeleteReservation(employeeId: number | undefined): void {
    if(employeeId !== undefined)
      this.reservationService.deleteReservation(employeeId).subscribe(
        (response: void) => {
          console.log(response);
          if(this.homeService.isAdminLoggedIn()){
            this.getReservations2();
          }
          else if(this.homeService.isClientLoggedIn()) {
            this.getReservations();
          }
        },
        (error: HttpErrorResponse) => {
          if(this.homeService.isAdminLoggedIn()){
            this.getReservations2();
          }
          else if(this.homeService.isClientLoggedIn()) {
            this.getReservations();
          }
        }
      );
  }
  public onOpenModal(reservation: Reservation, mode: string): void {
    const container = document.getElementById('main-container')!;
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'delete') {
      this.deleteReservation = reservation;
      button.setAttribute('data-target', '#deleteVehicleModal');
    }
    else if (mode === 'delete1') {
      this.deleteReservation = reservation;
      button.setAttribute('data-target', '#deleteVehicleModal1');
    }
    container.appendChild(button);
    button.click();
  }
}
