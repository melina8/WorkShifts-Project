import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Main extends Application {


    ComboBox<String> comboMonth;
    Button btnAddEmp, btnAddWorkShift, btnProduce;


    @Override
    public void start(Stage stage) {


        Connection conn = null;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/workshift",
                    "javamelina2", "123456");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Connection finalConn = conn;


        btnAddEmp = new Button();
        btnAddEmp.setText("Employees");
        btnAddEmp.setPrefWidth(100);



        btnAddWorkShift = new Button();
        btnAddWorkShift.setText("Work Shifts");
        btnAddWorkShift.setPrefWidth(100);


       HBox hbox = new HBox(btnAddEmp, btnAddWorkShift);
       hbox.setSpacing(80);
       hbox.setPrefHeight(300);
       hbox.setAlignment(Pos.CENTER);



       comboMonth = new ComboBox<String>();
       comboMonth.getItems().addAll("January", "February", "Mars", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        comboMonth.setPrefWidth(120);
        comboMonth.setValue("Select Month");


        btnProduce = new Button();
        btnProduce.setText("Get Programme");

       HBox hbox2 = new HBox (comboMonth, btnProduce);
       hbox2.setSpacing(30);
       hbox2.setAlignment(Pos.CENTER);

       VBox vbox = new VBox(hbox, hbox2);

        // the scene
        Scene scene = new Scene(vbox);
        scene.heightProperty().addListener(
                (observable, oldValue, newValue) -> {
                    vbox.setPrefHeight(scene.getHeight());

                });
        scene.widthProperty().addListener(
                (observable, oldValue, newValue) -> {
                    vbox.setPrefWidth(scene.getWidth());
                });

        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Work Shifts");
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - 600) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - 500) / 2);
        stage.setWidth(600);
        stage.setHeight(500);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();



        btnAddEmp.setOnAction(e -> {

            EmployeeForm ef = new EmployeeForm(finalConn);
        });

        btnAddWorkShift.setOnAction(e -> {
            WorkShiftForm wsf = new WorkShiftForm(finalConn);
        });

        btnProduce.setOnAction(e -> {

            if (comboMonth.getValue().equals("Select Month")){
                MessageBoxOK mb = new MessageBoxOK("Please select a month from the list", "WARNING!");
                System.out.println("Please select a month from the list");
            }
            else {

                ProgramForm pf = new ProgramForm(finalConn, comboMonth.getValue());
            }
        });

    }
}


