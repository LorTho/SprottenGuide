import {Link, useNavigate} from "react-router-dom";
import HeadElement from "./StyleElements.tsx";
import {useEffect} from "react";
import {UserHook} from "../hooks/UserHook.tsx";

export default function LandingPage() {
    const navigate = useNavigate()
    const memberCode = UserHook((UserState) => UserState.memberCode)

    useEffect(() => {
        if (memberCode === undefined || memberCode === "anonymousUser")
            navigate("/login")
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
