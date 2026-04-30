package com.TreasureHunter.CommonLib.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackages = "com.TreasureHunter.CommonLib")
public class CommonLibAutoConfiguration {
    // Không cần code gì, chỉ cần annotation @AutoConfiguration
    // và @ComponentScan sẽ tự động scan và register các @Component, @Service, @Repository, v.v.
}

