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
import {ShiftSchedule, WorkShift} from "../../../model/WorkSchedule.tsx";
import {HelperHook} from "../../../hooks/Helper.tsx";

type Props = {
    day: ShiftSchedule,
    wishList: WorkShift[],
    onUpdate: (nextWeekShift: ShiftSchedule) => void;
}
export default function PlanCardCreate(props: Props) {
    const userList = HelperHook((State)=> State.userList)
    const getUserName = HelperHook((State)=> State.getUserName)
    const getUserCode = HelperHook((State)=> State.getUserCode)
    const [targetDay, setTargetDay] = useState<ShiftSchedule>(props.day)
    const userNames = getUserAttributes()
    const wishList = getWishList()

    function getUserAttributes(): string[] {
        const userNamesList: string[] = []
        userList.forEach(user => {
            userNamesList.push(user.firstName)
        })
        return userNamesList
    }

    function setEmployee(name: string, shiftNumber: number) {
        const userId = getUserCode(name)
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

    function getWishList(){
        const returnList: {
            name: string,
            startTime: string,
        }[] = []
        props.wishList.forEach(employee=>{
            returnList.push({name: getUserName(employee.employeeId), startTime: employee.startTime})
        })
        return returnList
    }

    return <TableContainer component={Paper} key={321}>
        <Table sx={{width: '95%'}} aria-label="customized table">
            <TableHead>
                <TableRow>
                    <TableCell padding={"none"} size={"small"}> Zeit </TableCell>
                    <TableCell padding={"none"} size={"small"}> Name </TableCell>
                    <TableCell padding={"none"} size={"small"} align={"right"}> {targetDay.day.substring(8,10)}</TableCell>
                </TableRow>
            </TableHead>
            <TableBody>

                {targetDay.shifts.map((value, index) => {
                    return (
                        <TableRow
                            key={nanoid()}
                            sx={{'&:last-child td, &:last-child th': {border: 0}}}
                        >
                            <TableCell padding={"none"} size={"small"} component="th" scope="row"> {value.startTime.substring(0,5)} </TableCell>
                            <TableCell padding={"none"} size={"small"}> {getUserName(value.employeeId)} </TableCell>
                            <TableCell padding={"none"} size={"small"} align="right">
                                <div className={"table-Selection"}>
                                    <SelectButton names={userNames} userWishList={wishList}
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
