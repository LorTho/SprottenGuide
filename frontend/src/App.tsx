import './App.css'
import {useEffect, useState} from "react";
import Week from "./components/userSites/Week.tsx";
import {Route, Routes} from "react-router-dom";
import LandingPage from "./components/LandingPage.tsx";
import Login from "./components/Login.tsx";
import WishNextWeek from "./components/userSites/WishNextWeek.tsx";
import UserPage from "./components/userSites/UserPage.tsx";
import ProtectedRoutes from "./ProtectedRoutes.tsx";
import {UserHook} from "./hooks/UserHook.tsx";
import {HelperHook} from "./hooks/Helper.tsx";

export default function App() {
    const userCode = UserHook((UserState) => UserState.memberCode);
    const isLogged = UserHook((UserState) => UserState.isLogged);
    const [initialLoad, setInitialLoad] = useState(true);

    const getWeekNumber = HelperHook((State)=> State.getCurrentWeekNumber);

    useEffect(()=>{
        try{
            getWeekNumber();
            isLogged();
        } catch(e){
            console.log(e);
        } finally {
            setInitialLoad(false)
        }
    }, [isLogged])

    if(initialLoad) return null;

    /*
    const [userList, setUserList] = useState<DtoUser[]>([])
    const [currentWeek, setCurrentWeek] = useState<WorkSchedule>()
    const [nextWeek, setNextWeek] = useState<WorkSchedule>()

    const current = getCurrentWeekNumber()
    const next = current + 1

    useEffect(getCurrentWeekSchedule, [])
    useEffect(getNextWeekSchedule, [])
    useEffect(getUserList, [])

    const navigate = useNavigate()



    function getCurrentWeekSchedule() {
        axios.get("/api/schedule/" + current)
            .then(response => {
                setCurrentWeek(response.data)
            })
    }

    function getNextWeekSchedule() {
        axios.get("/api/schedule/" + next)
            .then(response => {
                setNextWeek(response.data)
            })
    }

    function handleRegister(newUser: DtoUser) {
        axios.post("/api/user", newUser)
            .then(response => {
                console.log(response.data)
            })
        navigate("/")
    }


    function handleSaveCreateSchedule(workSchedule: WorkSchedule) {
        axios.put("/api/schedule", workSchedule)
            .then(response => {
                setNextWeek(response.data)
            })
        navigate("/")
    }

    function getUserList() {
        axios.get("/api/user/list")
            .then(response => {
                setUserList(response.data)
            })
    }

    if (employee === undefined)
        return <h1>Mitarbeiter wird geladen!</h1>
    if (employeeShifts === undefined)
        return <h1>Arbeitszeiten werden geladen!</h1>
    if (employeeWish === undefined)
        return <h1>Wunschliste wird geladen!</h1>
    if (currentWeek === undefined)
        return <h1>Arbeitsplan wird geladen!</h1>
    if (nextWeek === undefined)
        return <h1>Wunschplan wird geladen!</h1>
    if (daily === undefined)
        return <h1>Tagesansicht wird geladen</h1>
     */

    return (
        <>
            <Routes>
                <Route path={"/"} element={<LandingPage/>}/>
                <Route path={"/login"} element={<Login/>}/>
                <Route element={<ProtectedRoutes user={userCode}/>}>
                    <Route path={"/user/userSpace"} element={<UserPage/>}/>
                    <Route path={"/user/actualWeek"} element={<Week select={0}/>}/>
                    <Route path={"/user/nextWeek"} element={<Week select={1}/>}/>
                    <Route path={"/user/wishPlan"}
                           element={<WishNextWeek/>}/>
                </Route>
            </Routes>
        </>
    )
}
