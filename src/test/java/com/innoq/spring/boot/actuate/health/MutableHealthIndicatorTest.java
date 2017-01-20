/**
 * Copyright 2017 innoQ Deutschland GmbH
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
package com.innoq.spring.boot.actuate.health;

import org.junit.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import static org.assertj.core.api.Assertions.assertThat;

public class MutableHealthIndicatorTest {

    @Test
    public void health_should_return_unknown_before_check_is_called() throws Exception {
        MutableHealthIndicator sut = MutableHealthIndicator.wrap(FixedHealthIndicator.up());

        Health health = sut.health();

        assertThat(health.getStatus()).isEqualTo(Status.UNKNOWN);
    }

    @Test
    public void health_should_return_status_from_check_after_check_is_called() throws Exception {
        MutableHealthIndicator sut = MutableHealthIndicator.wrap(FixedHealthIndicator.up());
        sut.check();

        Health health = sut.health();

        assertThat(health.getStatus()).isEqualTo(Status.UP);
    }
}
