import './App.css'
import axios from "axios";
import {useEffect, useState} from "react";
import {DtoUser, guest, Time, User} from "./model/User.tsx";
import ActualWeek from "./components/userSites/ActualWeek.tsx";
import Register from "./components/Register.tsx";
import {Route, Routes, useNavigate} from "react-router-dom";
import LandingPage from "./components/LandingPage.tsx";
import Login from "./components/Login.tsx";
import NextWeek from "./components/userSites/NextWeek.tsx";
import UserPage from "./components/userSites/UserPage.tsx";
import SchedulePage from "./components/scheduleSites/SchedulePage.tsx";

export default function App() {
    const [employee, setEmployee] = useState<User>(guest)
    const [codeNumber, setCodeNumber] = useState<string>()

    useEffect(getEmployee, [codeNumber])
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
        setCodeNumber(() => undefined)
        setEmployee(guest)
        navigate("/")
    }
    function handleWishTime(wishTime: Time[]){
        axios.put("/api/employee/" + codeNumber, wishTime)
            .then(response=>{
                setEmployee(response.data)
                setCodeNumber(employee.id)
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
                <Route path={"/schedule/actualWeek"} element={<ActualWeek user={employee}/>}/>
                <Route path={"/schedule/nextWeek"} element={<NextWeek user={employee} onChangeTimes={handleWishTime}/>}/>
            </Routes>
        </>
    )
}
