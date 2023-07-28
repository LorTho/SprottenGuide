type Props = {
    title: string
}
export default function HeadElement(props: Props) {
    return (
        <header>
            <div className={"Header-Div"}>
                <div>
                    <img src={"Logo.png"} alt={"logo"} className={"logo-Header"}/>
                </div>
                <div>
                    <h1>{props.title}</h1>
                </div>
            </div>
        </header>
    )
}