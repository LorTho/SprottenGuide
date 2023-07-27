import './App.css'
import axios from "axios";
import {useEffect, useState} from "react";
import {DtoUser, guest, User} from "./model/User.tsx";
import Employee from "./components/Employee.tsx";
import Register from "./components/Register.tsx";
import {Route, Routes} from "react-router-dom";
import LandingPage from "./components/LandingPage.tsx";

export default function App() {
    const [employee, setEmployee] = useState<User>(guest)
    const [codeNumber, setCodeNumber] = useState("1111")

    useEffect(getEmployee, [codeNumber])
    function handleRegister(newUser: DtoUser) {
        axios.post("/api/employee", newUser)
            .then(response => {
                setEmployee(response.data);
                setCodeNumber(newUser.id)
            })
    }

    function getEmployee(){
        axios.get("/api/employee/"+codeNumber)
            .then(response => {
                setEmployee(response.data);
            })
    }

    return (
        <>
            <Routes>
                <Route path={"/"} element={<LandingPage/>}/>
                <Route path={"/week"} element={<Employee user={employee}/>}/>
                <Route path={"/register"} element={<Register onRegister={handleRegister}/>}/>
            </Routes>
        </>
    )
}
