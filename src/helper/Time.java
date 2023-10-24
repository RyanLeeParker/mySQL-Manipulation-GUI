package helper;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Time
{
        public static String convertTimeDateUTC(String dateTime)
        {
            Timestamp currentTimeStamp = Timestamp.valueOf(dateTime);
            LocalDateTime LocalDT = currentTimeStamp.toLocalDateTime();
            ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
            ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime localOUT = utcDT.toLocalDateTime();
            String utcOUT = localOUT.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));

            return utcOUT;
        }
}
