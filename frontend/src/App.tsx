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

export default function App() {
    const [employee, setEmployee] = useState<User>()
    const [codeNumber, setCodeNumber] = useState<string>("0000")

    const[currentWeek, setCurrentWeek] = useState<WorkSchedule>()

    useEffect(getEmployee, [codeNumber])
    useEffect(getWorkSchedule, [])

    const navigate = useNavigate()

    function handleRegister(newUser: DtoUser) {
        axios.post("/api/employee", newUser)
            .then(response => {
                setEmployee(response.data)
                setCodeNumber(newUser.id)
            })
        navigate("/")
    }
    function handleLogin(code:string){
        setCodeNumber(() => code)
        navigate("/")
    }
    function handleLogout(){
        setCodeNumber(() => "0")
        navigate("/")
    }
    function handleWishTime(wishTime: Time[]){
        axios.put("/api/employee/" + codeNumber, wishTime)
            .then(response=>{
                setEmployee(response.data)
            })
        navigate("/")
    }

    function getEmployee() {
        if (codeNumber !== undefined) {
            axios.get("/api/employee/" + codeNumber)
                .then(response => {
                    setEmployee(response.data)
                })
        }
    }
    function getWorkSchedule(){
        axios.get("/api/schedule/" +"current")
            .then(response =>{
                setCurrentWeek(response.data)
            })
    }

    if(employee === undefined)
        return <h1>Mitarbeiter wird geladen!</h1>
    if(currentWeek === undefined)
        return <h1>Arbeitsplan wird geladen!</h1>
    return (
        <>
            <Routes>
                <Route path={"/"} element={<LandingPage/>}/>
                <Route path={"/login"} element={<Login onLogin={handleLogin}/>}/>
                <Route path={"/user/userSpace"} element={<UserPage user={employee} onLogout={handleLogout}/>}/>
                <Route path={"/user/actualWeek"} element={<ActualWeek user={employee}/>}/>
                <Route path={"/user/nextWeek"} element={<NextWeek user={employee} onChangeTimes={handleWishTime}/>}/>
                <Route path={"/register"} element={<Register onRegister={handleRegister}/>}/>
                <Route path={"/schedule/scheduleSite"} element={<SchedulePage/>}/>
                <Route path={"/schedule/actualWeek"} element={<CurrentWeek schedule={currentWeek}/>}/>
                <Route path={"/schedule/nextWeek"} element={<CreateSchedule/>}/>
            </Routes>
        </>
    )
}
