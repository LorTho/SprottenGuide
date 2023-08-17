import {create} from "zustand";
import {WorkSchedule} from "../model/WorkSchedule.tsx";
import axios from "axios";
import {NavigateFunction} from "react-router-dom";

type State = {
    currentWeek: WorkSchedule,
    nextWeek: WorkSchedule,

    getWeekSchedules: (weekNumber: number) => void,
    saveSchedule:(workSchedule: WorkSchedule, navigate: NavigateFunction) => void,
}

export const ScheduleHook = create<State>((set) => ({
    currentWeek: {
        name: "12345",
        drivers: [],
        kitchen: [],
        wishes: []
    },
    nextWeek: {
        name: "56789",
        drivers: [],
        kitchen: [],
        wishes: []
    },

    getWeekSchedules: (weekNumber: number) => {
        axios.get("/api/schedule/" + weekNumber)
            .then(response => response.data)
            .then((data) =>{
                set({currentWeek: data})
            })
        axios.get("/api/schedule/" + (weekNumber+1))
            .then(response => response.data)
            .then((data) =>{
                set({nextWeek: data})
            })
    },
    saveSchedule: (workSchedule: WorkSchedule, navigate: NavigateFunction) =>{
        axios.put("/api/schedule", workSchedule)
            .then(response => response.data)
            .then((data) =>{
                set({nextWeek: data})
                navigate("/")
            })
    },
}))
