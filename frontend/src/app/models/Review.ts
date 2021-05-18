import { User } from './User';

export interface Review{
    id: number,
    title: string,
    review: string,
    user: User
}