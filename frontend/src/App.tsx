import './App.css'
import axios from "axios";
import {useEffect, useState} from "react";
import {DtoUser, User} from "./model/User.tsx";
import Employee from "./components/Employee.tsx";
import Register from "./components/Register.tsx";

export default function App() {
    const [employee, setEmployee] = useState<User>()
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

    if (!employee)
        return (
            <>
                <img src="Logo.png" alt="logo"/>
                <Register onRegister={handleRegister}/>
            </>
        )

    return (
        <>
            <img src="Logo.png" alt="logo"/>
            <Employee user={employee}/>
        </>
    )
}
