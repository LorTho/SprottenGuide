import {User} from "../model/User.tsx";

type Props = {
    week: number,
    user: User,
}

export default function Employee(props: Props) {
    const filterShift = props.user.weeklyTime.length

    console.log(props.user.weeklyTime)
    console.log(filterShift)
    console.log(Array.isArray(filterShift))

    return <>
        <h2> {props.user.firstName}</h2>
        <div className={"shifts"}>
            <table>
                <tbody>
                <tr>
                    <th>Datum</th>
                    <th>Uhrzeit</th>
                </tr>
                {filterShift.map(shifts => {
                  if (shifts.key === props.week){
                      return <p> moin </p>
                }})}
                </tbody>
            </table>
        </div>
    </>
}