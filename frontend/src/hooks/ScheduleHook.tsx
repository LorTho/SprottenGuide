import {create} from "zustand";
import {WorkSchedule} from "../model/WorkSchedule.tsx";
import axios from "axios";
import {NavigateFunction} from "react-router-dom";

type State = {
    currentWeek: WorkSchedule,
    nextWeek: WorkSchedule,
    jwtToken: string | null,

    getWeekSchedules: (weekNumber: number) => void,
    saveSchedule:(workSchedule: WorkSchedule, navigate: NavigateFunction) => void,
}

export const ScheduleHook = create<State>((set, get) => ({
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
    jwtToken: localStorage.getItem('token'),

    getWeekSchedules: (weekNumber: number) => {
        const {jwtToken} = get()
        axios.get("/api/schedule/" + weekNumber,{headers: {
                Authorization: "Bearer "+ jwtToken
            }})
            .then(response => response.data)
            .then((data) =>{
                set({currentWeek: data})
            })
        axios.get("/api/schedule/" + (weekNumber+1),{headers: {
                Authorization: "Bearer "+ jwtToken
            }})
            .then(response => response.data)
            .then((data) =>{
                set({nextWeek: data})
            })
    },
    saveSchedule: (workSchedule: WorkSchedule, navigate: NavigateFunction) =>{
        const {jwtToken} = get()
        axios.put("/api/schedule", workSchedule,{headers: {
                Authorization: "Bearer "+ jwtToken
            }})
            .then(response => response.data)
            .then((data) =>{
                set({nextWeek: data})
                navigate("/")
            })
    },
}))
