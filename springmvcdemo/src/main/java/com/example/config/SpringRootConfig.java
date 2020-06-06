package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * SpringContext中相关的bean
 *
 * @return
 */
@Configuration
@ComponentScan("com.example.service")
public class SpringRootConfig {
}
