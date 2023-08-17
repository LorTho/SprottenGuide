import {Navigate, Outlet} from "react-router-dom";

type Props = {
    user: string |undefined,
}
export default function ProtectedRoutes(props:Props){
    const isAuthenticated = props.user !== undefined && props.user !== "0"
    return (
        isAuthenticated ? <Outlet/> : <Navigate to ="/login"/>
    )
}
