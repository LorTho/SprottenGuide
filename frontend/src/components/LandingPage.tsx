import {Link, useNavigate} from "react-router-dom";
import HeadElement from "./StyleElements.tsx";
import {useEffect} from "react";
import {UserHook} from "../hooks/UserHook.tsx";
export default function LandingPage() {
    const isLogged = UserHook((UserState)=> UserState.isLogged)
    const navigate = useNavigate()

    useEffect(() => {
        if (localStorage.getItem('token') === null)
            navigate("/login")
        else {
            isLogged()
        }
    })

    return (
        <>
            <main>
                <HeadElement title={"Welcome"}/>
                <hr/>
                <Link to={"/user/userSpace"}>
                    <button>UserSide</button>
                </Link>
                <Link to={"/management"}>
                    <button>Management</button>
                </Link>
            </main>
        </>
    )
}
