import HeadElement from "../StyleElements.tsx";
import PlanCard from "./PlanCard.tsx";
import {WorkSchedule} from "../../model/WorkSchedule.tsx";

type Props = {
    schedule: WorkSchedule,
}
export default function CurrentWeek(props: Props) {
    return <>
        <HeadElement title={"Zeiten"}/>
        <div className={"plan"}>
        <PlanCard key={1} shift={props.schedule.drivers}/>
        </div>
        <hr/>
        <div className={"plan"}>
            <PlanCard key={2} shift={props.schedule.kitchen}/>
        </div>
    </>
}
