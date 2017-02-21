import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ragonda on 20/02/2017.
 */
public class Utils {

    public static Calendar getCalendar() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:");
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal;
    }

    public static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:");
        Date newDate = dateFormat.parse(date);
        return newDate;
    }
}
