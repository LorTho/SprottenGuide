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

export const guest:User ={
    "id": "0",
    "firstName": "Guest",
    "lastName": "Guest",
    "thisWeek": [{
        "day": "MONDAY",
        "startTime": "11:00:00"
    },{
        "day": "WEDNESDAY",
        "startTime": "11:00:00"
    },{
        "day": "FRIDAY",
        "startTime": "17:00:00"
    },{
        "day": "SUNDAY",
        "startTime": "11:00:00"
    }],
    "nextWeek": [{
        "day": "MONDAY",
        "startTime": "11:00:00"
    },{
        "day": "WEDNESDAY",
        "startTime": "11:00:00"
    }]
}