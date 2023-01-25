import java.io.Serializable;
import java.time.LocalDate;

public class ProgramPairs implements Serializable {
    private LocalDate the_day;
    private String ws;
    private String emp_id;



    public ProgramPairs(){
    }

    public ProgramPairs(LocalDate the_day,  String ws, String emp_id) {
       this.the_day = the_day;
       this.ws = ws;
       this.emp_id = emp_id;
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
        return "ProgramPairs{" +
                "the_day=" + the_day +
                ", ws='" + ws + '\'' +
                ", emp_id='" + emp_id + '\'' +
                '}';
    }
}
