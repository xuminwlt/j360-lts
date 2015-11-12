package me.j360.lts.common.test.spi;


import me.j360.lts.common.extension.ExtensionLoader;
import me.j360.lts.common.support.Application;
import me.j360.lts.common.support.Config;

/**
 * dubbo SPI Test
 * */
public class MainTest {

    public static void main(String[] args) {

//        TestService testService = ExtensionLoader.getExtensionLoader(TestService.class).getExtension("test2");
        TestService testService = ExtensionLoader.getExtensionLoader(TestService.class).getAdaptiveExtension();
        TestService testService2 = ExtensionLoader.getExtensionLoader(TestService.class).getAdaptiveExtension();
        TestService testService3 = ExtensionLoader.getExtensionLoader(TestService.class).getAdaptiveExtension();
        TestService testService4 = ExtensionLoader.getExtensionLoader(TestService.class).getAdaptiveExtension();

        Config config = new Config();
        testService.sayHello(config);

        config.setParameter("test.type", "test1");
        testService.sayHello(config);
        Application application = new Application() {
        };
    }

}
