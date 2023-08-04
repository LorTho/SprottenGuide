import Table from "@mui/material/Table";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableHead from "@mui/material/TableHead";
import TableContainer from '@mui/material/TableContainer';
import {ShiftSchedule} from "../../../model/WorkSchedule.tsx";
import Paper from "@mui/material/Paper";
import TableBody from "@mui/material/TableBody";
import { nanoid } from 'nanoid'
import {DtoUser} from "../../../model/User.tsx";

type Props = {
    shift: ShiftSchedule[],
    userList: DtoUser[],
}
export default function PlanCardShow(props: Props) {
    function getUserName(id: string) {
        const getUser = props.userList.find(user => user.id === id)
        if(getUser === undefined)
            return "--"
        return getUser.firstName
    }

    return <>
        {props.shift.map(value => (
            <TableContainer component={Paper} key={value.day}>
                <Table sx={{width: '14%'}} aria-label="customized table">
                    <TableHead>
                        <TableRow>
                            <TableCell size={"small"}>{value.day}</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {value.shifts.map(shift => (
                            <TableRow
                                key={nanoid()}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell size={"small"} align="left" component="th" scope="row">
                                    {shift.startTime}
                                </TableCell>
                                <TableCell size={"small"} align="left">{getUserName(shift.employeeId)}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        ))}
    </>
}
