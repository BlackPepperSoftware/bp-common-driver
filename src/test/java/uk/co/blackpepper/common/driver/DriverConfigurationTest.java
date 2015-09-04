package uk.co.blackpepper.common.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class DriverConfigurationTest {
	
	@Test
	public void constructorSetsProperties() throws MalformedURLException {
		WebDriver webDriver = mock(WebDriver.class);
		URL serverUrl = new URL("http://x/");
		
		DriverConfiguration actual = new DriverConfiguration(webDriver, serverUrl);
		
		assertThat("webDriver", actual.getWebDriver(), is(webDriver));
		assertThat("serverUrl", actual.getServerUrl(), is(serverUrl));
	}

	@Test(expected = NullPointerException.class)
	public void constructorWithNullWebDriverThrowsException() throws MalformedURLException {
		new DriverConfiguration(null, new URL("http://x/"));
	}
	
	@Test(expected = NullPointerException.class)
	public void constructorWithNullServerUrlThrowsException() {
		new DriverConfiguration(mock(WebDriver.class), null);
	}
	
	@Test
	public void urlWithPathReturnsUrl() throws MalformedURLException {
		DriverConfiguration config = new DriverConfiguration(mock(WebDriver.class), new URL("http://x/"));

		assertThat(config.url("/z"), is("http://x/z"));
	}

	@Test
	public void urlWithUrlReturnsUrl() throws MalformedURLException {
		DriverConfiguration config = new DriverConfiguration(mock(WebDriver.class), new URL("http://x/"));

		assertThat(config.url("http://y/z"), is("http://y/z"));
	}

	@Test(expected = NullPointerException.class)
	public void urlWithNullThrowsException() throws MalformedURLException {
		new DriverConfiguration(mock(WebDriver.class), new URL("http://x/")).url(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void urlWithMalformedUrlThrowsException() throws MalformedURLException {
		new DriverConfiguration(mock(WebDriver.class), new URL("http://x/")).url("x:");
	}
}
