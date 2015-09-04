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
