import { User } from './User';

export interface Question{
    id: number,
    title: string,
    question: string,
    answered: boolean,
    user: User
}