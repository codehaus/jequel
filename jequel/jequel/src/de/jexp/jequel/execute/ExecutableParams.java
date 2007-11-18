package de.jexp.jequel.execute;

import java.util.Collection;
import java.util.Map;

/**
 * @author mh14 @ jexp.de
 * @since 03.11.2007 16:37:09 (c) 2007 jexp.de
 */
public interface ExecutableParams {
    boolean hasParams();

    boolean hasOnlyNamed();

    int getParamCount();

    Collection<?> getParamValues();

    Collection<?> getParamNames();

    Map<String, Object> getNamedParams();

    void addParams(final ExecutableParams executableParams);
}
