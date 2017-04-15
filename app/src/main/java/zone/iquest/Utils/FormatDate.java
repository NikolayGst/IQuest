package zone.iquest.Utils;

public class FormatDate {

    public static String format(String date) {

        String dateResult = "";
        String parts[] = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        dateResult = day + " " + getMonth(month) + " " + year;

        return dateResult;
    }

    private static String getMonth(int i) {
        String month = "";
        switch (i) {
            case 1:
                month = "Января";
                break;
            case 2:
                month = "Февраля";
                break;
            case 3:
                month = "Марта";
                break;
            case 4:
                month = "Апреля";
                break;
            case 5:
                month = "Мая";
                break;
            case 6:
                month = "Июня";
                break;
            case 7:
                month = "Июля";
                break;
            case 8:
                month = "Августа";
                break;
            case 9:
                month = "Сентября";
                break;
            case 10:
                month = "Октября";
                break;
            case 11:
                month = "Ноября";
                break;
            case 12:
                month = "Декабря";
                break;
        }
        return month;
    }
}
