import HeadElement from "../StyleElements.tsx";
import {Time, User} from "../../model/User.tsx";
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableBody from "@mui/material/TableBody";
import TableContainer from "@mui/material/TableContainer";
import {useEffect, useState} from "react";
import {MenuItem, Select} from "@mui/material";

type Props = {
    user: User,
    wishes: Time[],
    onChangeTimes: (data: Time[]) => void;
}
export default function NextWeek(props: Props) {
    const [wishTime, setWishTime] = useState<Time[]>([
        {day: "MONDAY", startTime: "00:00:00"},
        {day: "TUESDAY", startTime: "00:00:00"},
        {day: "WEDNESDAY", startTime: "00:00:00"},
        {day: "THURSDAY", startTime: "00:00:00"},
        {day: "FRIDAY", startTime: "00:00:00"},
        {day: "SATURDAY", startTime: "00:00:00"},
        {day: "SUNDAY", startTime: "00:00:00"},
    ])

    useEffect(initialiseWishTime, [props.wishes])

    function initialiseWishTime() {
        setWishTime(
            wishTime.map(wishDay => {
                props.wishes.forEach(shift => {
                    if (shift.day === wishDay.day) {
                        wishDay = shift
                    }
                })
                return wishDay
            })
        )
    }

    function handleWishTime() {
        const newTimes = wishTime.filter(value => value.startTime !== "00:00:00")
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
                                key={shift.day}
                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                            >
                                <TableCell component="th" scope="row">
                                    {shift.day}
                                </TableCell>
                                <TableCell align="right">
                                        {shift.day !== "SATURDAY" && shift.day !== "SUNDAY"
                                            ?
                                            <Select
                                                id={shift.day}
                                                value={shift.startTime}
                                                onChange={event => {
                                                    setWishTime(
                                                        wishTime.map(wishDay => {
                                                            if (wishDay.day === shift.day) {
                                                                return {...wishDay, startTime: event.target.value}
                                                            }
                                                            return wishDay
                                                        })
                                                    )
                                                }}
                                            >

                                                <MenuItem value={"00:00:00"}><em>Frei</em></MenuItem>
                                                <MenuItem value={"11:00:00"}>11</MenuItem>
                                                <MenuItem value={"17:00:00"}>17</MenuItem>
                                                <MenuItem value={"19:00:00"}>19</MenuItem>
                                            </Select>
                                            :
                                            <Select
                                                id={shift.day}
                                                value={shift.startTime}
                                                onChange={event => {
                                                    setWishTime(
                                                        wishTime.map(wishDay => {
                                                            if (wishDay.day === shift.day) {
                                                                return {...wishDay, startTime: event.target.value}
                                                            }
                                                            return wishDay
                                                        })
                                                    )
                                                }}
                                            >
                                                <MenuItem value={"00:00:00"}><em>Frei</em></MenuItem>
                                                <MenuItem value={"12:00:00"}>12</MenuItem>
                                                <MenuItem value={"17:00:00"}>17</MenuItem>
                                                <MenuItem value={"18:00:00"}>18</MenuItem>
                                            </Select>
                                        }
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
