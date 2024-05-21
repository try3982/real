package nakwon.real;

import java.sql.Date;

public class productDatabwj {

    private Integer menuId;

    private String menuName;
    private String type;
    private Integer stock;
    private Integer price;
    private String status;
    private String image;
    private Date date;
    private Integer quantity;


    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }


    public productDatabwj(Integer menuId, String menuName, String type, Integer stock,
                       Integer price, String status, String image, Date date) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.image = image;
        this.date = date;
    }



    public Integer getMenuId() {
        return menuId;
    }



    public String getMenuName() {
        return menuName;
    }

    public String getType() {
        return type;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

    public Integer getQuantity() {
        return quantity;
    }

}