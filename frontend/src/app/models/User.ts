import { Role } from './Role';

export interface User{
    id: number,
    firstName: string,
    lastName: string,
    username: string,
    email: string,
    password: string,
    roles: Set<Role>
}