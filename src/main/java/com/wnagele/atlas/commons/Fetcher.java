package com.wnagele.atlas.commons;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;

public class Fetcher {
	private final String AUTH_COOKIE_NAME = "crowd.token_key";
	private final String AUTH_URL = "https://access.ripe.net";
	private final String FIELD_USERNAME = "username";
	private final String FIELD_PASSWORD = "password";

	private DefaultHttpClient client = new SystemDefaultHttpClient();

	public void login(String username, String password) throws AtlasException, IOException {
		if (isAuthenticated()) // Don't login if we already are authenticated
			return;

		HttpPost post = new HttpPost(AUTH_URL);
		post.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(new BasicNameValuePair(FIELD_USERNAME, username),
		                                                           new BasicNameValuePair(FIELD_PASSWORD, password)), Consts.UTF_8));
		EntityUtils.consumeQuietly(client.execute(post).getEntity());

		if (!isAuthenticated())
			throw new AtlasException("Login failed");
	}

	public InputStream fetch(String url) throws AtlasException, IOException {
		if (!isAuthenticated())
			throw new AtlasException("You need to login before you can perform a fetch operation");

		HttpResponse response = client.execute(new HttpGet(url));
		StatusLine responseStatusLine = response.getStatusLine();
		if (responseStatusLine.getStatusCode() != HttpStatus.SC_OK)
			throw new AtlasException("Incorrect status code when fetching data: " + responseStatusLine);

		return response.getEntity().getContent();
	}

	private boolean isAuthenticated() {
		for (Cookie cookie : client.getCookieStore().getCookies()) {
			if (AUTH_COOKIE_NAME.equals(cookie.getName()))
					return true;
		}
		return false;
	}

	public static String fetchToString(String username, String password, String url) throws AtlasException, IOException {
		Fetcher fetcher = new Fetcher();
		fetcher.login(username, password);
		return CharStreams.toString(new InputStreamReader(fetcher.fetch(url), Consts.UTF_8));
	}
}