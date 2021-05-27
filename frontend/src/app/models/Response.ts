import { User } from './User';
import { Question } from './Question';

import { Role } from './Role';

export interface Response{
    jwt: string,
    role: Set<Role>

}