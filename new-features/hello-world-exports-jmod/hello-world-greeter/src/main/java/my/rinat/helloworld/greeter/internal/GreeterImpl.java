package my.rinat.helloworld.greeter.internal;

import my.rinat.helloworld.greeter.Greeter;

public class GreeterImpl implements Greeter {
    @Override
    public String greet(String subject) {
        return "Hello, " + subject + "!";
    }
}
