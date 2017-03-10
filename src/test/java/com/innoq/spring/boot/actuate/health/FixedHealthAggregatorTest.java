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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class FixedHealthAggregatorTest {

    @Test
    public void aggregate_should_return_status_with_no_given_healths() throws Exception {
        Status expectedStatus = new Status("FOO");
        FixedHealthAggregator sut = FixedHealthAggregator.withStatus(expectedStatus);

        Health health = sut.aggregate(Collections.emptyMap());

        assertThat(health.getStatus()).isSameAs(expectedStatus);
    }

    @Test
    public void aggregate_should_return_status_with_different_healths_given() throws Exception {
        Status expectedStatus = new Status("FOO");
        FixedHealthAggregator sut = FixedHealthAggregator.withStatus(expectedStatus);

        Map<String, Health> healths = new HashMap<>();
        healths.put("foo", Health.down().build());
        healths.put("bar", Health.outOfService().build());

        Health health = sut.aggregate(healths);

        assertThat(health.getStatus()).isSameAs(expectedStatus);
    }

    @Test
    public void withStatus_should_return_instance_with_given_status() throws Exception {
        Status expectedStatus = new Status("FOO");
        FixedHealthAggregator sut = FixedHealthAggregator.withStatus(expectedStatus);

        Status status = sut.getStatus();

        assertThat(status).isSameAs(expectedStatus);
    }

    @Test
    public void unknown_should_return_instance_with_status_unknown() throws Exception {
        FixedHealthAggregator sut = FixedHealthAggregator.unknown();

        Status status = sut.getStatus();

        assertThat(status).isEqualTo(Status.UNKNOWN);
    }

    @Test
    public void up_should_return_instance_with_status_up() throws Exception {
        FixedHealthAggregator sut = FixedHealthAggregator.up();

        Status status = sut.getStatus();

        assertThat(status).isEqualTo(Status.UP);
    }

    @Test
    public void down_should_return_instance_with_status_down() throws Exception {
        FixedHealthAggregator sut = FixedHealthAggregator.down();

        Status status = sut.getStatus();

        assertThat(status).isEqualTo(Status.DOWN);
    }

    @Test
    public void outOfService_should_return_health_with_unknown_outOfService() throws Exception {
        FixedHealthAggregator sut = FixedHealthAggregator.outOfService();

        Status status = sut.getStatus();

        assertThat(status).isEqualTo(Status.OUT_OF_SERVICE);
    }

    @Test
    public void withStatus_should_return_cached_instance_for_status_unknown() throws Exception {
        FixedHealthAggregator first = FixedHealthAggregator.withStatus(Status.UNKNOWN);
        FixedHealthAggregator second = FixedHealthAggregator.withStatus(Status.UNKNOWN);

        assertThat(first).isSameAs(second);
    }

    @Test
    public void withStatus_should_return_cached_instance_for_status_up() throws Exception {
        FixedHealthAggregator first = FixedHealthAggregator.withStatus(Status.UP);
        FixedHealthAggregator second = FixedHealthAggregator.withStatus(Status.UP);

        assertThat(first).isSameAs(second);
    }

    @Test
    public void withStatus_should_return_cached_instance_for_status_down() throws Exception {
        FixedHealthAggregator first = FixedHealthAggregator.withStatus(Status.DOWN);
        FixedHealthAggregator second = FixedHealthAggregator.withStatus(Status.DOWN);

        assertThat(first).isSameAs(second);
    }

    @Test
    public void withStatus_should_return_cached_instance_for_status_outOfService() throws Exception {
        FixedHealthAggregator first = FixedHealthAggregator.withStatus(Status.OUT_OF_SERVICE);
        FixedHealthAggregator second = FixedHealthAggregator.withStatus(Status.OUT_OF_SERVICE);

        assertThat(first).isSameAs(second);
    }
}
