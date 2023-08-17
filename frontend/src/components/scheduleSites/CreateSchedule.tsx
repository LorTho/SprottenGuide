import HeadElement from "../StyleElements.tsx";
import PlanCardCreate from "./components/PlanCardCreate.tsx";
import {useState} from "react";
import {ShiftSchedule, WorkSchedule, WorkShift} from "../../model/WorkSchedule.tsx";
import {nanoid} from "nanoid";
import {ScheduleHook} from "../../hooks/ScheduleHook.tsx";
import {useNavigate} from "react-router-dom";

export default function CreateSchedule() {
    const week = ScheduleHook((State)=>State.nextWeek)
    const saveSchedule = ScheduleHook((State)=>State.saveSchedule)
    const navigate = useNavigate()


    const [workSchedule, setWorkSchedule] = useState<WorkSchedule>(week)
    const wishList = week.wishes
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
        saveSchedule(workSchedule, navigate)
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
                return <PlanCardCreate key={nanoid()} day={shift} wishList={userWishes(shift.day)}
                                       onUpdate={(nextWeekShift) => handleUpdateShift("drivers", nextWeekShift)}/>
            })}
        </div>
        <hr/>
        <h3>Kitchen</h3>
        <div className={"plan"}>
            {workSchedule.kitchen.map(shift => {
                return <PlanCardCreate key={nanoid()} day={shift} wishList={userWishes(shift.day)}
                                       onUpdate={(nextWeekShift) => handleUpdateShift("kitchen", nextWeekShift)}/>
            })}
        </div>
        <button onClick={handelSubmit}> Submit </button>
    </>
}
