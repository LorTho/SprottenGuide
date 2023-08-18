import {Link} from "react-router-dom";
import HeadElement from "./StyleElements.tsx";
import {UserHook} from "../hooks/UserHook.tsx";
import {useEffect} from "react";
import {HelperHook} from "../hooks/Helper.tsx";

export default function UserPage() {
    const memberCode = UserHook((UserState) => UserState.memberCode)
    const employee = UserHook((UserState) => UserState.employee)
    const logout = UserHook((UserState) => UserState.logout)
    const getWishes = UserHook((UserState) => UserState.getEmployeeWish)
    const currentWeek = HelperHook((State) => State.currentWeekNumber)

    const getEmployee = UserHook(((UserState) => UserState.getEmployee))

    useEffect(() => {
        getEmployee(memberCode);
        getWishes(memberCode, currentWeek+1)
    },[])

    return (
        <>
            <main>
                <HeadElement title={"UserSide"}/>
                <h2>{employee.firstName}</h2>
                <Link to={"/user/actualWeek"}>
                    <button>Diese Woche</button>
                </Link>
                <Link to={"/user/nextWeek"}>
                    <button>Nächste Woche</button>
                </Link>
                <Link to={"/user/wishPlan"}>
                    <button>Dienstplanwunsch</button>
                </Link>
                <hr/>
                <button onClick={logout}>Logout</button>
            </main>
            <Link to={"/"}>
                <button className={"back"}> ⬅️ </button>
            </Link>
        </>
    )
}
