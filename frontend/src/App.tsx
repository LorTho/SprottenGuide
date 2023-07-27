import './App.css'
import axios from "axios";
import {useEffect, useState} from "react";
import {DtoUser, guest, Time, User} from "./model/User.tsx";
import ActualWeek from "./components/ActualWeek.tsx";
import Register from "./components/Register.tsx";
import {Route, Routes, useNavigate} from "react-router-dom";
import LandingPage from "./components/LandingPage.tsx";
import Login from "./components/Login.tsx";
import NextWeek from "./components/NextWeek.tsx";

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
    }
    function handleLogin(code:string){
        setCodeNumber(code)
        navigate("/")
    }
    function handleWishTime(wishTime: Time[]){
        axios.put("/api/employee/" + codeNumber, wishTime)
            .then(response=>{
                setEmployee(response.data)
                setCodeNumber(codeNumber)
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
                <Route path={"/"} element={<LandingPage user={employee}/>}/>
                <Route path={"/login"} element={<Login onLogin={handleLogin}/>}/>
                <Route path={"/actualWeek"} element={<ActualWeek user={employee}/>}/>
                <Route path={"/nextWeek"} element={<NextWeek user={employee} onChangeTimes={handleWishTime}/>}/>
                <Route path={"/register"} element={<Register onRegister={handleRegister}/>}/>
            </Routes>
        </>
    )
}
