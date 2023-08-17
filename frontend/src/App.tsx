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
import CurrentWeek from "./components/scheduleSites/CurrentWeek.tsx";
import CreateSchedule from "./components/scheduleSites/CreateSchedule.tsx";
import DayView from "./components/daySite/DayView.tsx";
import SchedulePage from "./components/scheduleSites/SchedulePage.tsx";
import Register from "./components/Register.tsx";

export default function App() {
    const userCode = UserHook((UserState) => UserState.memberCode);
    const isLogged = UserHook((UserState) => UserState.isLogged);
    const [initialLoad, setInitialLoad] = useState(true);

    const getWeekNumber = HelperHook((State) => State.getCurrentWeekNumber);

    useEffect(() => {
        try {
            getWeekNumber();
            isLogged();
        } catch (e) {
            console.log(e);
        } finally {
            setInitialLoad(false)
        }
    }, [isLogged])

    if (initialLoad) return null;

    /*
    useEffect(getCurrentWeekSchedule, [])
    useEffect(getNextWeekSchedule, [])

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

                    //Schichtleitung
                    <Route path={"/register"} element={<Register onRegister={handleRegister}/>}/>
                    <Route path={"/schedule/scheduleSite"} element={<SchedulePage/>}/>
                    <Route path={"/schedule/actualWeek"}
                           element={<CurrentWeek schedule={currentWeek} userList={userList}/>}/>
                    <Route path={"/schedule/nextWeek"} element={<CreateSchedule nextWeek={nextWeek} userList={userList}
                                                                                onSubmit={handleSaveCreateSchedule}/>}/>
                    <Route path={"/day"} element={<DayView/>}/>
                </Route>
            </Routes>
        </>
    )
}
