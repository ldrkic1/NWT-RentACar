import {User} from "./User";
import {Vehicle} from "./Vehicle";

export interface Reservation {
  id: number,
  brojRezervacije: number,
  reservationStart: Date,
  reservationEnd: Date,
  carShare: boolean,
  registered: User,
  vehicle: Vehicle

}
