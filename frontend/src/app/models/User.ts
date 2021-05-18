import { Role } from './Role';

export interface User{
    id: number,
    firstName: string,
    lastName: string,
    username: string,
    roles: Set<Role>
}