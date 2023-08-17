import HeadElement from "../StyleElements.tsx";
import PlanCardShow from "./components/PlanCardShow.tsx";
import {ScheduleHook} from "../../hooks/ScheduleHook.tsx";

export default function CurrentWeek() {
    const currentWeek = ScheduleHook((State)=>State.currentWeek)
    return <>
        <HeadElement title={"Zeiten"}/>
        <h3>Drivers</h3>
        <div className={"plan"}>
            <PlanCardShow key={1} shift={currentWeek.drivers}/>
        </div>
        <hr/>
        <h3>Kitchen</h3>
        <div className={"plan"}>
            <PlanCardShow key={2} shift={currentWeek.kitchen}/>
        </div>
    </>
}
