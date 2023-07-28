import {Link} from "react-router-dom";
import {User} from "../../model/User.tsx";
import HeadElement from "../StyleElements.tsx";

type Props ={
    user: User,
    onLogout: () => void
}
export default function UserPage(props:Props){
    return (
        <>
            <main>
                <HeadElement title={"UserSide"}/>
                <h2>{props.user.firstName}</h2>
                <button onClick={props.onLogout}>Logout</button>
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