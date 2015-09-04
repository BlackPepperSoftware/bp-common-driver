package uk.co.blackpepper.common.driver.support.test;

import org.openqa.selenium.support.ui.ExpectedCondition;

import uk.co.blackpepper.common.driver.AbstractDriver;
import uk.co.blackpepper.common.driver.DriverConfiguration;

public class FakeDriver extends AbstractDriver<FakeDriver> {

	public FakeDriver(DriverConfiguration config) {
		super(config);
	}

	public FakeDriver(DriverConfiguration config, String url) {
		super(config, url);
	}

	public FakeDriver(DriverConfiguration config, String url, ExpectedCondition<Boolean> visibleCondition) {
		super(config, url, visibleCondition);
	}

	public FakeDriver(DriverConfiguration config, String url, ExpectedCondition<Boolean> visibleCondition,
		int visibleWait) {
		super(config, url, visibleCondition, visibleWait);
	}

	@Override
	protected FakeDriver self() {
		return this;
	}
}
