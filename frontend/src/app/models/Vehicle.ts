import {Category} from  './Category';
import {User} from "./User";
import {Question} from "./Question";

export interface Vehicle {
  id: number;
  model: string,
  brojSjedista: number,
  potrosnja: number,
  url: string,
  category: Category;

}
