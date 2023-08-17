import {create} from "zustand";
import axios from "axios";
import {Day} from "../model/Day.tsx";

type State = {
    daily: Day,

    getDaily: ()=>void,
    setDaily: (newDaily: Day)=>void,
}

export const DailyHook = create<State>((set) => ({
    daily: {day: "heute", dailyPlanList: []},

    getDaily: () =>{
        axios.get("/api/month/today")
            .then(response => response.data)
            .then((data) =>{
                set({daily:data})
            })
    },
    setDaily: (newDaily: Day) =>{
        axios.put("/api/month/save", newDaily)
            .then(response => response.data)
            .then((data) => {
                set({daily:data})
            })
    },
}))
