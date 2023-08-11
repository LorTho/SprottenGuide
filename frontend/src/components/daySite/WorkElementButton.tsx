import {DailyPlan} from "../../model/Day.tsx";
import {useState} from "react";

type Props ={
    worker: DailyPlan,
}

export default function WorkerElementButton(props:Props){
    const[worker , setWorker] = useState<DailyPlan>(props.worker)
    const state = 1;

    function handleStart(){
        const date = new Date();
        const showTime = date.getHours()+':'+date.getMinutes();
        const updateWorker = worker
        updateWorker.start = showTime
        setWorker(updateWorker)
    }

    if(state === 1)
        return <button onClick={handleStart}>Start der Schicht {worker.start}</button>
    if(state === 2)
        return <>
            <button>Pause Anfang</button>
            <button>Schichtende</button>
            </>
    if(state === 3)
        return <button>wieder anfangen</button>
}