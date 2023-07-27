import HeadElement from "./StyleElements.tsx";
import {Time, User} from "../model/User.tsx";
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableBody from "@mui/material/TableBody";
import TableContainer from "@mui/material/TableContainer";
import {useState} from "react";
import {MenuItem, Select} from "@mui/material";

type Props = {
    user: User,
    onChangeTimes: (data: Time[])=>void;
}
export default function NextWeek(props: Props) {
    const [wishTime, setWishTime] = useState<Time[]>(
        [
            {date: "MONDAY", startTime: "00:00"},
            {date: "TUESDAY", startTime: "00:00"},
            {date: "WEDNESDAY", startTime: "00:00"},
            {date: "THURSDAY", startTime: "00:00"},
            {date: "FRIDAY", startTime: "00:00"},
            {date: "SATURDAY", startTime: "00:00"},
            {date: "SUNDAY", startTime: "00:00"},
        ]
    )

    function handleWishTime(){
        const newTimes = wishTime.filter(value => value.startTime !== "00:00")
        props.onChangeTimes(newTimes)
    }

    return (
        <>
            <HeadElement title={"Wunsch"}/>
            <h3>Wünsche für nächste Woche</h3>
            <TableContainer component={Paper}>
                <Table sx={{width: '90%'}} aria-label="customized table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Datum</TableCell>
                            <TableCell align="right">Startzeit</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {wishTime.map(shift => (
                            <TableRow
                                key={shift.date}
                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                            >
                                <TableCell component="th" scope="row">
                                    {shift.date}
                                </TableCell>
                                <TableCell align="right">
                                    <Select
                                        id={shift.date}
                                        value={shift.startTime}
                                        onChange={event => {
                                            setWishTime(
                                                wishTime.map(day => {
                                                    if (day.date === shift.date) {
                                                        return {...day, startTime: event.target.value}
                                                    }
                                                    return day
                                                })
                                            )
                                        }}
                                    >
                                        <MenuItem value={"00:00"}><em>Frei</em></MenuItem>
                                        <MenuItem value={"11:00"}>11</MenuItem>
                                        <MenuItem value={"17:00"}>17</MenuItem>
                                        <MenuItem value={"19:00"}>19</MenuItem>
                                    </Select>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <button onClick={handleWishTime}>Submit</button>
        </>
    )
}