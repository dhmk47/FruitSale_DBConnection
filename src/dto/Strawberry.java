package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Strawberry {
	private static final int code = 0;
	private String fruitName;
	private int price;
	private int amount;
	
	public static int getCode() {
		return code;
	}
}
