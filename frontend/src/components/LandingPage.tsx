import {Link, useNavigate} from "react-router-dom";
import HeadElement from "./StyleElements.tsx";
import {useEffect} from "react";
import {UserHook} from "../hooks/UserHook.tsx";
import {DailyHook} from "../hooks/DailyHook.tsx";
import {HelperHook} from "../hooks/Helper.tsx";

export default function LandingPage() {
    const navigate = useNavigate()
    const getDaily = DailyHook((State)=>State.getDaily)
    const getUserList = HelperHook((State)=>State.getUserList)
    const memberCode = UserHook((UserState) => UserState.memberCode)
    useEffect(() => {
        if (memberCode === undefined || memberCode === "anonymousUser")
            navigate("/login")
        getDaily()
        getUserList()
    })

    return (
        <>
            <main>
                <HeadElement title={"Welcome"}/>
                <Link to={"/user/userSpace"}>
                    <button>UserSide</button>
                </Link>
                <Link to={"/schedule/scheduleSite"}>
                    <button>Arbeitsplan</button>
                </Link>
                <Link to={"/day"}>
                    <button>Tagesansicht</button>
                </Link>
            </main>
        </>
    )
}
