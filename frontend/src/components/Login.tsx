import HeadElement from "./StyleElements.tsx";
import {TextField} from "@mui/material";
import {useState} from "react";

type Props={
    onLogin: (data: string) => void;
}
export default function Login(props:Props){
    const [inputValue, setInputValue] = useState(
        {
            id: "",
        })
    function handleLogin(){
        props.onLogin(inputValue.id)
    }
    return(
        <>
            <HeadElement title={"Login"}/>
            <form onSubmit={handleLogin}>
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
                <button>Login</button>
            </form>
        </>
    )
}
