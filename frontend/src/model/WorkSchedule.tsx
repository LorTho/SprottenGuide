export type WorkSchedule = {
    name: string,
    drivers: ShiftSchedule[],
    kitchen: ShiftSchedule[],
}
export type ShiftSchedule = {
    day: string,
    shifts: WorkShift[],
}
export type WorkShift ={
    employeeId: string,
    startTime: number,
}

export const exampleWorkShift: WorkSchedule = {
    name: "next",
    drivers: [
        {day: "MONDAY",
            shifts: [
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1900},
            ]},
        {day: "TUESDAY",
            shifts: [
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1900},
            ]},
        {day: "WEDNESDAY",
            shifts: [
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1900},
            ]},
        {day: "THURSDAY",
            shifts: [
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1900},
            ]},
        {day: "FRIDAY",
            shifts: [
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1900},
            ]},
        {day: "SATURDAY",
            shifts: [
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1900},
            ]},
        {day: "SUNDAY",
            shifts: [
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1100},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1700},
                {employeeId : "null", startTime: 1900},
            ]}],
    kitchen: [
        {day: "MONDAY",
            shifts: [
                {employeeId: "null", startTime:1100},
                {employeeId: "null", startTime:1700},
            ]},
        {day: "TUESDAY",
            shifts: [
                {employeeId: "null", startTime:1100},
                {employeeId: "null", startTime:1700},
            ]},
        {day: "WEDNESDAY",
            shifts: [
                {employeeId: "null", startTime:1100},
                {employeeId: "null", startTime:1700},
            ]},
        {day: "THURSDAY",
            shifts: [
                {employeeId: "null", startTime:1100},
                {employeeId: "null", startTime:1700},
            ]},
        {day: "FRIDAY",
            shifts: [
                {employeeId: "null", startTime:1100},
                {employeeId: "null", startTime:1700},
            ]},
        {day: "SATURDAY",
            shifts: [
                {employeeId: "null", startTime:1200},
                {employeeId: "null", startTime:1700},
            ]},
        {day: "SUNDAY",
            shifts: [
                {employeeId: "null", startTime:1200},
                {employeeId: "null", startTime:1700},
            ]},
    ]
}
