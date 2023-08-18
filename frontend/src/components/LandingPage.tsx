import {Link, useNavigate} from "react-router-dom";
import HeadElement from "./StyleElements.tsx";
import {useEffect} from "react";
import {UserHook} from "../hooks/UserHook.tsx";
import {Role} from "../model/User.tsx";

export default function LandingPage() {
    const userRole = UserHook((UserState) => UserState.role)
    const isLogged = UserHook((UserState) => UserState.isLogged)
    const logout = UserHook((UserState) => UserState.logout)
    const navigate = useNavigate()

    useEffect(() => {
        if (localStorage.getItem('token') === null)
            navigate("/login")
        else {
            isLogged()
        }
    })

    function checkRole() {
        if (userRole === Role.ADMIN)
            return <Link to={"/management"}>
                <button>Management</button>
            </Link>
    }

    return (
        <>
            <main>
                <HeadElement title={"Welcome"}/>
                <hr/>
                <Link to={"/user/userSpace"}>
                    <button>UserSide</button>
                </Link>
                {checkRole()}

                <hr/>
                <button onClick={logout}> logout </button>
            </main>
        </>
    )
}
