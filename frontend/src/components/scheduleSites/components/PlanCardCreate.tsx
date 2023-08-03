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

type Props = {
    day: ShiftSchedule,
    onUpdate: (nextWeekShift: ShiftSchedule) => void;
}
export default function PlanCardCreate(props: Props) {
    const [targetDay, setTargetDay] = useState<ShiftSchedule>(props.day)
    const names = ["Lorenz", "Arnold", "Rüdiger", "Denise"]

    function setEmployee(name: string, shiftNumber: number) {
        const newTargetDay = {
                ...targetDay,
                shifts: targetDay.shifts.map((value, index) => {
                    if (index === shiftNumber) {
                        return {employeeId: name, startTime: value.startTime}
                    } else {
                        return value
                    }
                })
            }
        setTargetDay(newTargetDay)
        props.onUpdate(newTargetDay)
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
                            <TableCell size={"small"}> {value.employeeId} </TableCell>
                            <TableCell size={"small"} align="right">
                                <div className={"table-Selection"}>
                                    <SelectButton names={names} name={(nameToChange) => setEmployee(nameToChange, index)}/>
                                </div>
                            </TableCell>
                        </TableRow>
                    )
                })}
            </TableBody>
        </Table>
    </TableContainer>
}
