import {User} from "../model/User.tsx";

type Props = {
    user: User,
}

export default function Employee(props: Props) {
    console.log(props.user)

    if(!props.user)
        return <p> No User</p>

    return <>
        <h2> {props.user.firstName}</h2>
        <div className={"shifts"}>
            <table>
                <tbody>
                <tr>
                    <th>Datum</th>
                    <th>Uhrzeit</th>
                </tr>
                {}
                </tbody>
            </table>
        </div>
    </>
}