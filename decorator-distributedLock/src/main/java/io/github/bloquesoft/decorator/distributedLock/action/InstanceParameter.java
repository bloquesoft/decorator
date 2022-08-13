package io.github.bloquesoft.decorator.distributedLock.action;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@AllArgsConstructor
public class InstanceParameter {

    @Getter
    private String parameterName;

    @Getter
    private Integer parameterIndex;

    public static InstanceParameter create(Method method, String parameterName)
    {
        Assert.state(!StringUtils.isEmpty(parameterName), "parameterName could not be empty, method:" + method.getName() + " class:" + method.getDeclaringClass().getName());
        int index = -1;
        InstanceParameter lockObjectParameter = null;
        for(Parameter p : method.getParameters())
        {
            index++;
            if (p.getName().equals(parameterName))
            {
                lockObjectParameter = new InstanceParameter(parameterName, index);
                break;
            }
        }
        Assert.notNull(lockObjectParameter, "Could not find parameter define, name:" + parameterName + "");
        return lockObjectParameter;
    }
}
