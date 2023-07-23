package utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

	public static void main(String[] args) {
		System.out.println("2023-04-30 10:00:00".replace(" ", "T"));
		LocalDateTime stime = LocalDateTime.parse("2023-04-30 10:00:00".replace(" ", "T"),  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		System.out.println(stime);
		

	}

}
