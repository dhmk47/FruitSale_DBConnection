package dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

//@Data
@Getter
@Builder
public class Apple {
	private static final int code = 1;
	private String fruitName;
	private int price;
	private int amount;
	
	public static int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "Apple [code = " + code + ", fruitName=" + fruitName + ", price=" + price + ", amount=" + amount + "]";
	}
	
}