package nakwon.real;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class initialController implements Initializable{
    /* cart_box -> 장바구니 box
     * _grid -> 각 화면이 추가될 grid pane
     * _scroll -> 각 grid pane에 추가되는 상품들의 수에 따라 각 scroll pane의 scroll을 통해 상품들을 확인 가능.
     * cart_scroll -> 장바구니에 추가된 상품들의 수량에 따라 scroll을 통해 확인 가능.
     * result_price -> 장바구니에 추가된 상품들의 총 금액을 나타내주기 위함.
     */
    @FXML
    public HBox cart_box;

    @FXML
    private GridPane drink_grid;

    @FXML
    private ScrollPane drink_scroll;

    @FXML
    private GridPane recommand_grid;

    @FXML
    private ScrollPane reommand_scroll;

    @FXML
    private GridPane set_grid;

    @FXML
    private ScrollPane set_scroll;

    @FXML
    private GridPane side_grid;

    @FXML
    private ScrollPane side_scroll;

    @FXML
    private GridPane single_grid;

    @FXML
    private ScrollPane single_scroll;

    @FXML
    private ScrollPane cart_scroll;

    @FXML
    public Label result_price;

    /*
     * 장바구니에 추가되는 상품들의 정보를 기억하기 위함.
     */
    public List<ClientProductData> order_list = new ArrayList<>();

    /*
     * 다른 클래스에서 initialController의 내용들을 사용해야할 때를 위해 instance를 가져가서 사용할 수 있도록 함.
     */
    public static initialController instance;

    public initialController() {
        instance = this;
    }

    /*
     * 상품 선택 화면이 나옴.
     * 나온 상품 중 하나를 선택하면 해당 상품에 대한 정보를 저장하고 cart에 추가하도록 함.
     */
    public void addToCart(ClientProductData product) {
        try {

            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("order_product.fxml"));
          //  FXMLLoader.load(getClass().getResource("/nakwon/real/StartScreen1.fxml"));
            Parent cart = fxmlloader.load();
            productCtrl ctrl = fxmlloader.getController();
            ctrl.order_setData(product);
            order_list.add(product);
            cart_box.getChildren().add(cart);
            ctrl.updateTotal();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }


    //add btn
//    public void setQuantity() {
//        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
//        prod_spinner.setValueFactory(spin);
//    }
//
//    private double totalP;
//    private double pr;
//
//    public void addBtn() {
//
//        ClientController cForm = new ClientController();
//        cForm.customerID();
//
//        qty = prod_spinner.getValue();
//        String check = "";
//        String checkAvailable = "SELECT status FROM product WHERE prod_id = '"
//                + prodID + "'";
//
//        connect = database.connectDB();
//
//        try {
//            int checkStck = 0;
//            String checkStock = "SELECT stock FROM product WHERE prod_id = '"
//                    + prodID + "'";
//
//            prepare = connect.prepareStatement(checkStock);
//            result = prepare.executeQuery();
//
//            if (result.next()) {
//                checkStck = result.getInt("stock");
//            }
//
//            if(checkStck == 0){
//                String updateStock = "UPDATE product SET prod_name = '"
//                        + prod_name.getText() + "', type = '"
//                        + type + "', stock = 0, price = " + pr
//                        + ", status = 'Unavailable', image = '"
//                        + prod_image + "', date = '"
//                        + prod_date + "' WHERE prod_id = '"
//                        + prodID + "'";
//                prepare = connect.prepareStatement(updateStock);
//                prepare.executeUpdate();
//
//            }
//
//            prepare = connect.prepareStatement(checkAvailable);
//            result = prepare.executeQuery();
//
//            if (result.next()) {
//                check = result.getString("status");
//            }
//
//            if (!check.equals("Available") || qty == 0) {
//                alert = new Alert(AlertType.ERROR);
//                alert.setTitle("Error Message");
//                alert.setHeaderText(null);
//                alert.setContentText("Something Wrong :3");
//                alert.showAndWait();
//            } else {
//
//                if (checkStck < qty) {
//                    alert = new Alert(AlertType.ERROR);
//                    alert.setTitle("Error Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Invalid. This product is Out of stock");
//                    alert.showAndWait();
//                } else {prod_image = prod_image.replace("\\", "\\\\");
//
//                    String insertData = "INSERT INTO customer "
//                            + "(customer_id, prod_id, prod_name, type, quantity, price, date, image, em_username) "
//                            + "VALUES(?,?,?,?,?,?,?,?,?)";
//                    prepare = connect.prepareStatement(insertData);
//                    prepare.setString(1, String.valueOf(data.cID));
//                    prepare.setString(2, prodID);
//                    prepare.setString(3, prod_name.getText());
//                    prepare.setString(4, type);
//                    prepare.setString(5, String.valueOf(qty));
//
//                    totalP = (qty * pr);
//                    prepare.setString(6, String.valueOf(totalP));
//
//                    Date date = new Date();
//                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//                    prepare.setString(7, String.valueOf(sqlDate));
//
//                    prepare.setString(8, prod_image);
//                    prepare.setString(9, data.username);
//
//                    prepare.executeUpdate();
//
//                    int upStock = checkStck - qty;
//
//
//
//                    System.out.println("Date: " + prod_date);
//                    System.out.println("Image: " + prod_image);
//
//                    String updateStock = "UPDATE product SET prod_name = '"
//                            + prod_name.getText() + "', type = '"+ type + "', stock = " + upStock + ", price = " + pr
//                            + ", status = '"
//                            + check + "', image = '"
//                            + prod_image + "', date = '"
//                            + prod_date + "' WHERE prod_id = '"
//                            + prodID + "'";
//
//                    prepare = connect.prepareStatement(updateStock);
//                    prepare.executeUpdate();
//
//                    alert = new Alert(AlertType.INFORMATION);
//                    alert.setTitle("Information Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Successfully Added!");
//                    alert.showAndWait();
//
//                    mForm.menuGetTotal();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

                /* 결제하기 버튼 클릭 시 결재 방법 선택 화면으로 넘어가도록 함.
     * 만약 장바구니에 아무런 상품이 없다면 경고문을 통해 상품 선택 필요를 알림.
     */
    public void Pay_Select(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("payment_select.fxml"));
        Parent root = loader.load();

        int totalPrice = 0;

        for (ClientProductData product : order_list) {
            totalPrice += product.getPrice() * product.getQuantity();
        }

        if(totalPrice == 0) {
            showAlert("상품을 선택해주세요");
        }
        else {
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

    /* 단품 tab에 있는 상품을 선택 시 단품, 일반 세트, 라지 세트를 정렬한 화면을 띄어주도록 함.
     *
     */
    private void Single_ClickEvent(VBox vbox, ClientProductData product) {
        vbox.setOnMouseClicked(event -> {
            try {

                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Single_Upg.fxml"));
                Parent root1 = fxmlloader.load();
                productCtrl ctrl = fxmlloader.getController();
                ctrl.chooseProduct(product);
                String productName = ctrl.chooseName;
                int productPrice = ctrl.choosePrice;

                String single_productImageSrc = "@img/단품/" + productName + ".png";
                String R_set_productImageSrc = "@img/R세트/" + productName + "세트.png";
                String L_set_productImageSrc = "@img/L세트/" + productName + "라지세트.png";

                ClientProductData single_product = new ClientProductData();
                single_product.name = productName;
                single_product.ImageSrc = single_productImageSrc;
                single_product.price = productPrice;

                ClientProductData R_set_product = new ClientProductData();
                R_set_product.name = productName + "세트";
                R_set_product.ImageSrc = R_set_productImageSrc;
                R_set_product.price = productPrice + 3000;

                ClientProductData L_set_product = new ClientProductData();
                L_set_product.name = productName + "라지세트";
                L_set_product.ImageSrc = L_set_productImageSrc;
                L_set_product.price = productPrice + 5000;

                FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("single_burger.fxml"));
                VBox vbox1 = fxmlLoader1.load();
                productCtrl ctrl1 = fxmlLoader1.getController();
                ctrl1.setData(single_product);

                FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("single_burger.fxml"));
                VBox vbox2 = fxmlLoader2.load();
                productCtrl ctrl2 = fxmlLoader2.getController();
                ctrl2.setData(R_set_product);

                FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("single_burger.fxml"));
                VBox vbox3 = fxmlLoader3.load();
                productCtrl ctrl3 = fxmlLoader3.getController();
                ctrl3.setData(L_set_product);

                ctrl.single_box.getChildren().add(vbox1);
                ctrl.R_set_box.getChildren().add(vbox2);
                ctrl.L_set_box.getChildren().add(vbox3);
                ctrl.single_box.setAlignment(Pos.CENTER);
                ctrl.R_set_box.setAlignment(Pos.CENTER);
                ctrl.L_set_box.setAlignment(Pos.CENTER);
                ctrl.L_set_cart = L_set_product;
                ctrl.R_set_cart = R_set_product;
                ctrl.single_cart = single_product;

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();

            }
        });

    }

    /* 세트 tab에 있는 상품을 선택 시 일반 세트, 라지 세트를 정렬한 화면을 띄움.
     *
     */
    private void Set_ClickEvent(VBox vbox, ClientProductData product) {
        vbox.setOnMouseClicked(event -> {
            try {

                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Set_Upg.fxml"));
                Parent root1 = fxmlloader.load();
                productCtrl ctrl = fxmlloader.getController();
                ctrl.chooseProduct(product);
                String productName = ctrl.chooseName;
                int productPrice = ctrl.choosePrice;

                String R_set_productImageSrc = "@img/R세트/" + productName + ".png";
                String L_set_productImageSrc = "@img/L세트/" + productName.substring(0,productName.length()-2) + "라지세트.png";

                ClientProductData R_set_product = new ClientProductData();
                R_set_product.name = productName;
                R_set_product.ImageSrc = R_set_productImageSrc;
                R_set_product.price = productPrice;

                ClientProductData L_set_product = new ClientProductData();
                L_set_product.name = productName.substring(0,productName.length()-2) + "라지세트";
                L_set_product.ImageSrc = L_set_productImageSrc;
                L_set_product.price = productPrice + 2000;

                FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("single_burger.fxml"));
                VBox vbox2 = fxmlLoader2.load();
                productCtrl ctrl2 = fxmlLoader2.getController();
                ctrl2.setData(R_set_product);

                FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("single_burger.fxml"));
                VBox vbox3 = fxmlLoader3.load();
                productCtrl ctrl3 = fxmlLoader3.getController();
                ctrl3.setData(L_set_product);

                ctrl.R_set_box2.getChildren().add(vbox2);
                ctrl.L_set_box2.getChildren().add(vbox3);
                ctrl.R_set_box2.setAlignment(Pos.CENTER);
                ctrl.L_set_box2.setAlignment(Pos.CENTER);
                ctrl.L_set_cart = L_set_product;
                ctrl.R_set_cart = R_set_product;

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();

            }
        });

    }
    /* 사이드 tab에 있는 상품을 선택 시 해당 사이드를 장바구니에 추가 여부를 묻는 화면이 나오도록 함.
     *
     */
    private void Side_ClickEvent(VBox vbox, ClientProductData product) {
        vbox.setOnMouseClicked(event -> {
            try {

                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("choice.fxml"));
                Parent root1 = fxmlloader.load();
                productCtrl ctrl = fxmlloader.getController();
                ctrl.chooseProduct(product);
                String productName = ctrl.chooseName;
                int productPrice = ctrl.choosePrice;
                String productImageSrc = "@img/단품/" + productName + ".png";

                ClientProductData click_product = new ClientProductData();
                click_product.name = productName;
                click_product.ImageSrc = productImageSrc;
                click_product.price = productPrice;

                FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("single_burger.fxml"));
                VBox side = fxmlLoader2.load();
                productCtrl ctrl1 = fxmlLoader2.getController();
                ctrl1.setData(click_product);
                ctrl.product_box.getChildren().add(side);
                ctrl.product_box.setAlignment(Pos.CENTER);

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();

            }
        });

    }

    /* 음료 tab에 있는 상품을 선택 시 해당 음료를 장바구니에 추가 여부를 묻는 화면이 나오도록 함.
     *
     */
    private void Drink_ClickEvent(VBox vbox, ClientProductData product) {
        vbox.setOnMouseClicked(event -> {
            try {

                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("choice.fxml"));
                Parent root1 = fxmlloader.load();
                productCtrl ctrl = fxmlloader.getController();
                ctrl.chooseProduct(product);
                String productName = ctrl.chooseName;
                int productPrice = ctrl.choosePrice;
                String productImageSrc = "@img/단품/" + productName + ".png";

                ClientProductData click_product = new ClientProductData();
                click_product.name = productName;
                click_product.ImageSrc = productImageSrc;
                click_product.price = productPrice;

                FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("single_burger.fxml"));
                VBox drink = fxmlLoader2.load();
                productCtrl ctrl1 = fxmlLoader2.getController();
                ctrl1.setData(click_product);
                ctrl.product_box.getChildren().add(drink);
                ctrl.product_box.setAlignment(Pos.CENTER);

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();

            }
        });

    }

    /* 데이터베이스에 있는 menu table로부터 단품, 세트, 사이트, 음료에 대해서 각각 정보를 가져오도록함.
     * 이후에 각 grid에 상품이 추가되도록 해주고 각 grid에 있는 각 상품들에 click event를 부여함.
     * 그래서 상품 클릭 시 다음 단계 진행.
     */
    private void setMenu(){
        int column = 0;
        int row = 1;

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nakwon",




            "root","1234");
            System.out.println(connection);
            String single_query = "SELECT * FROM menu WHERE category = '단품'";
            PreparedStatement single_statement = connection.prepareStatement(single_query);
            ResultSet single_resultSet = single_statement.executeQuery();

            while (single_resultSet.next()) {
                // 각 상품에 대한 정보 추출

                String productName = single_resultSet.getString("menu_name");
                String productImageSrc = "img/단품/" + productName + ".png";
                System.out.printf(productImageSrc);
                int productPrice = single_resultSet.getInt("price");

                ClientProductData single_product = new ClientProductData();
                single_product.name = productName;
                single_product.ImageSrc = productImageSrc;
                single_product.price = productPrice;

                // FXMLLoader를 사용하여 FXML 파일에서 sb_box 로드
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("single_burger.fxml"));
                VBox sb_box1 = fxmlLoader.load();
                productCtrl ctrl1 = fxmlLoader.getController();
                ctrl1.setData(single_product);

                // 클릭 이벤트 설정
                Single_ClickEvent(sb_box1, single_product);

                // single_grid에 sb_box 추가
                single_grid.add(sb_box1, column++, row);

                if (column == 3) {
                    column = 0;
                    row++;
                }
            }

            // 연결 해제
            single_resultSet.close();
            single_statement.close();

            column = 0;
            row = 1;

            String Rset_query = "SELECT * FROM menu WHERE category = 'R세트'";
            PreparedStatement Rset_statement = connection.prepareStatement(Rset_query);
            ResultSet Rset_resultSet = Rset_statement.executeQuery();

            while (Rset_resultSet.next()) {
                // 각 상품에 대한 정보 추출

                String productName = Rset_resultSet.getString("menu_name");
                String productImageSrc = "@img/R세트/" + productName + ".png";
                int productPrice = Rset_resultSet.getInt("price");

                // ProductData 객체 생성
                ClientProductData product = new ClientProductData();
                product.name = productName;
                product.ImageSrc = productImageSrc;
                product.price = productPrice;

                // FXMLLoader를 사용하여 FXML 파일에서 sb_box 로드
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("single_burger.fxml"));
                VBox sb_box = fxmlLoader.load();
                productCtrl ctrl = fxmlLoader.getController();

                // 상품 데이터 설정
                ctrl.setData(product);

                // 클릭 이벤트 설정
                Set_ClickEvent(sb_box, product);

                // single_grid에 sb_box 추가
                set_grid.add(sb_box, column++, row);

                if (column == 3) {
                    column = 0;
                    row++;
                }
            }
            Rset_resultSet.close();
            Rset_statement.close();

            column = 0;
            row = 1;

            String Side_query = "SELECT * FROM menu WHERE category = '사이드'";
            PreparedStatement Side_statement = connection.prepareStatement(Side_query);
            ResultSet Side_resultSet = Side_statement.executeQuery();

            while (Side_resultSet.next()) {
                // 각 상품에 대한 정보 추출

                String productName = Side_resultSet.getString("menu_name");
                String productImageSrc = "@img/사이드/" + productName + ".png";
                int productPrice = Side_resultSet.getInt("price");

                // ProductData 객체 생성
                ClientProductData product = new ClientProductData();
                product.name = productName;
                product.ImageSrc = productImageSrc;
                product.price = productPrice;

                // FXMLLoader를 사용하여 FXML 파일에서 sb_box 로드
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("single_burger.fxml"));
                VBox sb_box = fxmlLoader.load();
                productCtrl ctrl = fxmlLoader.getController();

                // 상품 데이터 설정
                ctrl.setData(product);

                // 클릭 이벤트 설정
                Side_ClickEvent(sb_box, product);

                // single_grid에 sb_box 추가
                side_grid.add(sb_box, column++, row);

                if (column == 3) {
                    column = 0;
                    row++;
                }
            }
            Side_resultSet.close();
            Side_statement.close();

            column = 0;
            row = 0;
            String Drink_query = "SELECT * FROM menu WHERE category = '음료'";
            PreparedStatement Drink_statement = connection.prepareStatement(Drink_query);
            ResultSet Drink_resultSet = Drink_statement.executeQuery();

            while (Drink_resultSet.next()) {
                // 각 상품에 대한 정보 추출

                String productName = Drink_resultSet.getString("menu_name");
                String productImageSrc = "@img/음료/" + productName + ".png";
                int productPrice = Drink_resultSet.getInt("price");

                // ProductData 객체 생성
                ClientProductData product = new ClientProductData();
                product.name = productName;
                product.ImageSrc = productImageSrc;
                product.price = productPrice;

                // FXMLLoader를 사용하여 FXML 파일에서 sb_box 로드
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("single_burger.fxml"));
                VBox sb_box = fxmlLoader.load();
                productCtrl ctrl = fxmlLoader.getController();

                // 상품 데이터 설정
                ctrl.setData(product);

                // 클릭 이벤트 설정
                Drink_ClickEvent(sb_box, product);

                // single_grid에 sb_box 추가
                drink_grid.add(sb_box, column++, row);

                if (column == 2) {
                    column = 0;
                    row++;
                }
            }
            Drink_resultSet.close();
            Drink_statement.close();
            connection.close();
        }
        catch(SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /* 결재하기 버튼 클릭 시 장바구니에 아무런 상품이 없을 때 경고문을 띄우기 위함.
     *
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("경고");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setMenu();

    }
}
