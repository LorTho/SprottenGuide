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
    startTime: string,
}
