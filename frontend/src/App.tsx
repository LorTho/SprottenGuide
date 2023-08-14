import './App.css'
import axios from "axios";
import {useEffect, useState} from "react";
import {DtoUser, Time, User} from "./model/User.tsx";
import ActualWeek from "./components/userSites/ActualWeek.tsx";
import Register from "./components/Register.tsx";
import {Route, Routes, useNavigate} from "react-router-dom";
import LandingPage from "./components/LandingPage.tsx";
import Login from "./components/Login.tsx";
import NextWeek from "./components/userSites/NextWeek.tsx";
import UserPage from "./components/userSites/UserPage.tsx";
import SchedulePage from "./components/scheduleSites/SchedulePage.tsx";
import CurrentWeek from "./components/scheduleSites/CurrentWeek.tsx";
import CreateSchedule from "./components/scheduleSites/CreateSchedule.tsx";
import {WorkSchedule} from "./model/WorkSchedule.tsx";
import {Day} from "./model/Day.tsx";
import DayView from "./components/daySite/DayView.tsx";

export default function App() {
    const [employee, setEmployee] = useState<User>()
    const [employeeShifts, setEmployeeShifts] = useState<Time[]>()
    const [employeeWish, setEmployeeWish]= useState<Time[]>()
    const [employeeCode, setEmployeeCode] = useState<string>("0000")

    const [userList, setUserList] = useState<DtoUser[]>([])
    const [currentWeek, setCurrentWeek] = useState<WorkSchedule>()
    const [nextWeek, setNextWeek] = useState<WorkSchedule>()
    const [daily, setDaily] = useState<Day>()

    const current = getCurrentWeekNumber()
    const next= current + 1

    useEffect(getCurrentWeekSchedule, [])
    useEffect(getNextWeekSchedule, [])
    useEffect(getEmployee, [employeeCode])
    useEffect(getEmployeeShifts, [employeeCode])
    useEffect(getEmployeeWish, [employeeCode])
    useEffect(getUserList, [])
    useEffect(getDaily,[])

    const navigate = useNavigate()

    function getCurrentWeekNumber() {
        const now = new Date();
        const startOfYear = new Date(now.getFullYear(), 0, 1);
        const startOfWeek = new Date(
            startOfYear.setDate(startOfYear.getDate() - startOfYear.getDay())
        );

        const diffInTime = now.getTime() - startOfWeek.getTime();
        const diffInWeeks = Math.floor(diffInTime / (1000 * 3600 * 24 * 7));

        return diffInWeeks + 1; // Add 1 to account for the first week
    }
    function getCurrentWeekSchedule() {
        axios.get("/api/schedule/" + current)
            .then(response => {
                setCurrentWeek(response.data)
            })
    }

    function getNextWeekSchedule() {
        axios.get("/api/schedule/" + next)
            .then(response => {
                setNextWeek(response.data)
            })
    }

    function handleRegister(newUser: DtoUser) {
        axios.post("/api/employee", newUser)
            .then(response => {
                setEmployee(response.data)
                setEmployeeCode(newUser.id)
            })
        navigate("/")
    }

    function handleLogin(code: string) {
        setEmployeeCode(() => code)
        navigate("/")
    }

    function handleLogout() {
        setEmployeeCode(() => "0")
        navigate("/")
    }

    function handleWishTime(wishTime: Time[]) {
        axios.put("/api/schedule/" + employeeCode+ "/" + next, wishTime)
            .then(response => {
                setEmployeeWish(response.data)
            })
        navigate("/")
    }

    function handleSaveCreateSchedule(workSchedule: WorkSchedule) {
        axios.put("/api/schedule", workSchedule)
            .then(response => {
                setNextWeek(response.data)
            })
        navigate("/")
    }
    function handleUpdateDay(day: Day){
        axios.put("/api/month/save", day)
            .then(response => {
                setDaily(response.data)
            })
    }
    function getEmployee() {
        axios.get("/api/employee/" + employeeCode)
            .then(response => {
                setEmployee(response.data)
            })
    }

    function getEmployeeShifts() {
        axios.get("/api/schedule/" + employeeCode + "/"+current)
            .then(response => {
                setEmployeeShifts(response.data)
            })
    }
    function getEmployeeWish() {
        axios.get("/api/schedule/" + employeeCode + "/"+next+"/wish")
            .then(response => {
                setEmployeeWish(response.data)
            })
    }

    function getUserList() {
        axios.get("/api/employee")
            .then(response => {
                setUserList(response.data)
            })
    }
    function getDaily(){
        axios.get("/api/month/today")
            .then(response=>{
                setDaily(response.data)
            })
    }

    if (employee === undefined)
        return <h1>Mitarbeiter wird geladen!</h1>
    if (employeeShifts === undefined)
        return <h1>Arbeitszeiten werden geladen!</h1>
    if (employeeWish === undefined)
        return <h1>Wunschliste wird geladen!</h1>
    if (currentWeek === undefined)
        return <h1>Arbeitsplan wird geladen!</h1>
    if (nextWeek === undefined)
        return <h1>Wunschplan wird geladen!</h1>
    if(daily === undefined)
        return <h1>Tagesansicht wird geladen</h1>

    return (
        <>
            <Routes>
                <Route path={"/"} element={<LandingPage/>}/>
                <Route path={"/login"} element={<Login onLogin={handleLogin}/>}/>
                <Route path={"/user/userSpace"} element={<UserPage user={employee} onLogout={handleLogout}/>}/>
                <Route path={"/user/actualWeek"} element={<ActualWeek shifts={employeeShifts}/>}/>
                <Route path={"/user/nextWeek"} element={<NextWeek user={employee} wishes={employeeWish} onChangeTimes={handleWishTime}/>}/>
                <Route path={"/register"} element={<Register onRegister={handleRegister}/>}/>
                <Route path={"/schedule/scheduleSite"} element={<SchedulePage/>}/>
                <Route path={"/schedule/actualWeek"}
                       element={<CurrentWeek schedule={currentWeek} userList={userList}/>}/>
                <Route path={"/schedule/nextWeek"} element={<CreateSchedule nextWeek={nextWeek} userList={userList}
                                                                            onSubmit={handleSaveCreateSchedule}/>}/>
                <Route path={"/day"} element={<DayView daily={daily} onUpdate={handleUpdateDay}/>}/>
            </Routes>
        </>
    )
}
