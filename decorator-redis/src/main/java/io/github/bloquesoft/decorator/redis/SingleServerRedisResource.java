package io.github.bloquesoft.decorator.redis;

public class SingleServerRedisResource extends AbstractRedisResource {

    public SingleServerRedisResource(String name, String masterAddress, Integer database, String username, String password) {
        super(name, masterAddress, database, username, password);
    }

    public SingleServerRedisResource(String name, String masterAddress) {
        this(name, masterAddress, 0, "", "");
    }
}