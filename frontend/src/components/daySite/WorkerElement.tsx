import {DailyPlan} from "../../model/Day.tsx";
import WorkerElementButton from "./WorkElementButton.tsx";

type Props = {
    worker: DailyPlan,
    onUpdate: (worker: DailyPlan) => void,
}
export default function WorkerElement(props: Props) {

    function onUpdate(updateWorker: DailyPlan) {
        props.onUpdate(updateWorker)
    }

    return <>
        <div className={"WorkerElement"}>
            <p>{props.worker.employeeId}</p>
            <p>{props.worker.time}</p>
            <WorkerElementButton worker={props.worker} state={1} update={onUpdate}/>
        </div>

    </>
}