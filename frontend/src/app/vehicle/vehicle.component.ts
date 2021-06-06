import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import {Vehicle} from "../models/Vehicle";
import {VehicleService} from "./vehicle.service";
import {NgForm} from "@angular/forms";
import {Question} from "../models/Question";
import {Category} from "../models/Category";
import {HomeService} from "../home/home.service";
import {Reservation} from "../models/Reservation";
import {CarShareService} from "../CarShare/CarShare.service";
import {CarShare} from "../models/CarShare";
import {User} from "../models/User";
import {Role} from "../models/Role";
import {Registered} from "../models/Registered";
import {ReservationService} from "../Reservation/Reservation.service";

@Component({ templateUrl: 'vehicle.component.html' })
export class VehicleComponent implements OnInit {
  public message: string = '';
  get updateVehicle(): Vehicle {
    return this._updateVehicle;
  }

  set updateVehicle(value: Vehicle) {
    this._updateVehicle = value;
  }
  public vehicles: Vehicle[] = [];
  public category: Category = {
    id: 0,
    description: "string",
    vehicles: new Set<Vehicle>()
  }
  private _updateVehicle: Vehicle = {
    id: 0,
    model: "",
    brojSjedista: 3,
    potrosnja: 3,
    url: "",
    category: this.category
  }
  public deleteVehicle: Vehicle = {
    id: 0,
    model: "",
    brojSjedista: 3,
    potrosnja: 3,
    url: "",
    category: this.category
  }
  public reservedVehicle: Vehicle = {
    id: 0,
    model: "",
    brojSjedista: 3,
    potrosnja: 3,
    url: "",
    category: this.category
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
  public reservation: Reservation = {
    id: 0,
    brojRezervacije: 0,
    reservationStart: new Date(),
    reservationEnd: new Date(),
    carShare: true,
    registered: this.user,
    vehicle: this.vehicle
  }
  public carShareShare: CarShare = {
    numberOfFreeSpaces:0,
    id : 0,
    reservation: this.reservation

  };
  filter = { Luksuzna: false, Sedan: false, Hatchback: false, StationWagon: false, All: true};
  filteredCars: Vehicle[] = [];
  public carShare1: string = "false";

  constructor(private vehicleService: VehicleService, public homeService: HomeService, public carShareService: CarShareService, public reservationService: ReservationService) {}
  ngOnInit() {
    this.getVehicles();
  }

  filterChange() {
    this.filteredCars = this.vehicles.filter(x =>
      (x.category.description === 'Sedan' && this.filter.Sedan)
      || (x.category.description === 'Luksuzna' && this.filter.Luksuzna)
      || (x.category.description === 'Hatchback' && this.filter.Hatchback)
      || (x.category.description === 'StationWagon' && this.filter.StationWagon)
      || (this.filter.All)
    );
  }
  public registered: Registered = {
    id:0,
    firstName:"",
    lastName:"",
    username:""
  }
  public getVehicles(): void {
    this.vehicleService.getVehicles().subscribe(
      (response: Vehicle[]) => {
        this.filteredCars = response;
        this.vehicles = response;
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        this.message = error.error.message;
      }
    );
  }

  public onAddVehicle(addVehicle: NgForm): void {
    console.warn(addVehicle);
    addVehicle.value.category={};
    addVehicle.value.category.description="";
    this.vehicleService.addVehicle(addVehicle.value,addVehicle.controls['category'].value).subscribe(
      (response: Vehicle) => {
        console.warn(addVehicle);
        this.getVehicles();
        addVehicle.reset();
      },
      (error: HttpErrorResponse) => {
        this.message = error.error.message;
        addVehicle.reset();
      }
    );
  }
  public onEditVehicle(vehicle: NgForm): void {
    vehicle.value.category = {};
    vehicle.value.category.description = "";
    this.vehicleService.editVehicle(vehicle.value, vehicle.controls['category'].value).subscribe(
      (response: Vehicle) => {
        this.getVehicles();
      },
      (error: HttpErrorResponse) => {
        this.message = error.error.message;
      }
    );
  }
  public onAddReservation(addReservationForm: NgForm): void {
    if(this.carShare1 == "true") {
      addReservationForm.value.carShare=true;
      this.carShareShare.reservation = addReservationForm.value;
      this.carShareService.addReservation(this.carShareShare);
    }
    else if(this.carShare1 == "false")
      addReservationForm.value.carShare=false;
    console.log(addReservationForm.value);
    addReservationForm.value.vehicle = this.reservedVehicle;

    addReservationForm.value.registered = {};
    addReservationForm.value.registered.username = '';
    let user = sessionStorage.getItem("username");
    addReservationForm.value.registered.username=user;
    console.warn(this.registered);
    this.vehicleService.addReservation(addReservationForm.value).subscribe(
      (response: Reservation) => {
        console.log(response);
        this.getVehicles();
        addReservationForm.reset();

      },
      (error: HttpErrorResponse) => {
        this.message = error.error.message;
        addReservationForm.reset();
      }
    );
  }
  public onDeleteVehicle(employeeId: number | undefined): void {
    if(employeeId !== undefined)
    this.vehicleService.deleteVehicle(employeeId).subscribe(
      (response: void) => {
        console.log(response);
        this.getVehicles();
      },
      (error: HttpErrorResponse) => {
        this.message = error.error.message;
        this.getVehicles();
      }
    );
  }
  public onOpenModal(vehicle: Vehicle, mode: string): void {
    const container = document.getElementById('main-container')!;
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'edit') {
      this._updateVehicle = vehicle;
      button.setAttribute('data-target', '#updateVehicleModal');
    }
    if (mode === 'delete') {
      this.deleteVehicle = vehicle;
      button.setAttribute('data-target', '#deleteVehicleModal');
    }
    if (mode === 'addReservation') {
      console.warn(this.reservationService.getRegistered("mmujic2"));
      this.reservedVehicle = vehicle;
      button.setAttribute('data-target', '#createReservation')
    }
    container.appendChild(button);
    button.click();
  }


}
