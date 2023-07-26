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
    firstName: string,
    lastName: string,
}
