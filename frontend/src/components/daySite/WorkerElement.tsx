import {DailyPlan} from "../../model/Day.tsx";
import WorkerElementButton from "./WorkElementButton.tsx";
import {HelperHook} from "../../hooks/Helper.tsx";

type Props = {
    worker: DailyPlan,
    onUpdate: (worker: DailyPlan) => void,
}
export default function WorkerElement(props: Props) {
    const getUserName = HelperHook((State) => State.getUserName)

    function onUpdate(updateWorker: DailyPlan) {
        props.onUpdate(updateWorker)
    }

    function getStatus(): number {
        if (props.worker.start === null)
            return 1
        if (props.worker.end !== null)
            return 4
        if (props.worker.pause.length) {
            if (props.worker.pause[props.worker.pause.length - 1].end === null)
                return 3
        }
        if (props.worker.start)
            return 2
        return 1
    }

    return <>
        <div className={"WorkerElement"}>
            <div className={"WorkerElementName"}>
                <p>{getUserName(props.worker.employeeId)}</p>
                <p>{props.worker.time}</p>
            </div>
            <div className={"WorkerElementButton"}>
                <WorkerElementButton worker={props.worker} state={getStatus()} update={onUpdate}/>
            </div>
        </div>

    </>
}