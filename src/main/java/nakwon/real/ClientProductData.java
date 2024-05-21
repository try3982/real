package nakwon.real;

public class ClientProductData {
	/* 각각 상품의 이름, 이미지, 가격, 수량을 저장하기 위함.
	 * 각 set_ 함수들은 상품들의 정보를 기억하기 위함.
	 * 각 get_ 함수들은 기억하고 있는 정보들을 알려주기 위함.
	 */
	public String name;
	public String ImageSrc;
	public int price;
    public int quantity;
    
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImageSrc() {
		return ImageSrc;
	}
	
	public void setImageSrc(String image) {
		this.ImageSrc = image;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
   
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
