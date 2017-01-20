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

public class FixedHealthIndicatorTest {

    @Test
    public void withStatus_should_return_instance_with_given_status() throws Exception {
        Status expectedStatus = new Status("FOO");
        FixedHealthIndicator sut = FixedHealthIndicator.withStatus(expectedStatus);

        Status status = sut.getStatus();

        assertThat(status).isEqualTo(expectedStatus);
    }

    @Test
    public void health_contains_given_status() throws Exception {
        Status expectedStatus = new Status("FOO");
        FixedHealthIndicator sut = FixedHealthIndicator.withStatus(expectedStatus);

        Health health = sut.health();

        assertThat(health.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    public void health_returns_given_health() throws Exception {
        Health expectedHealth = Health.up().withDetail("foo", "bar").build();
        FixedHealthIndicator sut = FixedHealthIndicator.withHealth(expectedHealth);

        Health health = sut.health();

        assertThat(health).isSameAs(expectedHealth);
    }

    @Test
    public void unknown_should_return_instance_with_status_unknown() throws Exception {
        FixedHealthIndicator sut = FixedHealthIndicator.unknown();

        Status status = sut.getStatus();

        assertThat(status).isEqualTo(Status.UNKNOWN);
    }

    @Test
    public void up_should_return_instance_with_status_up() throws Exception {
        FixedHealthIndicator sut = FixedHealthIndicator.up();

        Status status = sut.getStatus();

        assertThat(status).isEqualTo(Status.UP);
    }

    @Test
    public void down_should_return_instance_with_status_down() throws Exception {
        FixedHealthIndicator sut = FixedHealthIndicator.down();

        Status status = sut.getStatus();

        assertThat(status).isEqualTo(Status.DOWN);
    }

    @Test
    public void outOfService_should_return_health_with_unknown_outOfService() throws Exception {
        FixedHealthIndicator sut = FixedHealthIndicator.outOfService();

        Status status = sut.getStatus();

        assertThat(status).isEqualTo(Status.OUT_OF_SERVICE);
    }

    @Test
    public void withStatus_should_return_cached_instance_for_status_unknown() throws Exception {
        FixedHealthIndicator indicator = FixedHealthIndicator.withStatus(Status.UNKNOWN);

        assertThat(indicator).isSameAs(FixedHealthIndicator.UNKNOWN);
    }

    @Test
    public void withStatus_should_return_cached_instance_for_status_up() throws Exception {
        FixedHealthIndicator indicator = FixedHealthIndicator.withStatus(Status.UP);

        assertThat(indicator).isSameAs(FixedHealthIndicator.UP);
    }

    @Test
    public void withStatus_should_return_cached_instance_for_status_down() throws Exception {
        FixedHealthIndicator indicator = FixedHealthIndicator.withStatus(Status.DOWN);

        assertThat(indicator).isSameAs(FixedHealthIndicator.DOWN);
    }

    @Test
    public void withStatus_should_return_cached_instance_for_status_outOfService() throws Exception {
        FixedHealthIndicator indicator = FixedHealthIndicator.withStatus(Status.OUT_OF_SERVICE);

        assertThat(indicator).isSameAs(FixedHealthIndicator.OUT_OF_SERVICE);
    }
}
