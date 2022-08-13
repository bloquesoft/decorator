package io.github.bloquesoft.decorator.common.register;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SyncRegister<Key, Content extends Registrable<Key>> implements Register<Key, Content> {

    private final Map<Key, Content> map = new HashMap<>();

    @Override
    public void register(Content content) {
        if (!map.containsKey(content.getContentKey())) {
            synchronized (this) {
                if (!map.containsKey(content.getContentKey())) {
                    map.put(content.getContentKey(), content);
                    log.info(content.getContentKey() + " be registered successful.");
                }
            }
        } else {
            log.error(content.getContentKey().toString() + " could not be repeated register.");
        }
    }

    @Override
    public Content get(Key id) {
        return map.get(id);
    }

    @Override
    public Boolean contain(Key key) {
        return map.containsKey(key);
    }
}
