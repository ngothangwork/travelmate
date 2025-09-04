package dev.thangngo.travelmate.logics;

import java.time.LocalDate;

public class ValidateDate {

    public boolean validateTimeOfItenerary(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)) {
            return false;
        }
        return true;
    }
}
