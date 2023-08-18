export type User = {
    id: string,
    firstName: string,
    lastName: string,
    role: Role,
}

export type Time = {
    day: string,
    startTime: string,
}

export type DtoUser = {
    memberCode: string,
    firstName: string,
    lastName: string,
}
export type RegisterUser = {
    memberCode: string,
    firstName: string,
    lastName: string,
    password: string,
    role: Role,
}
export enum Role {
    USER = "USER",
    ADMIN = "ADMIN",
}

export type UserObject = {
    memberCode: string,
    role: Role,
}
