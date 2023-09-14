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
            System.out.println("20: zoneDT: " + zoneDT);
            ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of("UTC"));                     // def wrong here                    3          GMT offset?
            System.out.println("21: utcDT: " + utcDT);
            LocalDateTime localOUT = utcDT.toLocalDateTime();                                                                                // 4
            System.out.println("22: localOUT: " + localOUT);
            String utcOUT = localOUT.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
            System.out.println("23: uctOUT: " + utcOUT);

            return utcOUT;

            //Timestamp currentTimeStamp = Timestamp.valueOf(String.valueOf(dateTime));
            //System.out.println("18: dateTime: " + dateTime);
            //System.out.println("19: LocalDT: " + LocalDT);
        }

//    public static LocalDateTime convertTimeDateUTC(String dateTime) {
//        Timestamp currentTimeStamp = Timestamp.valueOf(String.valueOf(dateTime));
//        LocalDateTime LocalDT = currentTimeStamp.toLocalDateTime();
//        ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
//        ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of("UTC"));
//        LocalDateTime localOUT = utcDT.toLocalDateTime();
//        String utcOUT = localOUT.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
//        //return utcOUT;
//        return localOUT;
//    }
}
