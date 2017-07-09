package orm;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.calendar.SingleDaySelectionModel;

/**
 * Created by Uliana on 16.12.2016.
 */
public class MyDateTimeTicker extends JXDatePicker {

    public MyDateTimeTicker () {
        super();
        getMonthView().setSelectionModel(new SingleDaySelectionModel());
    }
}
