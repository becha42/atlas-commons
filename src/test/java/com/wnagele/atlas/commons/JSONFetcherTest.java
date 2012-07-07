package com.wnagele.atlas.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.io.Files;
import com.wnagele.atlas.commons.AtlasException;
import com.wnagele.atlas.commons.JSONFetcher;

public class JSONFetcherTest {
	@Test
	public void test() throws AtlasException, IOException {
		JSONFetcher fetcher = getFetcherFromFile("src/test/resources/test.json");
		Iterable<JsonNode> result = fetcher.fetchJSON(null);
		assertEquals(3, Iterables.size(result));
		Iterator<JsonNode> resultIter = result.iterator();
		validateMeasurement(resultIter.next());
		validateMeasurement(resultIter.next());
		validateMeasurement(resultIter.next());
		assertFalse(resultIter.hasNext());
	}

	private void validateMeasurement(JsonNode msm) {
		assertEquals(11, msm.size());
		JsonNode result = msm.get("result");
		assertNotNull(result);
		assertEquals(9, result.size());
	}

	private JSONFetcher getFetcherFromFile(final String file) {
		return new JSONFetcher() {
			@Override
			public InputStream fetch(String url) throws AtlasException, IOException {
				return Files.newInputStreamSupplier(new File(file)).getInput();
			}
		};
	}
}