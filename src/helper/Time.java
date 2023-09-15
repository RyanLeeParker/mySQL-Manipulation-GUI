package helper;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Time
{
        public static String convertTimeDateUTC(String dateTime)
        {
            Timestamp currentTimeStamp = Timestamp.valueOf(dateTime);
            LocalDateTime LocalDT = currentTimeStamp.toLocalDateTime();                                                                     // 1
            ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));            // maybe wrong here                2
            ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of("UTC"));                     // def wrong here                    3          GMT offset?
            LocalDateTime localOUT = utcDT.toLocalDateTime();                                                                                // 4
            String utcOUT = localOUT.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));


            return utcOUT;

            //System.out.println("20: zoneDT: " + zoneDT);
            //System.out.println("21: utcDT: " + utcDT);
            //System.out.println("22: localOUT: " + localOUT);
            //System.out.println("23: uctOUT: " + utcOUT);
        }
}
