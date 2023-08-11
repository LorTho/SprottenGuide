import {DailyPlan} from "../../model/Day.tsx";
import WorkerElementButton from "./WorkElementButton.tsx";

type Props = {
    worker: DailyPlan
}
export default function WorkerElement(props: Props) {

        const date = new Date();
        const showTime = date.getHours()+':'+date.getMinutes();


    return <>
        <div className={"WorkerElement"}>
            <p>{props.worker.employeeId}</p>
            <p>{showTime}</p>
            <WorkerElementButton worker={props.worker}/>
        </div>

    </>
}