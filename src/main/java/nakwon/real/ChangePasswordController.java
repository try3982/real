package nakwon.real;

/**
 * Sample Skeleton for 'Login.fxml' Controller Class
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChangePasswordController extends FindPasswordController {


    @FXML
    private Button fp_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    void changePassBtn(ActionEvent event) {

    }

    @FXML
    void fp_backtologinBtn(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) fp_back.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }catch(Exception e) {
            e.printStackTrace();

        }
    }

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;



    public void changePassBtn() {
        if (np_newPassword.getText().isEmpty() || np_confirmPassword.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("모든 필드를 채워주세요.");
            alert.showAndWait();


        } else {

            if (np_newPassword.getText().equals(np_confirmPassword.getText())) {
                String getDate = "SELECT date FROM employee WHERE username = '"
                        + fp_username.getText() + "'";

                connect = database.connectDB();

                try {

                    prepare = connect.prepareStatement(getDate);
                    result = prepare.executeQuery();

                    String date = "";
                    if (result.next()) {
                        date = result.getString("date");
                    }

                    String updatePass = "UPDATE employee SET password = '"
                            + np_newPassword.getText() + ""
                            + "' WHERE username = '"
                            + fp_username.getText() + "'";

                    prepare = connect.prepareStatement(updatePass);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("비밀번호가 성공적으로 변경되었습니다.");
                    alert.showAndWait();


                    np_confirmPassword.setText("");
                    np_newPassword.setText("");

                    fp_username.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("비밀번호가 일치하지 않습니다.");
                alert.showAndWait();
            }
        }


    }

}
