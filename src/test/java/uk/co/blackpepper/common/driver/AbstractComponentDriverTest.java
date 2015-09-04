package uk.co.blackpepper.common.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import uk.co.blackpepper.common.driver.support.test.FakeDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractComponentDriverTest {
	
	private AbstractComponentDriver driver;
	
	@Test(expected = NullPointerException.class)
	public void constructorWithNullPageThrowsException() {
		driver = newComponentDriver(null, mock(WebElement.class));
	}

	@Test(expected = NullPointerException.class)
	public void constructorWithNullElementThrowsException() {
		driver = newComponentDriver(mock(AbstractDriver.class), null);
	}
	
	@Test
	public void isVisibleWhenDisplayedReturnsTrue() {
		driver = newComponentDriver(mock(AbstractDriver.class), newDisplayedElement());
		
		assertThat(driver.isVisible(), is(true));
	}
	
	@Test
	public void isVisibleWhenUndisplayedReturnsFalse() {
		driver = newComponentDriver(mock(AbstractDriver.class), newUndisplayedElement());
		
		assertThat(driver.isVisible(), is(false));
	}
	
	@Test
	public void waitUntilVisibleWhenDisplayedDoesNotThrowException() throws MalformedURLException {
		driver = newComponentDriver(newDriver(), newDisplayedElement());
		
		driver.waitUntilVisible();
	}
	
	@Test(expected = TimeoutException.class)
	public void waitUntilVisibleWhenUndisplayedThrowsException() throws MalformedURLException {
		driver = newComponentDriver(newDriver(), newUndisplayedElement());
		
		driver.waitUntilVisible();
	}

	@Test
	public void waitUntilNotVisibleWhenUndisplayedDoesNotThrowException() throws MalformedURLException {
		driver = newComponentDriver(newDriver(), newUndisplayedElement());
		
		driver.waitUntilNotVisible();
	}
	
	@Test(expected = TimeoutException.class)
	public void waitUntilNotVisibleWhenDisplayedThrowsException() throws MalformedURLException {
		driver = newComponentDriver(newDriver(), newDisplayedElement());
		
		driver.waitUntilNotVisible();
	}
	
	private static AbstractComponentDriver newComponentDriver(AbstractDriver<?> driver, WebElement element) {
		return new AbstractComponentDriver(driver, element) {
			// concrete class
		};
	}
	
	private static AbstractDriver<?> newDriver() throws MalformedURLException {
		return new FakeDriver(new DriverConfiguration(mock(WebDriver.class), new URL("http://_host/")));
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
