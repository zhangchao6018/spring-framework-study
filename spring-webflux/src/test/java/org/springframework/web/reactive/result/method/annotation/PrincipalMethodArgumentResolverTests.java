/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.reactive.result.method.annotation;

import java.security.Principal;

import io.reactivex.Single;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.mock.http.server.reactive.test.MockServerHttpRequest;
import org.springframework.mock.web.test.server.MockServerWebExchange;
import org.springframework.web.method.ResolvableMethod;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.server.ServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link PrincipalMethodArgumentResolver}.
 *
 * @author Rossen Stoyanchev
 */
public class PrincipalMethodArgumentResolverTests {

	private final PrincipalMethodArgumentResolver resolver =
			new PrincipalMethodArgumentResolver(ReactiveAdapterRegistry.getSharedInstance());

	private ResolvableMethod testMethod = ResolvableMethod.on(getClass()).named("handle").build();


	@Test
	public void supportsParameter() {
		assertThat(this.resolver.supportsParameter(this.testMethod.arg(Principal.class))).isTrue();
		assertThat(this.resolver.supportsParameter(this.testMethod.arg(Mono.class, Principal.class))).isTrue();
		assertThat(this.resolver.supportsParameter(this.testMethod.arg(Single.class, Principal.class))).isTrue();
	}


	@Test
	public void resolverArgument() {
		BindingContext context = new BindingContext();
		Principal user = () -> "Joe";
		ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/"))
				.mutate().principal(Mono.just(user)).build();

		MethodParameter param = this.testMethod.arg(Principal.class);
		Object actual = this.resolver.resolveArgument(param, context, exchange).block();
		assertThat(actual).isSameAs(user);

		param = this.testMethod.arg(Mono.class, Principal.class);
		actual = this.resolver.resolveArgument(param, context, exchange).block();
		assertThat(Mono.class.isAssignableFrom(actual.getClass())).isTrue();
		assertThat(((Mono<?>) actual).block()).isSameAs(user);

		param = this.testMethod.arg(Single.class, Principal.class);
		actual = this.resolver.resolveArgument(param, context, exchange).block();
		assertThat(Single.class.isAssignableFrom(actual.getClass())).isTrue();
		assertThat(((Single<?>) actual).blockingGet()).isSameAs(user);
	}


	@SuppressWarnings("unused")
	void handle(
			Principal user,
			Mono<Principal> userMono,
			Single<Principal> singleUser) {
	}

}
