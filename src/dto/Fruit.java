package dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class Fruit {
	private int code;
	private String fruitName;
	private int price;
	private int amount;
	

	public void setCode(int code) {
		this.code = code;
	}


	public void setFruitName(String fruitName) {
		this.fruitName = fruitName;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "Fruit [code = " + code + ", fruitName=" + fruitName + ", price=" + price + ", amount=" + amount + "]";
	}
}