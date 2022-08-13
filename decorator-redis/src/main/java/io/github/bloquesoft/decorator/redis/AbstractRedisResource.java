package io.github.bloquesoft.decorator.redis;

import io.github.bloquesoft.decorator.resource.AbstractResource;
import lombok.Getter;

public abstract class AbstractRedisResource extends AbstractResource
{
    @Getter
    private final String masterAddress;

    @Getter
    private final String username;

    @Getter
    private final String password;

    @Getter
    private final Integer database;

    public AbstractRedisResource(String name, String masterAddress, Integer database, String username, String password)
    {
        super(name);
        this.masterAddress = masterAddress;
        this.database = database;
        this.username = username;
        this.password = password;
    }
}