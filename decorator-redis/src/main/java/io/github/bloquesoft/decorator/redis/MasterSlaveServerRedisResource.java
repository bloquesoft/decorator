package io.github.bloquesoft.decorator.redis;

import lombok.Getter;

public class MasterSlaveServerRedisResource extends AbstractRedisResource {

    @Getter
    private final String slaveAddress;

    public MasterSlaveServerRedisResource(String name, String masterAddress, String slaveAddress, Integer database, String username, String password) {
        super(name, masterAddress, database, username, password);
        this.slaveAddress = slaveAddress;
    }
}
