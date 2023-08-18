import {FormControlLabel, Radio, RadioGroup, TextField} from "@mui/material";
import React, {useState} from "react";
import {RegisterUser, role} from "../model/User.tsx";
import HeadElement from "./StyleElements.tsx";
import {UserHook} from "../hooks/UserHook.tsx";
import {useNavigate} from "react-router-dom";

export default function Register() {
    const register = UserHook((UserState) => UserState.register)
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

    function setRole(event: React.ChangeEvent<HTMLInputElement>) {
        if (event.target.value === "user")
            setInputValue({...inputValue, role: role.USER})
        if (event.target.value === "admin")
            setInputValue({...inputValue, role: role.ADMIN})
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
                    <RadioGroup
                        row
                        aria-labelledby="demo-row-radio-buttons-group-label"
                        name="row-radio-buttons-group"
                        onChange={setRole}
                    >
                        <FormControlLabel value="user" control={<Radio/>} label="User"/>
                        <FormControlLabel value="admin" control={<Radio/>} label="Manager"/>
                    </RadioGroup>
                </div>

                <section>
                    <button>Neuen Mitarbeiter anlegen!</button>
                </section>
            </form>
        </div>
    </>
}
