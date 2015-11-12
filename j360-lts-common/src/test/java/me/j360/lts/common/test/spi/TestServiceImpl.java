package me.j360.lts.common.test.spi;


import me.j360.lts.common.support.Config;


public class TestServiceImpl implements TestService {

    public TestServiceImpl() {
        System.out.println("1111111");
    }

    @Override
    public void sayHello(Config config) {
        System.out.println("1");
    }

}
