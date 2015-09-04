package uk.co.blackpepper.common.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import static uk.co.blackpepper.support.selenium.ExpectedConditions2.and;
import static uk.co.blackpepper.support.selenium.ExpectedConditions2.anotherWindowToBeAvailableAndSwitchToIt;
import static uk.co.blackpepper.support.selenium.WebDriverUtils.until;

public abstract class AbstractDriver<T extends AbstractDriver<T>> implements Driver<T> {

	private static final int DEFAULT_VISIBLE_WAIT = 3;
	
	private final int visibleWait;

	private final DriverConfiguration config;

	private final String url;

	private final ExpectedCondition<Boolean> visibleCondition;
	
	public AbstractDriver(DriverConfiguration config) {
		this(config, null);
	}
	
	public AbstractDriver(DriverConfiguration config, String url) {
		this(config, url, null);
	}
	
	public AbstractDriver(DriverConfiguration config, String url, ExpectedCondition<Boolean> visibleCondition) {
		this(config, url, visibleCondition, DEFAULT_VISIBLE_WAIT);
	}
	
	public AbstractDriver(DriverConfiguration config, String url, ExpectedCondition<Boolean> visibleCondition,
		int visibleWait) {
		this.config = checkNotNull(config, "config");
		this.url = url;
		this.visibleCondition = visibleCondition;
		this.visibleWait = visibleWait;
	}

	@Override
	public T show() {
		checkState(url != null, "Missing url to show");
		driver().get(url(url));
		return self();
	}
	
	@Override
	public boolean isVisible() {
		return until(new WebDriverWait(driver(), visibleWait), visibleCondition);
	}
	
	@Override
	public boolean isVisibleInAnotherWindow() {
		return until(new WebDriverWait(driver(), visibleWait),
			and(anotherWindowToBeAvailableAndSwitchToIt(), visibleCondition));
	}
	
	public void checkVisible() {
		checkState(isVisible(), "Expected %s", visibleCondition);
	}
	
	public DriverConfiguration getConfiguration() {
		return config;
	}
	
	public String getUrl() {
		return url;
	}
	
	public ExpectedCondition<Boolean> getVisibleCondition() {
		return visibleCondition;
	}

	public WebDriver driver() {
		return config.getWebDriver();
	}
	
	public WebDriverWait driverWait(int timeout) {
		return new WebDriverWait(driver(), timeout);
	}
	
	public Actions actions() {
		return new Actions(driver());
	}
	
	protected final String url(String url) {
		return config.url(url);
	}
	
	protected abstract T self();
}
