package me.j360.lts.common.extension;

import java.lang.annotation.*;

/**
 * Dubbo采用微内核+插件体系，使得设计优雅，扩展性强。那所谓的微内核+插件体系是如何实现的呢！大家是否熟悉spi(service providerinterface)机制，
 * 即我们定义了服务接口标准，让厂商去实现（如果不了解spi的请谷歌百度下）, jdk通过ServiceLoader类实现spi机制的服务查找功能。
 * */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {

    /**
     * 缺省扩展点名。
     */
    String value() default "";

}