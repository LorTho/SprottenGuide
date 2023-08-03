import Table from "@mui/material/Table";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableHead from "@mui/material/TableHead";
import TableContainer from '@mui/material/TableContainer';
import {ShiftSchedule} from "../../../model/WorkSchedule.tsx";
import Paper from "@mui/material/Paper";
import TableBody from "@mui/material/TableBody";
import { nanoid } from 'nanoid'

type Props = {
    shift: ShiftSchedule[],
}
export default function PlanCardShow(props: Props) {
    return <>
        {props.shift.map(value => (
            <TableContainer component={Paper} key={value.day}>
                <Table sx={{width: '90%'}} aria-label="customized table">
                    <TableHead>
                        <TableRow>
                            <TableCell size={"small"}>{value.day}</TableCell>
                            <TableCell size={"small"}>  </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {value.shifts.map(shift => (
                            <TableRow
                                key={nanoid()}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell size={"small"} component="th" scope="row">
                                    {shift.startTime}
                                </TableCell>
                                <TableCell size={"small"} align="right">{shift.employeeId}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        ))}
    </>
}
