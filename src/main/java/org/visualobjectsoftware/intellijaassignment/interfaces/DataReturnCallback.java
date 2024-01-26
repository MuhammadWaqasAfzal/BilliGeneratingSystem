package org.visualobjectsoftware.intellijaassignment.interfaces;

import java.time.LocalDate;

public interface DataReturnCallback {

    void onDataReturn(LocalDate startDate, LocalDate endDate);

}
