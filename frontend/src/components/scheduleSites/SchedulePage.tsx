import HeadElement from "../StyleElements.tsx";
import {Link} from "react-router-dom";

export default function SchedulePage(){
    return(
    <>
        <main>
            <HeadElement title={"Plans"}/>

            <Link to={"/schedule/actualWeek"}>
                <button>Aktueller Plan</button>
            </Link>
            <Link to={"/schedule/nextWeek"}>
                <button>neuer Plan</button>
            </Link>
        </main>
    </>
    )
}
