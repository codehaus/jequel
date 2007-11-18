package de.jexp.bricksandmortar.results;

import de.jexp.bricksandmortar.StepResult;

/**
 * Created by mh14 on 06.07.2007 14:39:04
 */
public class TextStepResult extends StepResult<CharSequence> {
    public TextStepResult(final String name, final CharSequence charSequence) {
        super(name, CharSequence.class, charSequence);
    }

    public CharSequence textify() {
        return getResult();
    }
}
