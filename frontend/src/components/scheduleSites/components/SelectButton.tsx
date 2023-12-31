import PersonAddIcon from '@mui/icons-material/PersonAdd';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import {useState} from "react";
import { nanoid } from 'nanoid'
import {IconButton} from "@mui/material";

let nameString: string[];
let wishList: {
    name: string,
    startTime: string,
}[];

type Props={
    names: string[],
    userWishList: {
        name: string,
        startTime: string,
    }[],
    name: (name: string)=>void,
}

export default function SelectButton(props:Props) {
    const [open, setOpen] = useState(false);
    const [selectedValue, setSelectedValue] = useState("Auswählen");

    nameString = props.names
    wishList = props.userWishList

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = (value: string) => {
        setOpen(false);
        setSelectedValue(value);
        props.name(value);
    };

    return (
        <div>
            <IconButton color={"primary"} onClick={handleClickOpen}>
                <PersonAddIcon />
            </IconButton>
            <SimpleDialog
                selectedValue={selectedValue}
                open={open}
                onClose={handleClose}
            />
        </div>
    );
}

export interface SimpleDialogProps {
    open: boolean;
    selectedValue: string;
    onClose: (value: string) => void;
}

function SimpleDialog(simpleProps: SimpleDialogProps) {
    const {onClose, selectedValue, open} = simpleProps;

    const handleClose = () => {
        onClose(selectedValue);
    };

    const handleListItemClick = (value: string) => {
        onClose(value);
    };

    return (
        <Dialog onClose={handleClose} open={open}>
            <DialogTitle>Wähle Mitarbeiter</DialogTitle>
            <List key={nanoid()} sx={{pt: 0}}>
                {wishList.map((name) => (
                    <ListItem key={nanoid()} disableGutters>
                        <ListItemButton onClick={() => handleListItemClick(name.name)} key={name.startTime}>
                            <ListItemText primary={name.name} secondary={name.startTime}/>
                        </ListItemButton>
                    </ListItem>
                ))}
                {nameString.map((name) => (
                    <ListItem key={nanoid()} disableGutters>
                        <ListItemButton onClick={() => handleListItemClick(name)} key={name}>
                            <ListItemText primary={name}/>
                        </ListItemButton>
                    </ListItem>
                ))}
            </List>
        </Dialog>
    );
}
