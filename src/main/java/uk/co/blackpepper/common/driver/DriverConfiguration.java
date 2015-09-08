/*
 * Copyright 2014 Black Pepper Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.blackpepper.common.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;

import static com.google.common.base.Preconditions.checkNotNull;

public final class DriverConfiguration {
	
	private final WebDriver webDriver;
	
	private final URL serverUrl;

	public DriverConfiguration(WebDriver webDriver, URL serverUrl) {
		this.webDriver = checkNotNull(webDriver, "webDriver");
		this.serverUrl = checkNotNull(serverUrl, "serverUrl");
	}
	
	public WebDriver getWebDriver() {
		return webDriver;
	}
	
	public URL getServerUrl() {
		return serverUrl;
	}

	public String url(String url) {
		checkNotNull(url, "url");
		
		try {
			return new URL(getServerUrl(), url).toString();
		}
		catch (MalformedURLException exception) {
			throw new IllegalArgumentException("Cannot create URL", exception);
		}
	}
}
