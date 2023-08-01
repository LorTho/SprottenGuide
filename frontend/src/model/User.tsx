export type User = {
    id: string,
    firstName: string,
    lastName: string,
    thisWeek: Time[],
    nextWeek: Time[],
}

export type Time = {
    day: string,
    startTime: string,
}

export type DtoUser = {
    id: string,
    firstName: string,
    lastName: string,
}
