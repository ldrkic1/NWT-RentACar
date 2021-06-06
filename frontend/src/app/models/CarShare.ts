import {Reservation} from "./Reservation";

export interface CarShare {
  id: number,
  numberOfFreeSpaces: number,
  reservation: Reservation
}
