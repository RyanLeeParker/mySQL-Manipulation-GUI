package helper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Time
{
        public static String convertTimeDateUTC(String dateTime)
        {
            Timestamp currentTimeStamp = Timestamp.valueOf(dateTime);
            LocalDateTime LocalDT = currentTimeStamp.toLocalDateTime();
            ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));            // maybe wrong here
            System.out.println("20: zoneDT: " + zoneDT);
            ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of("UTC"));                     // def wrong here
            System.out.println("21: utcDT: " + utcDT);
            LocalDateTime localOUT = utcDT.toLocalDateTime();
            System.out.println("22: localOUT: " + localOUT);
            String utcOUT = localOUT.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
            System.out.println("23: uctOUT: " + utcOUT);

            return utcOUT;


            //Timestamp currentTimeStamp = Timestamp.valueOf(String.valueOf(dateTime));
            //System.out.println("18: dateTime: " + dateTime);
            //System.out.println("19: LocalDT: " + LocalDT);
        }
}
