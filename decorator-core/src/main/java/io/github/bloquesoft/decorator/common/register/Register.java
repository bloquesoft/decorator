package io.github.bloquesoft.decorator.common.register;

public interface Register<Key, Content extends Registrable<Key>>  {

    void register(Content content);

    Content get(Key key);

    Boolean contain(Key key);
}
