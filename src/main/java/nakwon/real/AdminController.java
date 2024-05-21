package nakwon.real;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController  implements Initializable {

    @FXML
    private AnchorPane dashboard_form;


    @FXML
    private AnchorPane admin_form;
    @FXML
    private Button inventory_clearbtn;
    @FXML
    private Button inventory_addBtn;
    @FXML
    private Button inventory_deletebtn;
    @FXML
    private Button inventory_importbtn;
    @FXML
    private Button inventory_udatebtn;




    @FXML
    private Button customerbtn;

    @FXML
    private Button dashboardbtn;
    @FXML
    private AnchorPane info_form;


    @FXML
    private AnchorPane customer_form;

    @FXML
    private ComboBox<?> inventory_category;


    @FXML
    private Button inventoryClearbtn;

    @FXML
    private TableColumn<productDatabwj, String> inventory_col_category;

    @FXML
    private TableColumn<productDatabwj, String> inventory_col_date;

    @FXML
    private ImageView inventory_col_imageView;

    @FXML
    private TableColumn<productDatabwj, String> inventory_col_menuId;

    @FXML
    private TableColumn<productDatabwj, String> inventory_col_menuName;

    @FXML
    private TableColumn<productDatabwj, String> inventory_col_price;

    @FXML
    private TableColumn<productDatabwj, String> inventory_col_status;

    @FXML
    private TableColumn<productDatabwj, String> inventory_col_stock;




    @FXML
    private AnchorPane inventory_form;

    @FXML
    private AnchorPane customers_form;



    @FXML
    private TextField inventory_menuId;

    @FXML
    private TextField inventory_price;

    @FXML
    private ComboBox<?> inventory_status;

    @FXML
    private TextField inventory_stock;

    @FXML
    private TableView<productDatabwj> inventory_tableView;



    @FXML
    private Button inventorybtn;

    @FXML
    private TextField invetory_menuName;

    @FXML
    private Button logoutbtn;

    @FXML
    private Button menutbn;

    @FXML
    private Label username;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Image image;


    private Alert alert;


    private String[] categoryList = {"버거단품", "R세트", "L세트", "음료", "사이드"};

    //카테고리 리스트
    public void inventoryCatetoryList() {
        List<String> categoryL = new ArrayList<>();

        for (String data : categoryList) {
            categoryL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(categoryL);
        inventory_category.setItems(listData);

    }


    



    private String[] statusList = {"판매 가능", "판매 불가"};

    public void inventoryStatusList() {

        List<String> statusL = new ArrayList<>();

        for (String data : statusList) {
            statusL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(statusL);
        inventory_status.setItems(listData);

    }

    public ObservableList<productDatabwj> inventoryDataList() {
        ObservableList<productDatabwj> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM menu";
        Connection connect = null;
        PreparedStatement prepare = null;
        ResultSet result = null;

        try {
            connect = database.connectDB();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                int id = result.getInt("menu_id");
                String menuName = result.getString("menu_name");
                String type = result.getString("category");
                int stock = result.getInt("stock");
                int price = result.getInt("price");
                String status = result.getString("status");
                String image = result.getString("image");
                Date date = result.getDate("date");

                productDatabwj prodDatabwj = new productDatabwj(id, menuName, type, stock, price, status, image, date);
                listData.add(prodDatabwj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 연결 및 리소스 해제
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listData;
    }

    // 테이블뷰에 데이터 뛰우기
    private ObservableList<productDatabwj> inventoryListData;

    public void inventoryShowData() {
        inventoryListData = inventoryDataList();

        inventory_col_menuId.setCellValueFactory(new PropertyValueFactory<>("menuId"));
        inventory_col_menuName.setCellValueFactory(new PropertyValueFactory<>("menuName"));
        inventory_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_tableView.setItems(inventoryListData);

    }

    public void inventorySelectData() {

        productDatabwj prodDatabwj = inventory_tableView.getSelectionModel().getSelectedItem();
        int num = inventory_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        //inventory_menuId.setText(String.valueOf(menuId));
        inventory_col_menuName.setText(prodDatabwj.getMenuName());
        inventory_stock.setText(String.valueOf(prodDatabwj.getStock()));
        inventory_price.setText(String.valueOf(prodDatabwj.getPrice()));

        data.path = prodDatabwj.getImage();

        data.path = "File:" + prodDatabwj.getImage();
        data.date = String.valueOf(prodDatabwj.getDate());
        data.id = prodDatabwj.getMenuId();

        image = new Image(data.path, 120, 127, false, true);
        inventory_col_imageView.setImage(image);
    }


    public void logout() {
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("*** 경고 ***");
            alert.setContentText("로그아웃 하시겠습니까?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {


                logoutbtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("낙원 키오스크");

                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 환영합니다. OO님
    public void displayUsername() {
        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(STR."\{user} 님");

    }

    public void inventoryClearBtn() {

        inventory_menuId.setText("");
        invetory_menuName.setText("");
        inventory_category.getSelectionModel().clearSelection();
        inventory_stock.setText("");
        inventory_price.setText("");
        inventory_status.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        inventory_col_imageView.setImage(null);
    }

    //메뉴 추가
    public void inventoryAddBtn() {
        if (inventory_menuId.getText().isEmpty()
                || invetory_menuName.getText().isEmpty()
                || inventory_category.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.path == null) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("모든 필드를 채워주세요");
            alert.showAndWait();

        } else {

            // 메뉴 아이디 확인
            String checkProdID = "SELECT menu_id FROM menu WHERE menu_id = '"
                    + inventory_menuId.getText() + "'";

            connect = database.connectDB();
            try {

                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(inventory_menuId.getText() + " 이미 존재하는 메뉴입니다.");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO menu "
                            + "(menu_id, menu_name, category, stock, price, status, image, date) "
                            + "VALUES(?,?,?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, inventory_menuId.getText());
                    prepare.setString(2, invetory_menuName.getText());
                    prepare.setString(3, (String) inventory_category.getSelectionModel().getSelectedItem());
                    prepare.setString(4, inventory_stock.getText());
                    prepare.setString(5, inventory_price.getText());
                    prepare.setString(6, (String) inventory_status.getSelectionModel().getSelectedItem());

                    String path = data.path;
                    path = path.replace("\\", "\\\\");

                    prepare.setString(7, path);


                    java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());

                    prepare.setString(8, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("메뉴가 성공적으로 등록되었습니다.!");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryClearBtn();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }




    private int cID;

    public void customerId() {

        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                cID = result.getInt("MAX(customer_id)");
            }

            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }

            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }

            data.cID = cID;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public void dashboardIncomeChart() {
//        dashboard_incomeChart.getData().clear();
//
//        String sql = "SELECT date, SUM(total) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
//        connect = database.connectDB();
//        XYChart.Series chart = new XYChart.Series();
//        try {
//            prepare = connect.prepareStatement(sql);
//            result = prepare.executeQuery();
//
//            while (result.next()) {
//                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getFloat(2)));
//            }
//
//            dashboard_incomeChart.getData().add(chart);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//public void dashboardCustomerChart(){
//    dashboard_CustomerChart.getData().clear();
//
//    String sql = "SELECT date, COUNT(id) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
//    connect = database.connectDB();
//    XYChart.Series chart = new XYChart.Series();
//    try {
//        prepare = connect.prepareStatement(sql);
//        result = prepare.executeQuery();
//
//        while (result.next()) {
//            chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
//        }
//
//        dashboard_CustomerChart.getData().add(chart);
//
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}



//    public void setQuantity() {
//        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
//        prod_spinner.setValueFactory(spin);
//    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();

        inventoryShowData();
        inventoryStatusList();
        inventoryCatetoryList();

//        dashboardDisplayNC();
//        dashboardDisplayTI();
//        dashboardTotalI();
//        dashboardNSP();
//        dashboardIncomeChart();
//        dashboardCustomerChart();
//

//        menuDisplayCard();
//        menuGetOrder();
//        menuDisplayTotal();
//        menuShowOrderData();
//
//        customersShowData();

    }


    public void switchForm(ActionEvent event) {


        if (event.getSource() == dashboardbtn) {
            inventory_form.setVisible(true);
            info_form.setVisible(false);
            customers_form.setVisible(false);


        } else if (event.getSource() == inventorybtn) {
            inventory_form.setVisible(false);
            info_form.setVisible(true);
            customers_form.setVisible(false);

            inventoryStatusList();
            inventoryShowData();

        } else if (event.getSource() == customerbtn) {
            inventory_form.setVisible(false);
            info_form.setVisible(false);
            customers_form.setVisible(true);


        }

    }


    public void inventoryImportBtn( ) {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*.png", "*.jpg"));

        File file = openFile.showOpenDialog(admin_form.getScene().getWindow());

        if (file != null) {
            data.path = file.getAbsolutePath();
            Image image = new Image(file.toURI().toString(), 156, 121, false, true);
            inventory_col_imageView.setImage(image);
        }

        
    }

    public void inventoryUpdateBtn() {

        if (inventory_menuId.getText().isEmpty()
                || invetory_menuName.getText().isEmpty()
                || inventory_category.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.path == null || data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("모든 필드를 채워주세요.");
            alert.showAndWait();

        } else {

            String path = data.path;
            path = path.replace("\\", "\\\\");

            String updateData = "UPDATE menu SET "
                    + "menu_id = '" + inventory_menuId.getText() + "', menu_name = '"
                    + invetory_menuName.getText() + "', catetory = '"
                    + inventory_category.getSelectionModel().getSelectedItem() + "', stock = '"
                    + inventory_stock.getText() + "', price = '"
                    + inventory_price.getText() + "', status = '"
                    + inventory_status.getSelectionModel().getSelectedItem() + "', image = '"
                    + path + "', date = '"
                    + data.date + "' WHERE id = " + data.id;

            connect = database.connectDB();
            try {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("메뉴를 수정하시겠습니까 " + inventory_menuId.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("성공적으로 수정되었습니다.!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("취소되었습니다.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inventoryDeleteBtn( ) {
        if (data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("모든 필드를 채워주세요.");
            alert.showAndWait();

        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("*** " + inventory_menuId.getText() + "을 삭제하시겠습니까? ***");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM menu WHERE menu_id = " + data.id;
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        }

    }
}


