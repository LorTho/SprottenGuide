export type User = {
    id: string,
    firstName: string,
    lastName: string,
    weeklyTime: [Map<number, time[]>],
}

export type time = {
    date: string,
    startTime: string,
}