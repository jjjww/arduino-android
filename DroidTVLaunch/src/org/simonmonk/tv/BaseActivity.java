/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.simonmonk.tv;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends DroidTVActivity {

	private InputController mInputController;

	public BaseActivity() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (mAccessory != null) {
			showControls();
		} else {
			hideControls();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Test");
		menu.add("Edit");
		menu.add("Program");
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle() == "Test") {
			showControls();
		} 
		if (item.getTitle() == "Edit" && mInputController != null) {
			mInputController.openSettings();
		} 
		if (item.getTitle() == "Program" && mInputController != null) {
			mInputController.sendProgramCommand();
		} 
		return true;
	}

	protected void enableControls(boolean enable) {
		if (enable) {
			showControls();
		} else {
			hideControls();
		}
	}

	protected void hideControls() {
		setContentView(R.layout.no_device);
		mInputController = null;
	}

	protected void showControls() {
		setContentView(R.layout.main);

		mInputController = new InputController(this);
		mInputController.accessoryAttached();
	}

	protected void handleValueMessage(ValueMsg t) {
		if (mInputController != null) {
			mInputController.handleValueMessage(t.getFlag(), t.getReading());
		}
	}

}