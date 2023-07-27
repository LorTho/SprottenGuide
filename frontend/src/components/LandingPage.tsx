import {Button} from "@mui/material";
import {User} from "../model/User.tsx";
import {Link} from "react-router-dom";

type Props = {
    user: User,
}
export default function LandingPage(props: Props) {

    if (props.user.id === "0")
        return (
            <>
                <img src={"Logo.png"} alt={"logo"}/>
                <Link to={"/login"}>
                    <Button>Login</Button>
                </Link>
                <Link to={"/register"} relative={"path"}>
                    <Button>Register</Button>
                </Link>
            </>
        )
    return (
        <>
            <main>
                <img src={"Logo.png"} alt={"logo"}/>
                <h2>{props.user.firstName}</h2>
                <button>Logout</button>
                <Link to={"/actualWeek"}>
                    <button>Arbeitsschichten</button>
                </Link>
                <Link to={"/nextWeek"}>
                    <button>Dienstplanwunsch</button>
                </Link>
            </main>
        </>
    )
}