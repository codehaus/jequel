package de.jexp.bricksandmortar.results;

import de.jexp.bricksandmortar.util.TextUtils;
import de.jexp.bricksandmortar.StepResult;

import java.util.Collection;
import java.util.Map;

/**
 * Created by mh14 on 06.07.2007 14:40:08
 */
public class ListStepResult extends StepResult<Collection<Map<String,?>>> {
    public ListStepResult(final String name, final Collection<Map<String, ?>> result) {
        super(name, result);
    }

    public CharSequence textify() {
        return TextUtils.createText(getResult());
    }
}
