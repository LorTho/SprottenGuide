import './App.css'
import axios from "axios";
import {useState} from "react";
import {DtoUser, User} from "./model/User.tsx";
import Employee from "./components/Employee.tsx";
import NewUser from "./components/NewUser.tsx";

function App() {
    const [employee, setEmployee] = useState<User>()

    function handleRegister(newUser: DtoUser) {
        axios.post("/api/employee", newUser)
            .then(response => {
                setEmployee(response.data);
            })
    }

    if (!employee)
        return (
            <>
                <img src="Logo.png" alt="logo"/>
                <NewUser onRegister={handleRegister}/>
            </>
        )

    return (
        <>
            <img src="Logo.png" alt="logo"/>
            <Employee user={employee}/>
        </>
    )
}

export default App
