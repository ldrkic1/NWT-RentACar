export const enum RoleName {
    ROLE_ADMIN,
    ROLE_CLIENT
}

export interface Role{
    id: number,
    roleName: RoleName
}