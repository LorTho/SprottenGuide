import {Link} from "react-router-dom";

type Props = {
    title: string
}
export default function HeadElement(props: Props) {
    return (
        <header>
            <div className={"Header-Div"}>
                <div>
                    <Link to={"/"}>
                        <img src={"/Logo.png"} alt={"logo"} className={"logo-Header"}/>
                    </Link>
                </div>
                <div>
                    <h1>{props.title}</h1>
                </div>
            </div>
        </header>
    )
}
