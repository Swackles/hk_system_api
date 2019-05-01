package hkSystem.Sahtel;

import hkSystem.Regex.Find;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Tunniplaan {

    public static ArrayList<Klass> Parse(String input) throws IOException {
        Find find = new Find(input, "<table id=\"timetable\"><tr><th>Aeg</th><th>Õppeaine nimetus</th><th>Maht</th><th>Õppejõud</th><th>Koht</th></tr><tr>", "</tr></table>");
        find.Parse();

        Klass klass = null;
        ArrayList<Klass> klasses = new ArrayList<>();

        for (String row : find.getResult().replaceAll("</tr><tr.*?>", "<<__new-row__>>").split("<<__new-row__>>")) {

            String result;
            find = new Find(row, "<td class=\"", "\" colspan=\"5\">");
            try {
                find.Parse();
                result = find.getResult();
            } catch (Exception e) {
                if(row.contains("<td></td><td colspan=\"4\">Kommentaar: ")) {
                    klasses.get(klasses.size() - 1).setDescription(row.replaceAll("<td></td><td colspan=\"4\">Kommentaar: (.*?)</td>", "$1"));
                    continue;
                }
                result = "default";
            }
            switch (result) {
                case "weekline":
                    break;

                case "dateline":
                    find = new Find(row ,"<td class=\"dateline\" colspan=\"5\">", "</td>");
                    find.Parse();
                    String dateString = find.getResult();

                    dateString = dateString.replace(".", "").replace(",", "");

                    String[] dates = dateString.split(" ");
                    String[] months = { "jaanuar", "veebruar", "märts", "april", "mai", "juuni", "juuli", "august", "september", "oktoober", "november", "detsember" };

                    klass = new Klass();
                    klass.setDate(Integer.parseInt(dates[3]), Arrays.asList(months).indexOf(dates[2]), Integer.parseInt(dates[1]));
                    break;

                case "classline":
                    find = new Find(row, "<td class=\"classline\" colspan=\"5\">","</td>");
                    find.Parse();
                    String values = find.getResult();

                    find = new Find(values, "", " [0-9]. kursus");
                    find.Parse();
                    klass.setGroup(find.getResult());

                    find = new Find(values, klass.getGroup() + " ", ". kursus");
                    find.Parse();
                    klass.setCourse(Byte.parseByte(find.getResult()));
                    break;

                default:
                    String column = row.replaceAll("</td><td.*?>", "<<__new-row__>>");
                    column = column.replaceAll("<br>", ", ");
                    column = column.replaceAll("<br>", "");
                    column = column.replaceAll("<td.*?>(.*?)</td.*?>", "$1");
                    column = column.replaceAll(" \\[<span class=\"hover\" value=\"\"></span>]", "");

                    String[] dataRows = column.split("<<__new-row__>>");
                    String[] startEnd = dataRows[0].split(" - ");

                    String[] time = startEnd[0].split(":");
                    klass.setClassStart(Integer.parseInt(time[0]), Integer.parseInt(time[1]), 0);

                    time = startEnd[1].split(":");
                    klass.setClassEnd(Integer.parseInt(time[0]), Integer.parseInt(time[1]), 0);

                    String[] nameCode = dataRows[1].replaceAll("(.*?) \\((.*?)\\)", "$1<<__new-row__>>$2").split("<<__new-row__>>");
                    klass.setName(nameCode[0]);
                    klass.setCode(nameCode[1]);
                    klass.setAmount(Byte.parseByte(dataRows[2]));
                    klass.setTeacher(dataRows[3].replaceAll("(.*?), ", "$1"));

                    if(dataRows.length < 5) {
                        klass.setRoom((short)0);
                    } else {
                        klass.setRoom(Short.parseShort(dataRows[4].replaceAll("[A-Üa-ü ]+\\w ([0-9]+\\w)", "$1")));
                    }
                    klasses.add(klass);
                    break;
            }
        }
        return klasses;
    }
}
