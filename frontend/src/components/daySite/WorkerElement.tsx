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

    function getStatus(): number {
        if (props.worker.start === null)
            return 1
        if (props.worker.end !== null)
            return 4
        console.log(props.worker.employeeId)
        console.log(!props.worker.pause.length)
        if (props.worker.pause.length) {
            if (props.worker.pause[props.worker.pause.length - 1].end === null)
                return 3
        }
        if (props.worker.start !== null)
            return 2
        return 1
    }

    return <>
        <div className={"WorkerElement"}>
            <p>{props.worker.employeeId}</p>
            <p>{props.worker.time}</p>
            <WorkerElementButton worker={props.worker} state={getStatus()} update={onUpdate}/>
        </div>

    </>
}