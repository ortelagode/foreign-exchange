package com.example.util;

import java.io.IOException;
import java.io.InputStreamReader;

import com.example.util.exceptions.ServiceException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.google.common.io.CharStreams;

import lombok.Cleanup;

public class Util {

	public static String processHttpResponse(HttpResponse response) throws ServiceException {
		try {
			HttpEntity responseEntity = response.getEntity();
			@Cleanup
			InputStreamReader reader = new InputStreamReader(responseEntity.getContent());
			String responseBody = CharStreams.toString(reader);
			return responseBody;
		} catch (UnsupportedOperationException | IOException e) {
			throw new ServiceException(e);
		}
	}

}
