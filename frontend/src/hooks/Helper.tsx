import {create} from "zustand";
import {DtoUser} from "../model/User.tsx";
import axios from "axios";
import jwt_decode from "jwt-decode";
import {MyToken} from "../model/MyToken.tsx";

type State = {
    currentWeekNumber: number,
    userList: DtoUser[],
    jwtToken: string | null,

    getCurrentWeekNumber: () => void,
    getUserList: () => void,

    getUserName:(code: string)=>string,
    getUserCode:(name: string)=>string,

    checkToken: ()=>void,
}

export const HelperHook = create<State>((set, get) => ({
    jwtToken: localStorage.getItem('token'),
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
        const {jwtToken} = get()
        axios.get("/api/user/list",{headers: {
                Authorization: "Bearer "+ jwtToken
            }})
            .then(response => response.data)
            .then((data) =>{
                set({userList:data})
            })
    },
    getUserName: (code: string) =>{
        const {userList} = get()
        const getUser = userList.find(user => user.memberCode === code)
        if(getUser === undefined)
            return "--"
        return getUser.firstName
    },
    getUserCode: (name: string) =>{
        const {userList} = get()
        const getUser = userList.find(user => user.firstName === name)
        if(getUser === undefined)
            return "--"
        return getUser.memberCode
    },
    checkToken: () =>{
        const token = localStorage.getItem('token')
        if(token !== null){
            const decoded = jwt_decode<MyToken>(token);
            const time = new Date()
            if(decoded.exp <= time.getMilliseconds()){
                localStorage.clear()
            }
        }
    },
}))
