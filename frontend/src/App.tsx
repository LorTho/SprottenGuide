import './App.css'
import axios from "axios";
import {useEffect, useState} from "react";
import {User} from "./model/User.tsx";
import Employee from "./components/Employee.tsx";

function App() {
    const[employee,setEmployee] = useState<User>()
    const employeeId:string = "123"

    useEffect(getEmployee, [])
    function getEmployee(){
        axios.get("/api/employee/" + employeeId)
            .then(response =>{
                setEmployee(() => response.data)
            })
            .catch(function(error){
                console.error(error)
            });
    }

    if(!employee)
        return <h1> ... loading</h1>
    return (
        <>
            <img src="Logo.png" alt="logo"/>
            <Employee week={31} user={employee}/>
        </>
    )
}

export default App
