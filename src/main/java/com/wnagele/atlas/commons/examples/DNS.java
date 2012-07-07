package com.wnagele.atlas.commons.examples;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonNode;
import org.xbill.DNS.Message;

import com.wnagele.atlas.commons.JSONFetcher;

public class DNS extends CommandExecutor {
	public static void main(String[] args) {
		new DNS().execute(args);
	}

	@Override
	public void execute(String username, String password, String[] urls) throws Exception {
		JSONFetcher fetcher = new JSONFetcher();
		fetcher.login(username, password);
		for (String url : urls) {
			for (JsonNode msm : fetcher.fetchJSON(url)) {
				JsonNode type = msm.get("type");
				if (type == null) {
					System.err.println("Missing type definition");
					continue;
				}

				String typeValue = type.getTextValue();
				if (!"dns".equals(typeValue)) {
					System.err.println("Expected dns type, received: " + typeValue);
					continue;
				}

				JsonNode result = msm.get("result");
				if (result == null) {
					System.err.println("Missing result");
					continue;
				}

				JsonNode answer = result.get("abuf");
				if (answer == null) {
					System.err.println("Missing answer buffer");
					continue;
				}

				System.out.println(new Message(Base64.decodeBase64(answer.getTextValue())));
			}
		}
	}
}