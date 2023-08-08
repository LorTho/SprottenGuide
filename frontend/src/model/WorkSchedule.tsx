export type WorkSchedule = {
    name: string,
    drivers: ShiftSchedule[],
    kitchen: ShiftSchedule[],
    wishes: WishSchedule[],
}
export type ShiftSchedule = {
    day: string,
    shifts: WorkShift[],
}
export type WorkShift ={
    employeeId: string,
    startTime: string,
}
export type WishSchedule = {
    employeeId: string,
    shifts: Shifts[],
}
export type Shifts = {
    day: string,
    startTime: string,
}