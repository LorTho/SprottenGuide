import {Day} from "../../model/Day.tsx";
import WorkerElement from "./WorkerElement.tsx";
import {nanoid} from "nanoid";
import HeadElement from "../StyleElements.tsx";

type Props = {
    daily:Day,
}
export default function DayView(props:Props){

    return <>
        <HeadElement title={"Tagesansicht"}/>
        {props.daily.dailyPlanList.map(work =>{
            return <WorkerElement key={nanoid()} worker={work}/>
        })}
    </>
}