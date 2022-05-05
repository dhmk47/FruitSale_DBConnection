package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Apple {
	private static final int code = 1;
	private String fruitName;
	private int price;
	private int amount;
	
	public static int getCode() {
		return code;
	}
	
}