import {DailyPlan, Day} from "../../model/Day.tsx";
import WorkerElement from "./WorkerElement.tsx";
import {nanoid} from "nanoid";
import HeadElement from "../StyleElements.tsx";
import {useState} from "react";

type Props = {
    daily: Day,
    onUpdate: (day: Day)=>void
}
export default function DayView(props: Props) {
    const [daily, setDaily] = useState<Day>(props.daily)

    function handleUpdate(updateWorker: DailyPlan) {
        const newDaily = {
            ...daily,
            shifts: daily.dailyPlanList.map(value => {
                if (value.employeeId === updateWorker.employeeId) {
                    return updateWorker
                } else {
                    return value
                }
            })
        }
        setDaily(newDaily)
        props.onUpdate(newDaily)
    }

    return <>
        <HeadElement title={"Tagesansicht"}/>
        {props.daily.dailyPlanList.map(work => {
            return <WorkerElement key={nanoid()} worker={work} onUpdate={handleUpdate}/>
        })}
    </>
}