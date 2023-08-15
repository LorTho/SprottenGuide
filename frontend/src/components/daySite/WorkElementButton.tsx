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
        props.worker.start = date.getHours() + ':' + date.getMinutes();
        props.update(props.worker)
    }

    function handleBreakStart() {
        const date = new Date();
        props.worker.pause.push({start: date.getHours() + ':' + date.getMinutes(), end: null})
        console.log(props.worker)
        props.update(props.worker)
    }

    function handleBreakEnd() {
        const date = new Date();
        props.worker.pause.forEach(value => {
            if (value.end == null)
                value.end = date.getHours() + ':' + date.getMinutes();
        })
        props.update(props.worker)
    }

    function handleEnd() {
        const date = new Date();
        props.worker.end = date.getHours() + ':' + date.getMinutes()
        props.update(props.worker)
    }

    if (state === 1)
        return <button onClick={handleStart}>Start der Schicht</button>
    if (state === 2)
        return <>
            <button onClick={handleBreakStart}>Pause Anfang</button>
            <button onClick={handleEnd}>Schichtende</button>
        </>
    if (state === 3)
        return <button onClick={handleBreakEnd}>wieder anfangen</button>
    if (state === 4)
        return <p>FEIERABEND</p>
}
