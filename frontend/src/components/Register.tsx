import {TextField} from "@mui/material";
import {useState} from "react";
import {DtoUser} from "../model/User.tsx";
import HeadElement from "./StyleElements.tsx";

type Props = {
    onRegister: (data: DtoUser) => void;
}
export default function Register(props: Props) {
    const [inputValue, setInputValue] = useState<DtoUser>(
        {
            id: "",
            firstName: "",
            lastName: ""
        })

    function handleRegister() {
        props.onRegister(inputValue)
    }

    return <>
        <div className={"register"}>
            <HeadElement title={"Register"}/>
            <form onSubmit={handleRegister}>
                <div className={"text-field"}>
                    <TextField
                        onChange={e => setInputValue({...inputValue, id: e.target.value})}
                        value={inputValue?.id}
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
