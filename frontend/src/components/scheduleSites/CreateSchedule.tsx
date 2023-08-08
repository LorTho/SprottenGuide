import HeadElement from "../StyleElements.tsx";
import PlanCardCreate from "./components/PlanCardCreate.tsx";
import {useState} from "react";
import {ShiftSchedule, WorkSchedule, WorkShift} from "../../model/WorkSchedule.tsx";
import {nanoid} from "nanoid";
import {DtoUser} from "../../model/User.tsx";

type Props ={
    nextWeek: WorkSchedule,
    userList: DtoUser[],
    onSubmit: (workSchedule: WorkSchedule)=>void,
}
export default function CreateSchedule(props: Props) {
    const [workSchedule, setWorkSchedule] = useState<WorkSchedule>(props.nextWeek)
    const wishList = props.nextWeek.wishes
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
    function userWishes(day: string){
        const userWishesDaily: WorkShift[] = []
        wishList.forEach(employee =>{
            employee.shifts.forEach(daywish =>{
                if(daywish.day === day)
                    userWishesDaily.push({employeeId: employee.employeeId, startTime: daywish.startTime})
            })
        })
        return userWishesDaily
    }

    return <>
        <HeadElement title={"Erstellen"}/>
        <h3>Driver</h3>
        <div className={"plan"}>
            {workSchedule.drivers.map(shift => {
                return <PlanCardCreate key={nanoid()} day={shift} userList={props.userList} wishList={userWishes(shift.day)}
                                       onUpdate={(nextWeekShift) => handleUpdateShift("drivers", nextWeekShift)}/>
            })}
        </div>
        <hr/>
        <h3>Kitchen</h3>
        <div className={"plan"}>
            {workSchedule.kitchen.map(shift => {
                return <PlanCardCreate key={nanoid()} day={shift} userList={props.userList} wishList={userWishes(shift.day)}
                                       onUpdate={(nextWeekShift) => handleUpdateShift("kitchen", nextWeekShift)}/>
            })}
        </div>
        <button onClick={handelSubmit}> Submit </button>
    </>
}
