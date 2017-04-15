package zone.iquest.Utils;

public class FormatSeance {

    public static String format(int seanceId, int questId) {
        String seance = "";
        switch (questId) {
            case 1:
                switch (seanceId) {
                    case 1:
                        seance = "10:30-11:30";
                        break;
                    case 2:
                        seance = "12:00-13:00";
                        break;
                    case 3:
                        seance = "13:30-14:30";
                        break;
                    case 4:
                        seance = "15:00-16:00";
                        break;
                    case 5:
                        seance = "16:30-17:30";
                        break;
                    case 6:
                        seance = "18:00-19:00";
                        break;
                    case 7:
                        seance = "19:30-20:30";
                        break;
                }
                break;
            case 2:
                switch (seanceId) {
                    case 1:
                        seance = "10:00-11:00";
                        break;
                    case 2:
                        seance = "11:30-12:30";
                        break;
                    case 3:
                        seance = "13:00-14:00";
                        break;
                    case 4:
                        seance = "14:30-15:30";
                        break;
                    case 5:
                        seance = "16:00-17:00";
                        break;
                    case 6:
                        seance = "17:30-18:30";
                        break;
                    case 7:
                        seance = "19:00-20:00";
                        break;
                    case 8:
                        seance = "20:30-21:30";
                        break;
                }
                break;
            case 3:
                switch (seanceId) {
                    case 1:
                        seance = "10:30-11:30";
                        break;
                    case 2:
                        seance = "11:45-12:45";
                        break;
                    case 3:
                        seance = "13:00-14:00";
                        break;
                    case 4:
                        seance = "14:15-15:15";
                        break;
                    case 5:
                        seance = "15:30-16:30";
                        break;
                    case 6:
                        seance = "17:45-18:45";
                        break;
                    case 7:
                        seance = "19:00-20:00";
                        break;
                }
                break;
        }
        return seance;
    }
}
