package com.wnagele.atlas.commons.examples;

import java.io.InputStreamReader;

import org.apache.http.Consts;

import com.google.common.io.CharStreams;
import com.wnagele.atlas.commons.Fetcher;

public class Plain extends CommandExecutor {
	public static void main(String[] args) {
		new Plain().execute(args);
	}

	@Override
	public void execute(String username, String password, String[] urls) throws Exception {
		Fetcher fetcher = new Fetcher();
		fetcher.login(username, password);
		for (String url : urls)
			System.out.println(CharStreams.toString(new InputStreamReader(fetcher.fetch(url), Consts.UTF_8)));
	}
}