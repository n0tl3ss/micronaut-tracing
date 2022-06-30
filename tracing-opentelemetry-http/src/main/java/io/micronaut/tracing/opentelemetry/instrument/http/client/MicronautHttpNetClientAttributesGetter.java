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
package io.micronaut.tracing.opentelemetry.instrument.http.client;

import io.micronaut.core.annotation.Internal;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.opentelemetry.instrumentation.api.instrumenter.net.NetClientAttributesGetter;

import javax.annotation.Nullable;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import static io.micronaut.http.HttpAttributes.SERVICE_ID;
import static io.opentelemetry.semconv.trace.attributes.SemanticAttributes.NetTransportValues.IP_TCP;

@Internal
final class MicronautHttpNetClientAttributesGetter
    implements NetClientAttributesGetter<MutableHttpRequest<Object>, HttpResponse<Object>> {

    public InetSocketAddress getAddress(MutableHttpRequest<Object> request) {
        return request.getRemoteAddress();
    }

    @Override
    public String transport(MutableHttpRequest<Object> request,
                            @Nullable HttpResponse<Object> response) {
        return IP_TCP;
    }

    @Override
    public String peerName(MutableHttpRequest<Object> request,
                                 @Nullable HttpResponse<Object> response) {

        String serviceId = request.getAttribute(SERVICE_ID).orElse("/").toString();
        if (!serviceId.contains("/")) {
            return serviceId;
        }

        return getAddress(request).getHostString();
    }

    @Override
    public Integer peerPort(MutableHttpRequest<Object> request,
                                  @Nullable HttpResponse<Object> response) {
        return getAddress(request).getPort();
    }

    @Override
    public String peerIp(MutableHttpRequest<Object> request,
                               @Nullable HttpResponse<Object> response) {
        InetSocketAddress address = getAddress(request);
        InetAddress remoteAddress = address.getAddress();
        if (remoteAddress != null) {
            return remoteAddress.getHostAddress();
        }
        return null;
    }
}
