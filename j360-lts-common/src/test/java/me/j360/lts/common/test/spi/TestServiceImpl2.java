package me.j360.lts.common.test.spi;


import me.j360.lts.common.support.Config;

public class TestServiceImpl2 implements TestService {

    public TestServiceImpl2() {
        System.out.println("2222222");
    }

    @Override
    public void sayHello(Config config) {
        System.out.println("2");
    }
}
