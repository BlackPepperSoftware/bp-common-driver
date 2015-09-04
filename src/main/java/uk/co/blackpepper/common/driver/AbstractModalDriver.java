package uk.co.blackpepper.common.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public abstract class AbstractModalDriver {
	
	private static final int DEFAULT_VISIBLE_WAIT = 3;
	
	private final int visibleWait;

	private final DriverConfiguration config;
	
	private final By byModal;
	
	public AbstractModalDriver(DriverConfiguration config, By byModal) {
		this(config, byModal, DEFAULT_VISIBLE_WAIT);
	}
	
	public AbstractModalDriver(DriverConfiguration config, By byModal, int visibleWait) {
		this.config = checkNotNull(config, "config");
		this.byModal = checkNotNull(byModal, "byModal");
		this.visibleWait = visibleWait;
	}
	
	public final boolean isVisible() {
		return isVisibleUsingLocator(byModal);
	}
	
	public final WebDriver driver() {
		return config.getWebDriver();
	}
	
	public final DriverConfiguration getConfiguration() {
		return config;
	}

	protected final String url(String url) {
		return config.url(url);
	}
	
	protected final SearchContext modal() {
		checkVisible();
		return driver().findElement(byModal);
	}

	protected final void checkVisible() {
		checkState(isVisible(), "Expected %s", byModal);
	}
	
	/**
	 * Wait for this modal dialog to close, after sleeping - it has been observed that sometimes Selenium will report
	 * that an element is no longer being display when in fact the browser is still in the act of hiding said element,
	 * as though the DOM is updated too soon.
	 */
	protected final void waitForClose(int millis) {
		pause(millis);
		waitForClose();
	}
	
	protected final void waitForClose() {
		new WebDriverWait(driver(), visibleWait).until(invisibilityOfElementLocated(byModal));
	}

	protected final boolean isVisibleUsingLocator(By locator) {
		try {
			new WebDriverWait(driver(), visibleWait).until(visibilityOfElementLocated(locator));
			return true;
		}
		catch (TimeoutException exception) {
			return false;
		}
	}
	
	private static void pause(int millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException exception) {
			// Nothing to do
		}
	}
}
