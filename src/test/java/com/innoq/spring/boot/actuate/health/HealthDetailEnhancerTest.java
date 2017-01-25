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

import static org.assertj.core.api.Assertions.assertThat;

public class HealthDetailEnhancerTest {

    @Test
    public void health_should_have_status_of_enhanced_indicator() throws Exception {
        FixedHealthIndicator indicator = FixedHealthIndicator.up();
        HealthDetailEnhancer sut = HealthDetailEnhancer.create().enhance(indicator);

        Health health = sut.health();

        assertThat(health.getStatus()).isEqualTo(indicator.getStatus());
    }

    @Test
    public void health_should_contain_added_fixed_detail() throws Exception {
        HealthDetailEnhancer sut = HealthDetailEnhancer
            .withDetail("foo", "bar").enhance(FixedHealthIndicator.up());

        Health health = sut.health();

        assertThat(health.getDetails())
            .hasSize(1)
            .containsEntry("foo", "bar");
    }

    @Test
    public void health_should_include_enhanced_indicator_details() throws Exception {
        FixedHealthIndicator indicator = FixedHealthIndicator.withHealth(
            Health.down()
                .withDetail("foo", "bar")
                .build());
        HealthDetailEnhancer sut = HealthDetailEnhancer
            .withDetail("bar", "foo").enhance(indicator);

        Health health = sut.health();

        assertThat(health.getDetails())
            .hasSize(2)
            .containsEntry("foo", "bar")
            .containsEntry("bar", "foo");
    }

    @Test
    public void health_should_not_override_value_if_detail_with_same_key_exists() throws Exception {
        FixedHealthIndicator indicator = FixedHealthIndicator.withHealth(
            Health.down()
                .withDetail("foo", "bar")
                .build());
        HealthDetailEnhancer sut = HealthDetailEnhancer
            .withDetail("foo", "baz").enhance(indicator);

        Health health = sut.health();

        assertThat(health.getDetails())
            .containsEntry("foo", "bar");
    }
}
