package de.jexp.bricksandmortar.results;

import de.jexp.bricksandmortar.StepResult;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by mh14 on 06.07.2007 14:36:35
 */
public class ErrorStepResult extends StepResult<Throwable> {
    public ErrorStepResult(final String name, final Throwable t) {
        super(name, t);
    }

    public CharSequence textify() {
        final StringWriter sw=new StringWriter();
        getResult().printStackTrace(new PrintWriter(sw));
        return sw.getBuffer();
    }
}
