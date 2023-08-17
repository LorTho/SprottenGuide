import {DtoUser, Time, User} from "../model/User.tsx";
import {create} from "zustand";
import axios from "axios";
import {NavigateFunction} from "react-router-dom";

type UserState = {
    memberCode: string,
    employee: User,
    employeeShifts: Time[],
    employeeWish: Time[],

    getEmployee: (memberCode: string) => void,
    getEmployeeShifts: (memberCode: string, weekNumber: number) => void,
    getEmployeeWish: (memberCode: string, weekNumber: number) => void,
    setEmployeeWish: (memberCode: string, weekNumber: number, wishTime: Time[], navigate: NavigateFunction) => void,

    login: (memberCode: string, password: string, navigate: NavigateFunction) => void,
    isLogged: () => void,
    logout: () => void,

    register: (newUser: DtoUser, navigate: NavigateFunction)=>void,
}

export const UserHook = create<UserState>((set) => ({
    memberCode: "",
    employee: {} as User,
    employeeShifts: [],
    employeeWish: [],

    getEmployee: (memberCode: string) => {
        axios.get("/api/user/" + memberCode)
            .then(response => response.data)
            .then((data) => {
                set({employee: data});
            })
            .catch(console.error)
    },
    getEmployeeShifts: (memberCode: string, weekNumber: number) => {
        axios.get("/api/schedule/" + memberCode + "/" + weekNumber)
            .then(response => response.data)
            .then((data) => {
                set({employeeShifts: data})
            })
            .catch(console.error)
    },
    getEmployeeWish: (memberCode: string, weekNumber: number) => {
        axios.get("/api/schedule/" + memberCode + "/" + weekNumber + "/wish")
            .then(response => response.data)
            .then((data) => {
                set({employeeWish: data})
            })
    },
    setEmployeeWish: (memberCode: string, weekNumber: number, wishTime: Time[], navigate: NavigateFunction) => {
        axios.put("/api/schedule/" + memberCode + "/" + weekNumber, wishTime)
            .then(response => response.data)
            .then((data) => {
                set({employeeWish: data})
                navigate("/")
            })
    },
    login: (memberCode: string, password: string, navigate: NavigateFunction) => {
        const LoginData = {
            username: memberCode,
            password: password,
        }
        axios.post("/api/user/login", undefined, {auth: LoginData})
            .then(response => response.data)
            .then((data) => {
                set({memberCode: data})
                navigate("/")
            })
    },
    isLogged: () => {
        axios.get("/api/user")
            .then(response => response.data)
            .then((data) => {
                set({memberCode: data})
            })
    },
    logout: () => {
        axios.post("/api/user/logout")
            .then(() => {
                set({memberCode: undefined})
                set({employee: undefined})
            })
    },
    register: (newUser: DtoUser, navigate: NavigateFunction)=>{
        axios.post("/api/user/add", newUser)
            .then(response => {
                console.log(response)
                navigate("/")
            })
            .catch((error)=>{
                console.error(error)
            })
    },
}))
