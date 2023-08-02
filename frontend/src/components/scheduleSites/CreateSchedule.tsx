import HeadElement from "../StyleElements.tsx";
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableBody from "@mui/material/TableBody";
import {nanoid} from "nanoid";
import TableContainer from "@mui/material/TableContainer";
import {useState} from "react";
import SelectButton from "./components/SelectButton.tsx";

export default function CreateSchedule() {
    const times = ["11:00", "11:00", "17:00", "17:00", "19:00"]
    const names = ["Lorenz", "Arnold", "Rüdiger", "Denise"]
    const [active, setActive] = useState("Auswählen")

    function setEmployee(name: string) {
        setActive(name)
    }


    return <>
        <HeadElement title={"Erstellen"}/>

        <TableContainer component={Paper} key={321}>
            <Table sx={{width: '95%'}} aria-label="customized table">
                <TableHead>
                    <TableRow>
                        <TableCell> StartZeit</TableCell>
                        <TableCell> Name </TableCell>
                        <TableCell> </TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>

                    {times.map(value => {
                        return (
                            <TableRow
                                key={nanoid()}
                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                            >
                                <TableCell component="th" scope="row"> {value} </TableCell>
                                <TableCell> {active} </TableCell>
                                <TableCell align="right">
                                    <div className={"table-Selection"}>
                                        <SelectButton names={names} name={setEmployee}/>
                                    </div>
                                </TableCell>
                            </TableRow>
                        )
                    })}
                </TableBody>
            </Table>
        </TableContainer>
    </>
}
