import {create} from "zustand";
import {DtoUser} from "../model/User.tsx";
import axios from "axios";

type State = {
    currentWeekNumber: number,
    userList: DtoUser[],

    getCurrentWeekNumber: () => void,
    getUserList: () => void,
}

export const HelperHook = create<State>((set) => ({
    currentWeekNumber: 999,
    userList: [],

    getCurrentWeekNumber: () => {
        const now = new Date();
        const startOfYear = new Date(now.getFullYear(), 0, 1);
        const startOfWeek = new Date(
            startOfYear.setDate(startOfYear.getDate() - startOfYear.getDay())
        );

        const diffInTime = now.getTime() - startOfWeek.getTime();
        const diffInWeeks = Math.floor(diffInTime / (1000 * 3600 * 24 * 7));

        set({currentWeekNumber:diffInWeeks + 1}); // Add 1 to account for the first week
    },
    getUserList: () =>{
        axios.get("/api/user/list")
            .then(response => response.data)
            .then((data) =>{
                set({userList:data})
            })
    },

}))
