import {DtoUser} from "../../model/User.tsx";
import {nanoid} from "nanoid";
import TableCell from "@mui/material/TableCell";
import TableRow from "@mui/material/TableRow";

type Props ={
    user: DtoUser,
}
export default function UserView(props:Props){
    return(
        <TableRow
            key={nanoid()}
            sx={{'&:last-child td, &:last-child th': {border: 0}}}
        >
            <TableCell padding={"none"} size={"small"} component="th" scope="row"> {props.user.firstName} </TableCell>
            <TableCell padding={"none"} size={"small"}> {props.user.lastName} </TableCell>
            <TableCell padding={"none"} size={"small"} align="right">{props.user.memberCode}</TableCell>
        </TableRow>
    )
}