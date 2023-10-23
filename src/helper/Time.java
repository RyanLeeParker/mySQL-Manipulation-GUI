package helper;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Time
{
        public static String convertTimeDateUTC(String dateTime)
        {
            System.out.println("17: dateTime: " + dateTime);
            Timestamp currentTimeStamp = Timestamp.valueOf(dateTime);
            System.out.println("18: currentTimeStamp: " + currentTimeStamp);
            LocalDateTime LocalDT = currentTimeStamp.toLocalDateTime();                                                 // 1
            System.out.println("19: LocalDT: " + LocalDT);
            ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));                        // 2
            System.out.println("20: zoneDT: " + zoneDT);
            ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of("UTC"));                                   // 3           5hr UTC offset
            System.out.println("21: utcDT: " + utcDT);
            LocalDateTime localOUT = utcDT.toLocalDateTime();
            System.out.println("22: localOUT: " + localOUT);// 4
            String utcOUT = localOUT.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
            System.out.println("23: uctOUT: " + utcOUT);

            return utcOUT;
        }
}
