import HeadElement from "../StyleElements.tsx";
import {Link} from "react-router-dom";
import {HelperHook} from "../../hooks/Helper.tsx";
import {ScheduleHook} from "../../hooks/ScheduleHook.tsx";
import {useEffect} from "react";

export default function SchedulePage(){
    const currentWeek = HelperHook((State)=>State.currentWeekNumber)
    const getSchedules = ScheduleHook((State)=>State.getWeekSchedules)
    const getUserList = HelperHook((State)=> State.getUserList)

    useEffect(()=>{
        getSchedules(currentWeek);
        getUserList();
    }, [])

    return(
    <>
        <main>
            <HeadElement title={"Plans"}/>
            <hr/>
            <Link to={"/schedule/actualWeek"}>
                <button>Aktueller Plan</button>
            </Link>
            <Link to={"/schedule/nextWeek"}>
                <button>nächste Woche</button>
            </Link>
        </main>
    </>
    )
}
