package de.jexp.bricksandmortar;

import java.io.IOException;

/**
 * @author mh14 @ jexp.de
 * @since 17.11.2007 00:26:17 (c) 2007 jexp.de
 */
public interface StepResultWriter {
    void writeFile(String paramName, int count) throws IOException;
}
