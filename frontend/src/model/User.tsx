export type User = {
    id: string,
    firstName: string,
    lastName: string,
    role: role,
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
    role: role,
}
export enum role {
    USER,
    ADMIN
}
