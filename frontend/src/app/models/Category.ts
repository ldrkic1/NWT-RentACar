import {Vehicle} from "./Vehicle";

export interface Category{
  id: number,
  description: string,
  vehicles: Set<Vehicle>
}
