package uk.co.blackpepper.common.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.support.ui.ExpectedCondition;

import uk.co.blackpepper.common.driver.support.test.FakeDriver;

import static java.util.Collections.singleton;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AbstractDriverTest {
	
	@Test
	public void constructorWithConfigurationSetsProperties() throws MalformedURLException {
		DriverConfiguration config = newDriverConfiguration();
		
		AbstractDriver<?> driver = new FakeDriver(config);
		
		assertThat("configuration", driver.getConfiguration(), is(config));
		assertThat("url", driver.getUrl(), is(nullValue()));
		assertThat("visibleCondition", driver.getVisibleCondition(), is(nullValue()));
	}
	
	@Test(expected = NullPointerException.class)
	public void constructorWithNullConfigurationThrowsException() {
		new FakeDriver(null);
	}
	
	@Test
	public void constructorWithConfigurationAndUrlSetsProperties() throws MalformedURLException {
		DriverConfiguration config = newDriverConfiguration();
		
		AbstractDriver<?> driver = new FakeDriver(config, "x");
		
		assertThat("configuration", driver.getConfiguration(), is(config));
		assertThat("url", driver.getUrl(), is("x"));
		assertThat("visibleCondition", driver.getVisibleCondition(), is(nullValue()));
	}
	
	@Test
	public void constructorWithConfigurationAndUrlAndVisibleConditionSetsProperties() throws MalformedURLException {
		DriverConfiguration config = newDriverConfiguration();
		ExpectedCondition<Boolean> visibleCondition = mock(ExpectedCondition.class);
		
		AbstractDriver<?> driver = new FakeDriver(config, "x", visibleCondition);
		
		assertThat("configuration", driver.getConfiguration(), is(config));
		assertThat("url", driver.getUrl(), is("x"));
		assertThat("visibleCondition", driver.getVisibleCondition(), is(visibleCondition));
	}
	
	@Test
	public void showWhenPathGetsUrl() throws MalformedURLException {
		WebDriver webDriver = mock(WebDriver.class);
		DriverConfiguration config = new DriverConfiguration(webDriver, new URL("http://x/"));
		AbstractDriver<?> driver = new FakeDriver(config, "/z");
		
		driver.show();
		
		verify(webDriver).get("http://x/z");
	}
	
	@Test
	public void showWhenUrlGetsUrl() throws MalformedURLException {
		WebDriver webDriver = mock(WebDriver.class);
		DriverConfiguration config = new DriverConfiguration(webDriver, new URL("http://x/"));
		AbstractDriver<?> driver = new FakeDriver(config, "http://y/z");
		
		driver.show();
		
		verify(webDriver).get("http://y/z");
	}
	
	@Test(expected = IllegalStateException.class)
	public void showWhenNullUrlThrowsException() throws MalformedURLException {
		AbstractDriver<?> driver = new FakeDriver(newDriverConfiguration(), null);
		
		driver.show();
	}
	
	@Test
	public void isVisibleWhenVisibleReturnsTrue() throws MalformedURLException {
		AbstractDriver<?> driver = new FakeDriver(newDriverConfiguration(), "x", newExpectedCondition(true));
		
		assertThat(driver.isVisible(), is(true));
	}
	
	@Test
	public void isVisibleWhenNotVisibleReturnsFalse() throws MalformedURLException {
		AbstractDriver<?> driver = new FakeDriver(newDriverConfiguration(), "x", newExpectedCondition(false), 0);
		
		assertThat(driver.isVisible(), is(false));
	}
	
	@Test
	public void isVisibleInAnotherWindowWhenVisibleReturnsTrue() throws MalformedURLException {
		WebDriver webDriver = mock(WebDriver.class);
		when(webDriver.getWindowHandles()).thenReturn(singleton("y"));
		whenSwitchToWindow(webDriver, "y").thenReturn(webDriver);
		AbstractDriver<?> driver = new FakeDriver(newDriverConfiguration(webDriver), "x", newExpectedCondition(true));
		
		assertThat(driver.isVisibleInAnotherWindow(), is(true));
	}
	
	@Test
	public void isVisibleInAnotherWindowWhenNotVisibleReturnsFalse() throws MalformedURLException {
		WebDriver webDriver = mock(WebDriver.class);
		when(webDriver.getWindowHandles()).thenReturn(singleton("y"));
		whenSwitchToWindow(webDriver, "y").thenReturn(webDriver);
		AbstractDriver<?> driver = new FakeDriver(newDriverConfiguration(webDriver), "x", newExpectedCondition(false),
			0);
		
		assertThat(driver.isVisibleInAnotherWindow(), is(false));
	}
	
	@Test
	public void urlWithPathReturnsUrl() throws MalformedURLException {
		FakeDriver driver = new FakeDriver(newDriverConfiguration("http://x/"));
		
		assertThat(driver.url("/z"), is("http://x/z"));
	}
	
	@Test
	public void urlWithUrlReturnsUrl() throws MalformedURLException {
		FakeDriver driver = new FakeDriver(newDriverConfiguration("http://x/"));
		
		assertThat(driver.url("http://y/z"), is("http://y/z"));
	}
	
	@Test(expected = NullPointerException.class)
	public void urlWithNullThrowsException() throws MalformedURLException {
		new FakeDriver(newDriverConfiguration("http://x/")).url(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void urlWithMalformedUrlThrowsException() throws MalformedURLException {
		new FakeDriver(newDriverConfiguration("http://x/")).url("x:");
	}
	
	private static DriverConfiguration newDriverConfiguration() throws MalformedURLException {
		return newDriverConfiguration(mock(WebDriver.class));
	}

	private static DriverConfiguration newDriverConfiguration(WebDriver webDriver) throws MalformedURLException {
		return new DriverConfiguration(webDriver, new URL("http://_host/"));
	}
	
	private static DriverConfiguration newDriverConfiguration(String serverUrl) throws MalformedURLException {
		return new DriverConfiguration(mock(WebDriver.class), new URL(serverUrl));
	}

	private static ExpectedCondition<Boolean> newExpectedCondition(boolean result) {
		ExpectedCondition<Boolean> condition = mock(ExpectedCondition.class);
		when(condition.apply(any(WebDriver.class))).thenReturn(result);
		return condition;
	}

	private static OngoingStubbing<WebDriver> whenSwitchToWindow(WebDriver driver, String nameOrHandle) {
		TargetLocator locator = mock(TargetLocator.class);
		when(driver.switchTo()).thenReturn(locator);
		return when(locator.window(nameOrHandle));
	}
}
