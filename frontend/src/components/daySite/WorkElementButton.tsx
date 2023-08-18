import {DailyPlan} from "../../model/Day.tsx";

type Props = {
    worker: DailyPlan,
    state: number,
    update: (updateWorker: DailyPlan) => void,
}

export default function WorkerElementButton(props: Props) {
    const state = props.state

    function handleStart() {
        const date = new Date();
        props.worker.start = date.getHours() + ':' + ('0' + date.getMinutes()).slice(-2);
        props.update(props.worker)
    }

    function handleBreakStart() {
        const date = new Date();
        props.worker.pause.push({start: date.getHours() + ':' + ('0' + date.getMinutes()).slice(-2), end: null})
        props.update(props.worker)
    }

    function handleBreakEnd() {
        const date = new Date();
        props.worker.pause.forEach(value => {
            if (value.end == null)
                value.end = date.getHours() + ':' + ('0' + date.getMinutes()).slice(-2);
        })
        props.update(props.worker)
    }

    function handleEnd() {
        const date = new Date();
        props.worker.end = date.getHours() + ':' + ('0' + date.getMinutes()).slice(-2)
        props.update(props.worker)
    }

    if (state === 1)
        return <button className={"button-dayView"} onClick={handleStart}>Start der Schicht</button>
    if (state === 2)
        return <>
            <button className={"button-dayView"} onClick={handleBreakStart}>Pause Anfang</button>
            <button className={"button-dayView"} onClick={handleEnd}>Schichtende</button>
        </>
    if (state === 3)
        return <button className={"button-dayView"} onClick={handleBreakEnd}>wieder anfangen</button>
    if (state === 4)
        return <div className={"div-dayView"}>
            <h5>Schicht zu Ende </h5>
        </div>
}
