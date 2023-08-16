import {create} from "zustand";

type State = {
    currentWeekNumber: number,

    getCurrentWeekNumber: () => void,
}

export const HelperHook = create<State>((set) => ({
    currentWeekNumber: 999,

    getCurrentWeekNumber: () => {
        const now = new Date();
        const startOfYear = new Date(now.getFullYear(), 0, 1);
        const startOfWeek = new Date(
            startOfYear.setDate(startOfYear.getDate() - startOfYear.getDay())
        );

        const diffInTime = now.getTime() - startOfWeek.getTime();
        const diffInWeeks = Math.floor(diffInTime / (1000 * 3600 * 24 * 7));

        set({currentWeekNumber:diffInWeeks + 1}); // Add 1 to account for the first week
    },

}))
