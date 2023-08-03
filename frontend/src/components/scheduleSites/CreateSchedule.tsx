import HeadElement from "../StyleElements.tsx";
import PlanCardCreate from "./components/PlanCardCreate.tsx";
import {useState} from "react";
import {exampleWorkShift, ShiftSchedule, WorkSchedule} from "../../model/WorkSchedule.tsx";
import {nanoid} from "nanoid";
import {DtoUser} from "../../model/User.tsx";

type Props ={
    nextWeek: WorkSchedule,
    userList: DtoUser[],
    onSubmit: (workSchedule: WorkSchedule)=>void,
}
export default function CreateSchedule(props: Props) {
    const [workSchedule, setWorkSchedule] = useState<WorkSchedule>(exampleWorkShift)

    function handleUpdateShift(kind: string, nextWeekShift: ShiftSchedule) {
        let newWorkSchedule = workSchedule
        if (kind === "drivers") {
            newWorkSchedule = {
                ...workSchedule,
                drivers: workSchedule.drivers.map(shifts => {
                    if (shifts.day === nextWeekShift.day)
                        return nextWeekShift
                    return shifts
                })
            }
        }
        if (kind === "kitchen") {
            newWorkSchedule = {
                ...workSchedule,
                kitchen: workSchedule.kitchen.map(shifts => {
                    if (shifts.day === nextWeekShift.day)
                        return nextWeekShift
                    return shifts
                })
            }
        }
        setWorkSchedule(newWorkSchedule)
    }

    function handelSubmit() {
        props.onSubmit(workSchedule)
    }

    return <>
        <HeadElement title={"Erstellen"}/>
        <h3>Driver</h3>
        <div className={"plan"}>
            {workSchedule.drivers.map(shift => {
                return <PlanCardCreate key={nanoid()} day={shift} userList={props.userList}
                                       onUpdate={(nextWeekShift) => handleUpdateShift("drivers", nextWeekShift)}/>
            })}
        </div>
        <hr/>
        <h3>Kitchen</h3>
        <div className={"plan"}>
            {workSchedule.kitchen.map(shift => {
                return <PlanCardCreate key={nanoid()} day={shift} userList={props.userList}
                                       onUpdate={(nextWeekShift) => handleUpdateShift("kitchen", nextWeekShift)}/>
            })}
        </div>
        <button onClick={handelSubmit}> Submit </button>
    </>
}
