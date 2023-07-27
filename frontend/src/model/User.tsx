export type User = {
    id: string,
    firstName: string,
    lastName: string,
    thisWeek: Time[],
    nextWeek: Time[],
}

export type Time = {
    date: string,
    startTime: string,
}

export type DtoUser = {
    id: string,
    firstName: string,
    lastName: string,
}

export const guest:User ={
    "id": "0",
    "firstName": "Guest",
    "lastName": "Guest",
    "thisWeek": [],
    "nextWeek": [],
}