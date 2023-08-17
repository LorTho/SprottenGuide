import {TextField} from "@mui/material";
import {useState} from "react";
import {DtoUser} from "../model/User.tsx";
import HeadElement from "./StyleElements.tsx";
import {UserHook} from "../hooks/UserHook.tsx";
import {useNavigate} from "react-router-dom";

export default function Register() {
    const register = UserHook((UserState)=> UserState.register)
    const navigate = useNavigate()
    const [inputValue, setInputValue] = useState<DtoUser>(
        {
            memberCode: "",
            firstName: "",
            lastName: ""
        })

    function handleRegister() {
        register(inputValue, navigate)
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

                <section>
                    <button>Neuen Mitarbeiter anlegen!</button>
                </section>
            </form>
        </div>
    </>
}
