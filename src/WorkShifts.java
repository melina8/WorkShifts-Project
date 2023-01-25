//import javax.swing.text.html.ListView;
import javafx.scene.control.ListView;
import java.io.Serializable;
import java.time.LocalTime;

public class WorkShifts implements Serializable {


    private LocalTime fromHour;
    private LocalTime toHour;
    private int noOfEmp;



    public WorkShifts() { }


    public WorkShifts(LocalTime fromHour, LocalTime toHour, int noOfEmp) {
        this.fromHour = fromHour;
        this.toHour = toHour;
        this.noOfEmp = noOfEmp;
    }

    public LocalTime getFromHour() {
        return fromHour;
    }

    public void setFromHour(LocalTime fromHour) {
        this.fromHour = fromHour;
    }

    public LocalTime getToHour() {
        return toHour;
    }

    public void setToHour(LocalTime toHour) {
        this.toHour = toHour;
    }

    public int getNoOfEmp() {
        return noOfEmp;
    }

    public void setNoOfEmp(int noOfEmp) {
        this.noOfEmp = noOfEmp;
    }

    @Override
    public String toString() {
        return "WorkShifts{" +
                "fromHour=" + fromHour +
                ", toHour=" + toHour +
                ", noOfEmp=" + noOfEmp +
                '}';
    }
}
