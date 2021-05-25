import { Question } from './Question';
import { User } from './User';

export interface QuestionNotification{
    id: number,
    title: string,
    content: string,
    createdAt: Date,
    user: User,
    question: Question
}