import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import {Vehicle} from "../models/Vehicle";
import {NgForm} from "@angular/forms";
import {Question} from "../models/Question";
import {Category} from "../models/Category";
import {HomeService} from "../home/home.service";
import {Reservation} from "../models/Reservation";
import {VehicleService} from "../vehicle/vehicle.service";
import {CarShare} from "../models/CarShare";
import {CarShareService} from "./CarShare.service";
import {User} from "../models/User";
import {Role} from "../models/Role";

@Component({ templateUrl: 'CarShare.component.html' })
export class CarShareComponent implements OnInit {

  public carShareVehicles: CarShare[]=[];
  public user: User = {
    id: 0,
    firstName: "",
    lastName: "",
    username: "",
    email: "",
    password: "",
    roles: new Set<Role>()
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
  public reservation: Reservation = {
    id: 0,
    brojRezervacije: 0,
    reservationStart: new Date(),
    reservationEnd: new Date(),
    carShare: true,
    registered: this.user,
    vehicle: this.vehicle
  }
  public deleteCarShare: CarShare = {
    numberOfFreeSpaces:0,
    id : 0,
    reservation: this.reservation

  };
  ngOnInit(): void {
    this.getCarShares();
  }

  constructor(private carShareService: CarShareService, public homeService: HomeService) {
  }
  public getCarShares(): void {
    this.carShareService.getCarShares().subscribe(
      (response: CarShare[]) => {
        this.carShareVehicles = response;
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        console.warn(error);
      }
    );
  }
  public onDeleteVehicle(employeeId: number | undefined): void {
    if(employeeId !== undefined)
      this.carShareService.deleteCarShare(employeeId).subscribe(
        (response: void) => {
          console.log(response);
          this.getCarShares();
        },
        (error: HttpErrorResponse) => {
          this.getCarShares();
        }
      );
  }
  public onAddReservation(addReservationForm: CarShare): void {
    this.onDeleteVehicle(addReservationForm.id);
    addReservationForm.numberOfFreeSpaces -=1;
    this.carShareService.addReservation(addReservationForm).subscribe(
      (response: CarShare) => {
        console.log(response);
        this.getCarShares();
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }
  public onOpenModal(vehicle: CarShare, mode: string): void {
    const container = document.getElementById('main-container')!;
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'delete') {
      this.deleteCarShare = vehicle;
      button.setAttribute('data-target', '#deleteVehicleModal');
    }
    container.appendChild(button);
    button.click();
  }
}
