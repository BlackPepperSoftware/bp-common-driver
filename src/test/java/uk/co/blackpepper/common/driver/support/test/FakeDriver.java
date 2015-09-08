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
