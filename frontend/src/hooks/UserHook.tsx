import {RegisterUser, Role, Time, User, UserObject} from "../model/User.tsx";
import {create} from "zustand";
import axios from "axios";
import {NavigateFunction} from "react-router-dom";

type UserState = {
    memberCode: string,
    role: Role,
    jwtToken: string | null,
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

    register: (newUser: RegisterUser, navigate: NavigateFunction)=>void,
}

export const UserHook = create<UserState>((set, get) => ({
    memberCode: "",
    role: Role.USER,
    jwtToken: localStorage.getItem('token'),
    employee: {} as User,
    employeeShifts: [],
    employeeWish: [],

    getEmployee: (memberCode: string) => {
        const {jwtToken} = get()
        axios.get("/api/user/" + memberCode, {headers: {
                Authorization: "Bearer "+ jwtToken
            }})
            .then(response => response.data)
            .then((data) => {
                set({employee: data});
            })
            .catch(console.error)
    },
    getEmployeeShifts: (memberCode: string, weekNumber: number) => {
        const {jwtToken} = get()
        axios.get("/api/schedule/" + memberCode + "/" + weekNumber, {headers: {
                Authorization: "Bearer "+ jwtToken
            }})
            .then(response => response.data)
            .then((data) => {
                set({employeeShifts: data})
            })
            .catch(console.error)
    },
    getEmployeeWish: (memberCode: string, weekNumber: number) => {
        const {jwtToken} = get()
        axios.get("/api/schedule/" + memberCode + "/" + weekNumber + "/wish", {headers: {
                Authorization: "Bearer "+ jwtToken
            }})
            .then(response => response.data)
            .then((data) => {
                set({employeeWish: data})
            })
    },
    setEmployeeWish: (memberCode: string, weekNumber: number, wishTime: Time[], navigate: NavigateFunction) => {
        const {jwtToken} = get()
        axios.put("/api/schedule/" + memberCode + "/" + weekNumber, wishTime, {headers: {
                Authorization: "Bearer "+ jwtToken
            }})
            .then(response => response.data)
            .then((data) => {
                set({employeeWish: data})
                navigate("/")
            })
    },
    login: (memberCode: string, password: string, navigate: NavigateFunction) => {
        const loginData = {
            userCode: memberCode,
            password: password,
        }
        axios.post("/api/user/login", loginData)
            .then(response => response.data)
            .then((data) => {
                localStorage.setItem('token', data)
                set({jwtToken: data})
                navigate("/")
            })
    },
    isLogged: () => {
        const {jwtToken} = get()
        if(jwtToken != null){
        axios.get("/api/user", {headers: {
                Authorization: "Bearer "+ jwtToken
            }})
            .then(response => response.data)
            .then((data) => {
                const userObj: UserObject = data
                set({memberCode: userObj.memberCode})
                set({role: userObj.role})
            })
            .catch((error) =>{
                console.error(error)
            })
        }
    },
    logout: () => {
        localStorage.clear();
        set({memberCode: undefined})
        set({employee: {} as User})
        set({employeeWish: []})
        set({employeeShifts: []})
    },
    register: (newUser: RegisterUser, navigate: NavigateFunction)=>{
        const {jwtToken} = get()
        axios.post("/api/user/add", newUser, {headers: {
                Authorization: "Bearer "+ jwtToken
            }})
            .then(()=>{
                navigate("/")
            })
            .catch((error)=>{
                console.error(error)
            })
    },
}))
