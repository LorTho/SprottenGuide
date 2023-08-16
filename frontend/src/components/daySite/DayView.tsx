import {DailyPlan, Day} from "../../model/Day.tsx";
import WorkerElement from "./WorkerElement.tsx";
import {nanoid} from "nanoid";
import HeadElement from "../StyleElements.tsx";
import {useEffect, useState} from "react";
import axios from "axios";

export default function DayView() {
    const [daily, setDaily] = useState<Day>()

    useEffect(getDaily)

    function getDaily() {
        axios.get("/api/month/today")
            .then(response => {
                setDaily(response.data)
            })
    }
    function planList(): JSX.Element{
        if(daily !== undefined) {
            return <>
                {daily.dailyPlanList.map(work => {
                    return <WorkerElement key={nanoid()} worker={work} onUpdate={handleUpdate}/>
            })}
            </>
        }else{
            return <p>Not found!</p>
        }
    }

    function handleUpdate(updateWorker: DailyPlan) {
        if (daily !== undefined) {
            const newDaily = {
                ...daily,
                dailyPlanList: daily.dailyPlanList.map(value => {
                    if (value.employeeId === updateWorker.employeeId) {
                        return updateWorker
                    } else {
                        return value
                    }
                })
            }
            setDaily(newDaily)
            axios.put("/api/month/save", newDaily)
                .then(response => {
                    setDaily(response.data)
                })
        }
    }

    return <>
        <HeadElement title={"Tagesansicht"}/>
        {planList()}
    </>
}
