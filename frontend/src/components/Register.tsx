import {MenuItem, Select, TextField} from "@mui/material";
import {useState} from "react";
import {RegisterUser, role} from "../model/User.tsx";
import HeadElement from "./StyleElements.tsx";
import {UserHook} from "../hooks/UserHook.tsx";
import {useNavigate} from "react-router-dom";

export default function Register() {
    const register = UserHook((UserState)=> UserState.register)
    const navigate = useNavigate()
    const [inputValue, setInputValue] = useState<RegisterUser>(
        {
            memberCode: "",
            firstName: "",
            lastName: "",
            password: "",
            role: role.USER
        })

    function handleRegister() {
        register(inputValue, navigate)
    }

    function getValue(value: unknown): role {
        if(value==="2")
            return role.ADMIN
        else
            return role.USER
    }

    return <>
        <div className={"register"}>
            <HeadElement title={"Register"}/>
            <form onSubmit={handleRegister}>
                <div className={"text-field"}>
                    <TextField
                        onChange={e => setInputValue({...inputValue, memberCode: e.target.value})}
                        value={inputValue?.memberCode}
                        id="outlined-basic"
                        color={"success"}
                        label="Mitarbeiter-ID"
                        variant="outlined"
                        required
                    />
                </div>
                <div className={"text-field"}>
                    <TextField
                        onChange={e => setInputValue({...inputValue, firstName: e.target.value})}
                        value={inputValue?.firstName}
                        id="outlined-basic"
                        color={"success"}
                        label="Vorname"
                        variant="outlined"
                        required
                    />
                </div>
                <div className={"text-field"}>
                    <TextField
                        onChange={e => setInputValue({...inputValue, lastName: e.target.value})}
                        value={inputValue?.lastName}
                        id="outlined-basic"
                        color={"success"}
                        label="Nachname"
                        variant="outlined"
                        required
                    />
                </div>
                <div className={"text-field"}>
                    <TextField
                        onChange={e => setInputValue({...inputValue, password: e.target.value})}
                        value={inputValue?.password}
                        id="outlined-basic"
                        color={"success"}
                        label="password"
                        variant="outlined"
                        required
                    />
                </div>
                <div className={"text-field"}>
                    <Select
                        labelId="demo-select-small-label"
                        id="demo-select-small"
                        value={inputValue.role}
                        label="Role"
                        onChange={e => setInputValue({...inputValue, role: getValue(e.target.value)})}
                    >
                        <MenuItem value={1}>User</MenuItem>
                        <MenuItem value={2}>Manager</MenuItem>
                    </Select>
                </div>

                <section>
                    <button>Neuen Mitarbeiter anlegen!</button>
                </section>
            </form>
        </div>
    </>
}
