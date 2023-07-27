import {User} from "../model/User.tsx";

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import HeadElement from "./StyleElements.tsx";

type Props = {
    user: User,
}

export default function ActualWeek(props: Props) {
    return <>
        <HeadElement title={"Zeiten"}/>
        <h2> {props.user.firstName}</h2>
        <TableContainer component={Paper}>
            <Table sx={{ width: '90%' }} aria-label="customized table">
                <TableHead>
                    <TableRow>
                        <TableCell>Datum</TableCell>
                        <TableCell align="right">Uhrzeit</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {props.user.thisWeek.map(shift => (
                        <TableRow
                            key={shift.day}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                {shift.day}
                            </TableCell>
                            <TableCell align="right">{shift.startTime}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    </>
}