module nakwon.real {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens nakwon.real to javafx.fxml;
    exports nakwon.real;
}