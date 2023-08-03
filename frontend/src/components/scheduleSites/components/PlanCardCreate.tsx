import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableBody from "@mui/material/TableBody";
import {nanoid} from "nanoid";
import SelectButton from "./SelectButton.tsx";
import TableContainer from "@mui/material/TableContainer";
import {useState} from "react";
import {ShiftSchedule} from "../../../model/WorkSchedule.tsx";
import {DtoUser} from "../../../model/User.tsx";

type Props = {
    day: ShiftSchedule,
    userList: DtoUser[],
    onUpdate: (nextWeekShift: ShiftSchedule) => void;
}
export default function PlanCardCreate(props: Props) {
    const [targetDay, setTargetDay] = useState<ShiftSchedule>(props.day)
    const userNames = getUserAtributes()

    function getUserAtributes(): string[] {
        const getUserNames: string[] = []
        props.userList.forEach(user => {
            getUserNames.push(user.firstName)
        })
        return getUserNames
    }

    function setEmployee(name: string, shiftNumber: number) {
        const userId = getUserId(name)
        const newTargetDay = {
            ...targetDay,
            shifts: targetDay.shifts.map((value, index) => {
                if (index === shiftNumber) {
                    return {employeeId: userId, startTime: value.startTime}
                } else {
                    return value
                }
            })
        }
        setTargetDay(newTargetDay)
        props.onUpdate(newTargetDay)
    }

    function getUserId(name: string) {
        const getUser = props.userList.find(user => user.firstName === name)
        if(getUser === undefined)
            return "--"
        return getUser.id
    }
    function getUserName(id: string) {
        const getUser = props.userList.find(user => user.id === id)
        if(getUser === undefined)
            return "--"
        return getUser.firstName
    }

    return <TableContainer component={Paper} key={321}>
        <Table sx={{width: '95%'}} aria-label="customized table">
            <TableHead>
                <TableRow>
                    <TableCell size={"small"}> StartZeit</TableCell>
                    <TableCell size={"small"}> Name </TableCell>
                    <TableCell size={"small"}> {targetDay.day}</TableCell>
                </TableRow>
            </TableHead>
            <TableBody>

                {targetDay.shifts.map((value, index) => {
                    return (
                        <TableRow
                            key={nanoid()}
                            sx={{'&:last-child td, &:last-child th': {border: 0}}}
                        >
                            <TableCell size={"small"} component="th" scope="row"> {value.startTime} </TableCell>
                            <TableCell size={"small"}> {getUserName(value.employeeId)} </TableCell>
                            <TableCell size={"small"} align="right">
                                <div className={"table-Selection"}>
                                    <SelectButton names={userNames}
                                                  name={(nameToChange) => setEmployee(nameToChange, index)}/>
                                </div>
                            </TableCell>
                        </TableRow>
                    )
                })}
            </TableBody>
        </Table>
    </TableContainer>
}
