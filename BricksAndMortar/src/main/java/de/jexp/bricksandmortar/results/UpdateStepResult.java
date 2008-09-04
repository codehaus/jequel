package de.jexp.bricksandmortar.results;

import de.jexp.bricksandmortar.StepResult;

/**
 * @author mh14 @ jexp.de
 * @since 15.11.2007 08:01:36 (c) 2007 jexp.de
 */
public class UpdateStepResult extends StepResult<Integer> {
    public UpdateStepResult(final String name, final int updatedRows) {
        super(name,Integer.class, updatedRows);
    }

    public CharSequence textify() {
        return "Updated Rows: "+getResult();
    }
}
