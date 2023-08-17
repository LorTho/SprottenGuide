import {DailyPlan, Day} from "../../model/Day.tsx";
import WorkerElement from "./WorkerElement.tsx";
import {nanoid} from "nanoid";
import HeadElement from "../StyleElements.tsx";
import {useEffect, useState} from "react";
import {DailyHook} from "../../hooks/DailyHook.tsx";

export default function DayView() {
    const currentDaily = DailyHook((State)=>State.daily)
    const getDaily = DailyHook((State)=>State.getDaily)
    const saveDaily = DailyHook((State)=>State.setDaily)

    const [daily, setDaily] = useState<Day>(currentDaily)

    useEffect(()=>{
        getDaily();
    }, [getDaily])


    function planList(): JSX.Element{
        if(daily.dailyPlanList.length === 0)
            return <p> Keine Arbeiter gefunden </p>
        if(daily) {
            return <>
                {daily.dailyPlanList.map(employee => {
                    return <WorkerElement key={nanoid()} worker={employee} onUpdate={handleUpdate}/>
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
            saveDaily(newDaily)
        }
    }

    return <>
        <HeadElement title={"Tagesansicht"}/>
        {planList()}
    </>
}
