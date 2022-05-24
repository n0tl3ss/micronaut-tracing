/*
 * Copyright 2017-2022 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.tracing.opentelemetry.instrument.http.server;

import io.micronaut.core.annotation.Internal;
import io.micronaut.http.HttpRequest;
import io.opentelemetry.instrumentation.api.instrumenter.net.InetSocketAddressNetServerAttributesGetter;

import java.net.InetSocketAddress;

import static io.opentelemetry.semconv.trace.attributes.SemanticAttributes.NetTransportValues.IP_TCP;

@SuppressWarnings("rawtypes")
@Internal
final class MicronautHttpNetServerAttributesGetter extends InetSocketAddressNetServerAttributesGetter<HttpRequest<?>> {

    @Override
    public InetSocketAddress getAddress(HttpRequest<?> request) {
        return request.getRemoteAddress();
    }

    @Override
    public String transport(HttpRequest<?> request) {
        return IP_TCP;
    }
}
