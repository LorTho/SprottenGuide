import {Link} from "react-router-dom";
import HeadElement from "./StyleElements.tsx";

export default function LandingPage() {

    return (
        <>
            <main>
                <HeadElement title={"Welcome"}/>
                <Link to={"/user/userSpace"}>
                    <button>UserSide</button>
                </Link>
                <Link to={"/schedule/scheduleSite"}>
                    <button>Arbeitsplan</button>
                </Link>
            </main>
        </>
    )
}
