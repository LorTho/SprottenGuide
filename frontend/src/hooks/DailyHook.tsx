import {create} from "zustand";
import axios from "axios";
import {Day} from "../model/Day.tsx";

type State = {
    daily: Day,
    jwtToken: string | null,

    getDaily: ()=>void,
    setDaily: (newDaily: Day)=>void,
}

export const DailyHook = create<State>((set, get) => ({
    daily: {day: "heute", dailyPlanList: []},
    jwtToken: localStorage.getItem('token'),

    getDaily: () =>{
        const {jwtToken} = get()
        axios.get("/api/month/today",{headers: {
                Authorization: "Bearer "+ jwtToken
            }})
            .then(response => response.data)
            .then((data) =>{
                set({daily:data})
            })
    },
    setDaily: (newDaily: Day) =>{
        const {jwtToken} = get()
        axios.put("/api/month/save", newDaily, {headers: {
                Authorization: "Bearer "+ jwtToken
            }})
            .then(response => response.data)
            .then((data) => {
                set({daily:data})
            })
    },
}))
