package ch.portmann.messenger;

import java.util.ArrayList;
import java.util.List;

public class Messenger {

	private String message = "";
	private List<String> messages = new ArrayList<String>();

	public Messenger() {

		// 0
		messages.add("You are a super star! You are right!");
		// 1
		messages.add("You can simplify your result!");
		// 2
		messages.add("You have to expand your result!");
		// 3
		messages.add("");
		// 4
		messages.add("");
		// 5
		messages.add("");
		// 6
		messages.add("");
		// 7
		messages.add("");
		// 8
		messages.add("");

	}

	public void setMessage(int i) {

		message = messages.get(i);

	}

	public String getMessage() {

		return this.message;

	}

}
