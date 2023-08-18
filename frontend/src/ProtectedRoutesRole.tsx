import {Navigate, Outlet} from "react-router-dom";
import {UserHook} from "./hooks/UserHook.tsx";

export default function ProtectedRoutesRole(){
    const userRole = UserHook((UserState)=>UserState.role)
    const isAuthenticated = userRole === "ADMIN"
    return (
        isAuthenticated ? <Outlet/> : <Navigate to ="/"/>
    )
}
