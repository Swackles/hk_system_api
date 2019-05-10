package sahtelPlusPlus.Sahtel;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class API {
    private DateFormat DateFormat;
    private String DateStart;
    private String DateEnd;
    private String Checked;
    private String Classes;
    private String Subjects;
    private String Teachers;
    private String Rooms;
    private String Search;

    public API() {
        this.DateFormat = new SimpleDateFormat("dd.MM.yyyy");

        Calendar calendar = Calendar.getInstance();
        this.DateStart = DateFormat.format(calendar.getTime());

        calendar.add(Calendar.MONTH, 6);
        this.DateEnd = DateFormat.format(calendar.getTime());

        this.Checked = "0";
        this.Classes = "";
        this.Subjects = "";
        this.Teachers = "";
        this.Rooms = "";
        this.Search = "";
    }

    public ArrayList<Teacher> getTeacher() throws IOException {
        List<NameValuePair> arguments = new ArrayList<>();
        arguments.add(new BasicNameValuePair("search", Search));

        String result = connectServer("http://start.hk.tlu.ee/sahtelbeta/tunniplaan/php/get_teacher_list.php", arguments);

        return Teacher.Parse(result);
    }

    public ArrayList<Subject> getSubject() throws IOException {
        List<NameValuePair> arguments = new ArrayList<>();
        arguments.add(new BasicNameValuePair("search", Search));

        String result = connectServer("http://start.hk.tlu.ee/sahtelbeta/tunniplaan/php/get_subject_list.php", arguments);

        return Subject.Parse(result);
    }

    public ArrayList<Klass> getTunniplaan() throws IOException {

        List<NameValuePair> arguments = new ArrayList<>();
        arguments.add(new BasicNameValuePair("start", DateStart));
        arguments.add(new BasicNameValuePair("end", DateEnd));
        arguments.add(new BasicNameValuePair("checked", Checked));
        arguments.add(new BasicNameValuePair("classes", Classes));
        arguments.add(new BasicNameValuePair("subjects", Subjects));
        arguments.add(new BasicNameValuePair("teachers", Teachers));
        arguments.add(new BasicNameValuePair("rooms", Rooms));

        String response = connectServer("http://start.hk.tlu.ee/sahtelbeta/tunniplaan/tunniplaan.php", arguments);

        if (response.contains("Ei leidnud ühtegi loengut")) { return new ArrayList<>(); }

        return  Tunniplaan.Parse(response);
    }

    private String connectServer(String url, List<NameValuePair> arguments) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new UrlEncodedFormEntity(arguments));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        return EntityUtils.toString(httpResponse.getEntity());
    }

    public String getDateStart() { return DateStart; }
    public void setDateStart(Calendar calendar) { DateStart = DateFormat.format(calendar); }

    public String getDateEnd() { return DateEnd; }
    public void  setDateEnd(Calendar calendar) { DateEnd = DateFormat.format(calendar); }

    public boolean getChecked() { return Checked == "1"; }
    public void setChecked(boolean checked) { if (checked) { Checked = "1"; } else { Checked = "0"; } }

    public ArrayList<String> getClasses() { return (ArrayList<String>)Arrays.asList(Classes.split("-")); }
    public void setClasses(ArrayList<String> classes) {
        for (String item:classes) {
            if (Classes == "") {
                Classes = item;
            } else {
                Classes += "-"+item;
            }
        }
    }

    public ArrayList<String> getSubjects() { return (ArrayList<String>)Arrays.asList(Subjects.split("-")); }
    public void setSubjects(ArrayList<String> subjects) {
        for (String item:subjects) {
            if (Subjects == "") {
                Subjects = item;
            } else {
                Subjects += "-"+item;
            }
        }
    }

    public ArrayList<String> getTeachers() { return (ArrayList<String>)Arrays.asList(Teachers.split("-")); }
    public void setTeachers(ArrayList<String> teachers) {
        for (String item:teachers) {
            if (Teachers == "") {
                Teachers = item;
            } else {
                Teachers += "-"+item;
            }
        }
    }

    public ArrayList<String> getRooms() { return (ArrayList<String>)Arrays.asList(Rooms.split("-")); }
    public void setRooms(ArrayList<String> rooms) {
        for (String item:rooms) {
            if (Rooms == "") {
                Rooms = item;
            } else {
                Rooms += "-"+item;
            }
        }
    }

    public String getSearch() { return Search; }
    public void setSearch(String search) { Search = search; }
}
