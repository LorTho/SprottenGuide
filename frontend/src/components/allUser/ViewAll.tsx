import {HelperHook} from "../../hooks/Helper.tsx";
import HeadElement from "../StyleElements.tsx";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableBody from "@mui/material/TableBody";
import Table from "@mui/material/Table";
import UserView from "./UserView.tsx";
import {Link} from "react-router-dom";
export default function ViewAll(){
    const userList = HelperHook((State)=> State.userList)

    return (
        <>
            <HeadElement title={"Angestellte"}/>
            <hr/>
            <Table sx={{width: '60%'}} aria-label="customized table">
                <TableHead>
                    <TableRow>
                        <TableCell padding={"none"} size={"small"}> Vorname </TableCell>
                        <TableCell padding={"none"} size={"small"}> Nachname </TableCell>
                        <TableCell padding={"none"} size={"small"} align={"right"}> Nummer</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {userList.map((value) => {
                        return <UserView key={value.memberCode}  user={value}/>
                    })}
                </TableBody>
            </Table>
            <Link to={"/management"}>
                <button className={"back"}> ⬅️ </button>
            </Link>
        </>
    )
}