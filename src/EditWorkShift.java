import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class EditWorkShift {

    Stage stage;
    Label lblTitle, lblFromHour, lblToHour, lblEmpNo;
    TextField txtEmpNo;

    ComboBox<String> comboFromHour, comboFromMin, comboToHour, comboToMin;

    ListView<Employee> listEmployees;
    Button
    btnUpdate, btnCancel;
    HBox hBox1, hBox2, hBox3;
    String state;

    ArrayList<String> numbers = new ArrayList<>();
    ArrayList<String> numbers2 = new ArrayList<>();

    Connection connection;

    public EditWorkShift(Connection conn, LocalTime from_hour, LocalTime to_hour, int noOfEmp) {
        connection = conn;

        WorkShifts ws = new WorkShifts(from_hour, to_hour, noOfEmp);

        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                numbers.add("0" + i);
            } else {
                numbers.add(String.valueOf(i));
            }
        }

        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                numbers2.add("0" + i);
            } else {
                numbers2.add(String.valueOf(i));
            }
        }


        //grid
        GridPane grid = new GridPane();
        grid.setHgap(11);
        grid.setVgap(13);


        //select work shift
        lblFromHour = new Label("From: ");
        comboFromHour = new ComboBox<String>();
        comboFromHour.setPromptText("Hour");
        comboFromHour.getItems().addAll(numbers);
        comboFromHour.setPrefWidth(85);

        comboFromMin = new ComboBox<String>();
        comboFromMin.setPromptText("Minute");
        comboFromMin.getItems().addAll(numbers2);
        comboFromMin.setPrefWidth(85);


        lblToHour = new Label("To: ");
        comboToHour = new ComboBox<String>();
        comboToHour.setPromptText("Hour");
        comboToHour.getItems().addAll(numbers);
        comboToHour.setPrefWidth(85);

        comboToMin = new ComboBox<String>();
        comboToMin.setPromptText("Minute");
        comboToMin.getItems().addAll(numbers2);
        comboToMin.setPrefWidth(85);


        hBox1 = new HBox();
        hBox1.getChildren().addAll(lblFromHour, comboFromHour, comboFromMin, lblToHour, comboToHour, comboToMin);
        hBox1.setSpacing(10);
        hBox1.setMinHeight(100);
        hBox1.setAlignment(Pos.CENTER);

        lblEmpNo = new Label("Set min number of employees");
        txtEmpNo = new TextField();
        txtEmpNo.setPrefWidth(50);


        btnUpdate = new Button("Update");

        btnCancel = new Button("Cancel");


        hBox2 = new HBox();
        hBox2.getChildren().addAll(lblEmpNo, txtEmpNo);
        hBox2.setSpacing(10);
        hBox2.setAlignment(Pos.CENTER);

        hBox3 = new HBox();
        hBox3.getChildren().addAll(btnCancel, btnUpdate);
        hBox3.setSpacing(10);
        hBox3.setAlignment(Pos.CENTER);
        hBox3.setMinHeight(100);


        btnCancel.setOnAction(e -> {
            loadForm(ws);
        });

        btnUpdate.setOnAction(e -> {
            MessageBoxCancelYes m = new MessageBoxCancelYes("Are you sure you want to make changes?","Warning!");
            boolean response = m.getResponse();
            if (response) {

                //check if all fields are filled in
                if (comboFromHour.getValue() == null) {
                    MessageBoxOK mb = new MessageBoxOK("Please select hour.", "WARNING!");
                    System.out.println("Please select hour.");
                } else if (comboFromMin.getValue() == null) {
                    MessageBoxOK mb = new MessageBoxOK("Please select minutes.", "WARNING!");
                    System.out.println("PPlease select minutes.");
                } else if (comboToHour.getValue() == null) {
                    MessageBoxOK mb = new MessageBoxOK("Please select hour.", "WARNING!");
                    System.out.println("Please select hour.");
                } else if (comboToMin.getValue() == null) {
                    MessageBoxOK mb = new MessageBoxOK("Please select minutes.", "WARNING!");
                    System.out.println("Please select minutes.");
                } else if (txtEmpNo.getText().isEmpty()) {
                    MessageBoxOK mb = new MessageBoxOK("Please enter min number of employees.", "WARNING!");
                    System.out.println("Please enter min number of employees.");
                }

                //everything is ok! update workshift
                else {
                    LocalTime old1 = ws.getFromHour();
                    System.out.println("old1: " + old1);
                    LocalTime old2 = ws.getToHour();
                    System.out.println("old2: " + old2);

                    System.out.println("Validate: OK");
                    String fh = comboFromHour.getValue();
                    String fm = comboFromMin.getValue();
                    String th = comboToHour.getValue();
                    String tm = comboToMin.getValue();
                    LocalTime fromHour = LocalTime.of(Integer.parseInt(fh), Integer.parseInt(fm));
                    LocalTime toHour = LocalTime.of(Integer.parseInt(th), Integer.parseInt(tm));
                    int noEmp = Integer.parseInt(txtEmpNo.getText());

                    System.out.println("from: " + fromHour + " to: " + toHour + " emp: " + noEmp);

                    WorkShifts wsh = new WorkShifts();
                    wsh.setFromHour(fromHour);
                    wsh.setToHour(toHour);
                    wsh.setNoOfEmp(noEmp);

                    try {
                        String updQuery = "UPDATE workshifts " +
                                " SET from_hour=?, to_hour=?, noOfEmp=?" +
                                " WHERE from_hour=? AND to_hour=? ";

                        PreparedStatement updStmt = conn.prepareStatement(updQuery);
                        updStmt.setObject(1, wsh.getFromHour());
                        updStmt.setObject(2, wsh.getToHour());
                        updStmt.setInt(3, wsh.getNoOfEmp());
                        updStmt.setObject(4, old1);
                        updStmt.setObject(5, old2);
                        updStmt.executeUpdate();

                        updStmt.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    MessageBoxOK mb = new MessageBoxOK("Workshift updated successfully.", "INFORMATION!");
                    stage.close();
                }
            }
            else {  //response is no
                loadForm(ws);
            }
        });


        VBox pane = new VBox();
        pane.getChildren().addAll(hBox1, hBox2, hBox3);
        pane.setPadding((new Insets(10, 10, 10, 10)));
        pane.setAlignment(Pos.CENTER);

        grid.getChildren().add(pane);

        // load values of the selected workshift
        loadForm(ws);

        // the scene
        Scene scene = new Scene(grid);
        scene.heightProperty().addListener(
                (observable, oldValue, newValue) -> {
                    grid.setPrefHeight(scene.getHeight());
                    grid.setPrefHeight(scene.getHeight());
                });
        scene.widthProperty().addListener(
                (observable, oldValue, newValue) -> {
                    grid.setPrefWidth(scene.getWidth());
                });

        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Edit WorkShift");
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - 500) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - 300) / 2);
        stage.setWidth(500);
        stage.setHeight(300);
        stage.initModality(Modality.APPLICATION_MODAL);


        stage.show();
    }

    public void loadForm(WorkShifts ws){
        comboFromHour.setValue(ws.getFromHour().toString().substring(0,2));
        comboFromMin.setValue(ws.getFromHour().toString().substring(3,5));
        comboToHour.setValue(ws.getToHour().toString().substring(0,2));
        comboToMin.setValue(ws.getToHour().toString().substring(3,5));
        txtEmpNo.setText(String.valueOf(ws.getNoOfEmp()));
    }
}