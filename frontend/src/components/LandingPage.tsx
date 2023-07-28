import {User} from "../model/User.tsx";
import {Link} from "react-router-dom";

type Props = {
    user: User,
    onlogout : () => void
}
export default function LandingPage(props: Props) {

    if (props.user.id === "0")
        return (
            <>
                <img src={"Logo.png"} alt={"logo"} className={"logo-landing"}/>
                <Link to={"/login"}>
                    <button>Login</button>
                </Link>
                <Link to={"/register"} relative={"path"}>
                    <button>Register</button>
                </Link>
            </>
        )
    return (
        <>
            <main>
                <img src={"Logo.png"} alt={"logo"} className={"logo-landing"}/>
                <h2>{props.user.firstName}</h2>
                <button onClick={props.onlogout}>Logout</button>
                <Link to={"/user/actualWeek"}>
                    <button>Arbeitsschichten</button>
                </Link>
                <Link to={"/user/nextWeek"}>
                    <button>Dienstplanwunsch</button>
                </Link>
            </main>
        </>
    )
}
