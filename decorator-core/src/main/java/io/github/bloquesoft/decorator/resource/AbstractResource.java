package io.github.bloquesoft.decorator.resource;

import io.github.bloquesoft.decorator.common.register.Registrable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class AbstractResource implements Registrable<String>
{
    @Getter
    private String name;

    @Override
    public String getContentKey()
    {
        return this.name;
    }
}