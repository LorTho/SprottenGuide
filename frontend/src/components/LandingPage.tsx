import {Link, useNavigate} from "react-router-dom";
import HeadElement from "./StyleElements.tsx";
import {useEffect} from "react";

export default function LandingPage() {

    const navigate = useNavigate()

    useEffect(() => {
        if (localStorage.getItem('token') === null)
            navigate("/login")


    })

    return (
        <>
            <main>
                <HeadElement title={"Welcome"}/>
                <hr/>
                <Link to={"/user/userSpace"}>
                    <button>UserSide</button>
                </Link>
                <Link to={"/management"}>
                    <button>Management</button>
                </Link>
            </main>
        </>
    )
}
