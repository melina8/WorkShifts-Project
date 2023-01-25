import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class TableViewProgram {
    private LocalDate the_day;
    private String ws;
    private String emp_id;
    private String name;
    private String surname;


    public TableViewProgram() {
    }

    public TableViewProgram(LocalDate the_day, String ws, String emp_id, String name, String surname) {
        this.the_day = the_day;
        this.ws = ws;
        this.emp_id = emp_id;
        this.name = name;
        this.surname = surname;
    }

    public LocalDate getThe_day() {
        return the_day;
    }

    public void setThe_day(LocalDate the_day) {
        this.the_day = the_day;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "TableViewProgram{" +
                "the_day=" + the_day +
                ", ws='" + ws + '\'' +
                ", emp_id='" + emp_id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}


