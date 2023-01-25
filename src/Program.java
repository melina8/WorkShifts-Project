import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class Program {
    private LocalDate the_day;
    private String ws;
    private String emp_id;


    public Program() {
    }

    public Program(LocalDate theDay, String workShift, String empId) {
        this.the_day = theDay;
        this.ws = workShift;
        this.emp_id = empId;
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

    @Override
    public String toString() {
        return "Program{" +
                "the_day=" + the_day +
                ", ws='" + ws + '\'' +
                ", emp_id='" + emp_id + '\'' +
                '}';
    }
}


