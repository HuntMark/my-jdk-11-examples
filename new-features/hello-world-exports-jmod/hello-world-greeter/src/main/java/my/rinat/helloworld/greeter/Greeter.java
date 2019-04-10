package my.rinat.helloworld.greeter;

import my.rinat.helloworld.greeter.internal.GreeterImpl;

public interface Greeter {

    static Greeter newInstance() {
        return new GreeterImpl();
    }

    String greet(String subject);
}
