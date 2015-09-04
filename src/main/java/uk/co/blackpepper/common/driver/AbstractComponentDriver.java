package uk.co.blackpepper.common.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.openqa.selenium.support.ui.ExpectedConditions.not;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractComponentDriver {
	
	private static final int VISIBLE_TIMEOUT = 1;

	private final AbstractDriver<?> page;
	
	private final WebElement element;

	public AbstractComponentDriver(AbstractDriver<?> page, WebElement element) {
		this.page = checkNotNull(page, "page");
		this.element = checkNotNull(element, "element");
	}
	
	public AbstractDriver<?> page() {
		return page;
	}
	
	public final WebElement element() {
		return element;
	}

	public boolean isVisible() {
		return element().isDisplayed();
	}

	public void waitUntilVisible() {
		page.driverWait(VISIBLE_TIMEOUT).until(visibilityOf(element()));
	}
	
	public void waitUntilNotVisible() {
		page.driverWait(VISIBLE_TIMEOUT).until(not(visibilityOf(element())));
	}
	
	public final WebDriver driver() {
		return page.driver();
	}
	
	public Actions actions() {
		return new Actions(driver());
	}
}
