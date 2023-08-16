import {Link, useNavigate} from "react-router-dom";
import HeadElement from "./StyleElements.tsx";
import {useEffect} from "react";

type Props = {
    user: string | undefined
}
export default function LandingPage(props: Props) {
    const navigate = useNavigate()
    console.log(props.user)

    useEffect(() => {
        if (props.user === undefined)
            navigate("/login")
    })

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
                <Link to={"/day"}>
                    <button>Tagesansicht</button>
                </Link>
            </main>
        </>
    )
}
