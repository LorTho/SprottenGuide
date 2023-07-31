import HeadElement from "../StyleElements.tsx";
import PlanCard from "./PlanCard.tsx";
import {WorkSchedule} from "../../model/WorkSchedule.tsx";

type Props = {
    schedule: WorkSchedule[],
}
export default function CurrentWeek(props: Props) {
    const currentSchedule = props.schedule.filter(value => value.id === "64c3df7f444a3fa2b8a05b50")
    return <>
        <HeadElement title={"Zeiten"}/>
        <div className={"plan"}>
        {currentSchedule.map(value => (<PlanCard key={1} shift={value.drivers}/>))}
        </div>
        <hr/>
        <div className={"plan"}>
            {currentSchedule.map(value => (<PlanCard key={2} shift={value.kitchen}/>))}
        </div>
    </>
}