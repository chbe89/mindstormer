package edu.kit.mindstormer.com.commands;

import java.io.IOException;
import java.net.URL;

public class RequestStatus extends AbstractCommand<Boolean> {

	public RequestStatus(URL commandUrl) {
		super(commandUrl);
	}

	@Override
	public Boolean execute() throws IOException {
		String response = doGet();
		return "FREE".equalsIgnoreCase(response);
	}

}
