import HeadElement from "../StyleElements.tsx";
import PlanCardShow from "./components/PlanCardShow.tsx";
import {WorkSchedule} from "../../model/WorkSchedule.tsx";

type Props = {
    schedule: WorkSchedule,
}
export default function CurrentWeek(props: Props) {
    return <>
        <HeadElement title={"Zeiten"}/>
        <h3>Drivers</h3>
        <div className={"plan"}>
            <PlanCardShow key={1} shift={props.schedule.drivers}/>
        </div>
        <hr/>
        <h3>Kitchen</h3>
        <div className={"plan"}>
            <PlanCardShow key={2} shift={props.schedule.kitchen}/>
        </div>
    </>
}
