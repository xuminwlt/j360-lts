package me.j360.lts.common.test.spi;


import me.j360.lts.common.extension.Adaptive;
import me.j360.lts.common.extension.SPI;
import me.j360.lts.common.support.Config;


@SPI("test2")
public interface TestService {

    @Adaptive("test.type")
    public void sayHello(Config config);

}
