package de.jexp.bricksandmortar.results;

import de.jexp.bricksandmortar.StepResult;

/**
 * Created by mh14 on 06.07.2007 14:36:35
 */
public class ErrorStepResult extends StepResult<Exception> {
    public ErrorStepResult(final String name, final Exception e) {
        super(name,e);
    }

    public CharSequence textify() {
        return getResult().getLocalizedMessage();
    }
}
