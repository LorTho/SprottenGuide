import HeadElement from "./StyleElements.tsx";
import {FormControl, IconButton, InputAdornment, InputLabel, OutlinedInput} from "@mui/material";
import {FormEvent, useState} from "react";
import {Visibility, VisibilityOff} from "@mui/icons-material";

type Props={
    onLogin: (id: string, password: string) => void;
}
export default function Login(props:Props){
    const [showPassword, setShowPassword] = useState(false);
    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const handleClickShowPassword = () => setShowPassword((show) => !show);
    const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
    };

    function onSubmit(event: FormEvent) {
        event.preventDefault()
        props.onLogin(username, password)
    }
    return(
        <>
            <HeadElement title={"Login"}/>
            <form className={"form-container"} onSubmit={onSubmit}>
                <p></p>
                <FormControl variant="outlined">
                    <InputLabel size={"small"} htmlFor="outlined-adornment-password">Member-code</InputLabel>
                    <OutlinedInput onChange={event => setUsername(event.target.value)} value={username}
                                   fullWidth
                                   size={"small"}
                                   id="outlined-adornment-membercode"
                                   type={'text'}
                                   label="Membercode"
                    />
                </FormControl>
                <p></p>
                <FormControl variant="outlined">
                    <InputLabel size={"small"} htmlFor="outlined-adornment-password">Password</InputLabel>
                    <OutlinedInput onChange={event => setPassword(event.target.value)} value={password}
                                   fullWidth
                                   size={"small"}
                                   id="outlined-adornment-password"
                                   type={showPassword ? 'text' : 'password'}
                                   endAdornment={
                                       <InputAdornment position="end">
                                           <IconButton
                                               aria-label="toggle password visibility"
                                               onClick={handleClickShowPassword}
                                               onMouseDown={handleMouseDownPassword}
                                               edge="end"
                                           >
                                               {showPassword ? <VisibilityOff/> : <Visibility/>}
                                           </IconButton>
                                       </InputAdornment>
                                   }
                                   label="Password"
                    />
                </FormControl>
                <button>Login</button>
            </form>
        </>
    )
}
