import HeadElement from "../StyleElements.tsx";
import {Link} from "react-router-dom";

export default function SchedulePage(){
    return(
    <>
        <main>
            <HeadElement title={"Dienstplan"}/>

            <Link to={"/schedule/actualWeek"}>
                <button>Arbeitsschichten</button>
            </Link>
            <Link to={"/schedule/nextWeek"}>
                <button>Dienstplanwunsch</button>
            </Link>
        </main>
    </>
    )
}