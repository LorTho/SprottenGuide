import HeadElement from "../StyleElements.tsx";
import PlanCardCreate from "./components/PlanCardCreate.tsx";
import {useState} from "react";
import {exampleWorkShift, ShiftSchedule, WorkSchedule} from "../../model/WorkSchedule.tsx";
import {nanoid} from "nanoid";

export default function CreateSchedule() {
    const [workSchedule, setWorkSchedule] = useState<WorkSchedule>(exampleWorkShift)

    function handleUpdateShift(kind: string, nextWeekShift: ShiftSchedule) {
        let newWorkSchedule = workSchedule
        if (kind === "drivers") {
            newWorkSchedule={
                ...workSchedule,
                drivers: workSchedule.drivers.map(shifts => {
                    if (shifts.day === nextWeekShift.day)
                        return nextWeekShift
                    return shifts
                })
            }
        }
        if (kind === "kitchen") {
            newWorkSchedule={
                ...workSchedule,
                kitchen: workSchedule.kitchen.map(shifts => {
                    if (shifts.day === nextWeekShift.day)
                        return nextWeekShift
                    return shifts
                })
            }
        }
        setWorkSchedule(newWorkSchedule)
        console.log(newWorkSchedule)
    }

    function handelSubmit(){

    }
    return <>
        <HeadElement title={"Erstellen"}/>
        {workSchedule.drivers.map(shift => {
            return <div key={nanoid()}>
                    <h3>{shift.day}</h3>
                    <PlanCardCreate day={shift} onUpdate={(nextWeekShift)=>handleUpdateShift("drivers", nextWeekShift)}/>
                </div>
        })})
        <button onSubmit={handelSubmit}></button>
    </>
}
