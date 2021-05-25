
import { User } from './User';

export interface Notification{
    id: number,
    title: string,
    content: string,
    createdAt: Date,
    user: User
}