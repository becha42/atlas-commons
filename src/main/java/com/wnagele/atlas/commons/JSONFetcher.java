package com.wnagele.atlas.commons;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.collect.Iterables;

public class JSONFetcher extends Fetcher {
	private JsonFactory factory = new JsonFactory();
	private ObjectMapper mapper = new ObjectMapper();

	public Iterable<JsonNode> fetchJSON(String url) throws AtlasException, IOException {
		InputStream in = fetch(url);
		JsonParser parser = factory.createJsonParser(in);
		JsonNode rootNode = mapper.readTree(parser);
		return Iterables.unmodifiableIterable(rootNode);
	}
}