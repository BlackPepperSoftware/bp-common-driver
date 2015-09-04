package uk.co.blackpepper.common.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractModalDriverTest {
	
	private AbstractModalDriver modalDriver;

	@Test(expected = NullPointerException.class)
	public void constructorWithNullConfigurationThrowsException() {
		newModalDriver(null, mock(By.class));
	}

	@Test(expected = NullPointerException.class)
	public void constructorWithNullByThrowsException() throws MalformedURLException {
		newModalDriver(newDriverConfiguration(), null);
	}

	@Test
	public void isVisibleWhenDisplayedReturnsTrue() throws MalformedURLException {
		WebDriver driver = mock(WebDriver.class);
		WebElement element = newDisplayedElement();
		when(driver.findElement(By.id("x"))).thenReturn(element);
		modalDriver = newModalDriver(newDriverConfiguration(driver), By.id("x"));
		
		assertThat(modalDriver.isVisible(), is(true));
	}

	@Test
	public void isVisibleWhenUndisplayedReturnsFalse() throws MalformedURLException {
		WebDriver driver = mock(WebDriver.class);
		WebElement element = newUndisplayedElement();
		when(driver.findElement(By.id("x"))).thenReturn(element);
		modalDriver = newModalDriver(newDriverConfiguration(driver), By.id("x"), 0);
		
		assertThat(modalDriver.isVisible(), is(false));
	}

	@Test
	public void checkVisibleWhenDisplayedDoesNotThrowException() throws MalformedURLException {
		WebDriver driver = mock(WebDriver.class);
		WebElement element = newDisplayedElement();
		when(driver.findElement(By.id("x"))).thenReturn(element);
		modalDriver = newModalDriver(newDriverConfiguration(driver), By.id("x"));
		
		modalDriver.checkVisible();
	}

	@Test(expected = IllegalStateException.class)
	public void checkVisibleWhenUndisplayedThrowsException() throws MalformedURLException {
		WebDriver driver = mock(WebDriver.class);
		WebElement element = newUndisplayedElement();
		when(driver.findElement(By.id("x"))).thenReturn(element);
		modalDriver = newModalDriver(newDriverConfiguration(driver), By.id("x"), 0);
		
		modalDriver.checkVisible();
	}

	@Test
	public void urlWithPathReturnsUrl() throws MalformedURLException {
		modalDriver = newModalDriver(newDriverConfiguration("http://x/"), mock(By.class), 0);
		
		assertThat(modalDriver.url("/z"), is("http://x/z"));
	}
	
	@Test
	public void urlWithUrlReturnsUrl() throws MalformedURLException {
		modalDriver = newModalDriver(newDriverConfiguration("http://x/"), mock(By.class), 0);
		
		assertThat(modalDriver.url("http://y/z"), is("http://y/z"));
	}
	
	@Test(expected = NullPointerException.class)
	public void urlWithNullThrowsException() throws MalformedURLException {
		modalDriver = newModalDriver(newDriverConfiguration("http://x/"), mock(By.class), 0);
		
		modalDriver.url(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void urlWithMalformedUrlThrowsException() throws MalformedURLException {
		modalDriver = newModalDriver(newDriverConfiguration("http://x/"), mock(By.class), 0);
		
		modalDriver.url("x:");
	}
	
	private static AbstractModalDriver newModalDriver(DriverConfiguration config, By byModal) {
		return new AbstractModalDriver(config, byModal) {
			// concrete implementation
		};
	}

	private static AbstractModalDriver newModalDriver(DriverConfiguration config, By byModal, int visibleWait) {
		return new AbstractModalDriver(config, byModal, visibleWait) {
			// concrete implementation
		};
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

	private static WebElement newDisplayedElement() {
		WebElement element = mock(WebElement.class);
		when(element.isDisplayed()).thenReturn(true);
		return element;
	}

	private static WebElement newUndisplayedElement() {
		WebElement element = mock(WebElement.class);
		when(element.isDisplayed()).thenReturn(false);
		return element;
	}
}
