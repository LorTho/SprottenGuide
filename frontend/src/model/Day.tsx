export type Day = {
    day: string,
    dailyPlanList: DailyPlan[],
}
export type DailyPlan = {
    employeeId: string,
    start: string,
    end: string,
    pause: Pause[],
    time: number,
}
export type Pause = {
    start: string,
    end: string | null,
}