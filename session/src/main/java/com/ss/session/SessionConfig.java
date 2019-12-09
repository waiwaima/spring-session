package com.ss.session;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapIndexConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;

@EnableHazelcastHttpSession(maxInactiveIntervalInSeconds = 300)
@Configuration
public class SessionConfig {

  @Bean
  public HazelcastInstance hazelcastInstance() {
    Config config = new Config();
    MapAttributeConfig attributeConfig = new MapAttributeConfig()
        .setName(HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
        .setExtractor(PrincipalNameExtractor.class.getName());
    config.getMapConfig(HazelcastIndexedSessionRepository.DEFAULT_SESSION_MAP_NAME)
        .addMapAttributeConfig(attributeConfig).addMapIndexConfig(
        new MapIndexConfig(HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));
    return Hazelcast.newHazelcastInstance(config);
  }

  // disable base64 encoding of the sessionId in the cookie for demostration
	@Bean
	public HttpSessionIdResolver httpSessionIdResolver() {
		CookieHttpSessionIdResolver resolver = new CookieHttpSessionIdResolver();
		DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
		cookieSerializer.setUseBase64Encoding(false);
		resolver.setCookieSerializer(cookieSerializer);
	    return resolver;
	}

}