package nakwon.real;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ClientController {
    public static final String WON = "원";
    @FXML
    public void startScreen(MouseEvent mouseEvent) {
        try {

            // Load the first screen
            Parent startScreenRoot = FXMLLoader.load(getClass().getResource("MainScreen1.fxml"));
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(startScreenRoot);
            stage.setScene(scene);
            stage.show();

//            // Load the admin screen in a new window
//            Parent adminRoot = FXMLLoader.load(getClass().getResource("admin.fxml"));
//            Stage adminStage = new Stage();
//            Scene adminScene = new Scene(adminRoot);
//            adminStage.setScene(adminScene);
//            adminStage.setTitle("Admin Screen");
//            adminStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
