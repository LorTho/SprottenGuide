import HeadElement from "./StyleElements.tsx";
import {Link} from "react-router-dom";
import {HelperHook} from "../hooks/Helper.tsx";
import {useEffect} from "react";
import {DailyHook} from "../hooks/DailyHook.tsx";

export default function Management(){
    const getUserList = HelperHook((State)=>State.getUserList)
    const getDaily = DailyHook((State)=>State.getDaily)

    useEffect(() => {
        getUserList()
        getDaily()
    })
    return (
        <>
            <main>
                <HeadElement title={"Management"}/>
                <hr/>
                <Link to={"/schedule/scheduleSite"}>
                    <button>Arbeitsplan</button>
                </Link>
                <Link to={"/day"}>
                    <button>Tagesansicht</button>
                </Link>
                <hr/>
                <Link to={"/register"}>
                    <button>neuer Angestellter</button>
                </Link>
            </main>
        </>
    )
}
