import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.util.converter.IntegerStringConverter;
import java.sql.*;
import java.time.LocalDate;


public class AllDaysOffForm {
    Stage stage;


    HBox hboxTable, hboxItems;
    TableView<DaysOff> tableView;


    TableColumn<DaysOff, String> empIdColumn;
    TableColumn<DaysOff, LocalDate> dayOff1Column;
    TableColumn<DaysOff, LocalDate> dayOff2Column;


    Connection connection;
    String emp_id;


    ObservableList<DaysOff> allDaysOffList = FXCollections.observableArrayList();

    public AllDaysOffForm(Connection conn, String emp_id) {

        connection = conn;
        String theId = emp_id;


        //tableView
        empIdColumn = new TableColumn<>("Employee Id");
        empIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        empIdColumn.setMinWidth(100);

        dayOff1Column = new TableColumn<>("First dayOff");
        dayOff1Column.setCellValueFactory(new PropertyValueFactory<>("dayOff1"));
        dayOff1Column.setMinWidth(150);

        dayOff2Column = new TableColumn<>("Second dayOff ");
        dayOff2Column.setCellValueFactory(new PropertyValueFactory<>("dayOff2"));
        dayOff2Column.setMinWidth(150);


        tableView = new TableView<>();
        tableView.getColumns().add(empIdColumn);
        tableView.getColumns().add(dayOff1Column);
        tableView.getColumns().add(dayOff2Column);


        empIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dayOff1Column.setCellValueFactory(new PropertyValueFactory<>("dayOff1"));
        dayOff2Column.setCellValueFactory(new PropertyValueFactory<>("dayOff2"));


        empIdColumn.setEditable(true);
        dayOff1Column.setEditable(true);
        dayOff2Column.setEditable(true);

        tableView.setPrefWidth(400);
        tableView.setPrefHeight(700);
        tableView.setEditable(true);

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        hboxTable = new HBox();
        hboxTable.getChildren().addAll(tableView);
        hboxTable.setPadding(new Insets(50, 50, 50, 50));
        hboxTable.setAlignment(Pos.CENTER);


        VBox pane = new VBox();
        pane.getChildren().addAll(hboxTable);

        // the scene
        Scene scene = new Scene(pane);
        scene.heightProperty().addListener(
                (observable, oldValue, newValue) -> {
                    pane.setPrefHeight(scene.getHeight());

                });
        scene.widthProperty().addListener(
                (observable, oldValue, newValue) -> {
                    pane.setPrefWidth(scene.getWidth());
                });

        //the stage
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("All Days Off");
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - 700) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - 800) / 2);
        stage.setWidth(700);
        stage.setHeight(800);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMaximized(false);
        stage.show();

        Statement stmt = null;

        try {
            CallableStatement callstmt = connection.prepareCall("call loadAllDaysOff(?)");
            callstmt.setString(1, theId);

            ResultSet newresult = callstmt.executeQuery();

            while (newresult.next()) {
                allDaysOffList.add(new DaysOff(
                        newresult.getString("emp_id"),
                        newresult.getDate("dayOff1").toLocalDate(),
                        newresult.getDate("dayOff2").toLocalDate()));
                        tableView.setItems(allDaysOffList);
            }
            newresult.close();
            callstmt.close();
        } catch (SQLException d) {
            d.printStackTrace();
        }


    }
}

