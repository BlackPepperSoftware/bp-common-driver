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
