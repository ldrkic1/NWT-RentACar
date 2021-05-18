import { User } from './User';
import { Question } from './Question';

export interface Answer{
    id: number,
    answer: string,
    user: User,
    question: Question
}