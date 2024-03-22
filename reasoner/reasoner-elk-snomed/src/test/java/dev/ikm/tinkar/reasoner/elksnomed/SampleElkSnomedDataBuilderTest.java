/*
 * Copyright © 2015 Integrated Knowledge Management (support@ikm.dev)
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
package dev.ikm.tinkar.reasoner.elksnomed;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.ikm.tinkar.common.service.PrimitiveData;

public class SampleElkSnomedDataBuilderTest extends ElkSnomedDataBuilderTest {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(SampleElkSnomedDataBuilderTest.class);

	static {
		stated_count = 83547;
		active_count = 83547;
		inactive_count = 0;
		test_case = "sample";
	}

	public static final String db = "sample-data-3-sa";

	@BeforeAll
	public static void startPrimitiveData() throws IOException {
		setupPrimitiveData(db);
		PrimitiveData.start();
	}

}
