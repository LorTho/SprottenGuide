export type User = {
    id: string,
    firstName: string,
    lastName: string,
    thisWeek: Map<number, Time[]>,
    nextWeek: Map<number, Time[]>,
}

export type Time = {
    date: string,
    startTime: string,
}

export type DtoUser = {
    firstName: string,
    lastName: string,
}
