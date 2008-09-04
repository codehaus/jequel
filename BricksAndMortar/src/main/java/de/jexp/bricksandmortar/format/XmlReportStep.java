package de.jexp.bricksandmortar.format;

import de.jexp.bricksandmortar.NamedWorkflowStep;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.results.XmlStepResult;
import org.znerd.xmlenc.LineBreak;
import org.znerd.xmlenc.XMLOutputter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;

/**
 * Created by mh14 on 06.07.2007 11:30:09
 */
public class XmlReportStep extends NamedWorkflowStep {
    private boolean elements;
    private LineBreak lineBreak;
    private String identation;

    public void runStep(final WorkflowContext workflowContext) {
        final Collection<ListStepResult> listStepResults = filterParams(workflowContext, getParamNames(), ListStepResult.class).values();
        for (ListStepResult listStepResult : listStepResults) {
            setOnWorkflowContext(workflowContext, formatAsXml(listStepResult));
        }
    }

    protected XmlStepResult formatAsXml(final ListStepResult result) {
        try {
            final Collection<Map<String, ?>> list = result.getResult();
            final CharSequence data = createDocument(list);
            return new XmlStepResult(getName(), data);
        } catch (IOException e) {
            throw new RuntimeException("Error creating xml document for " + result.getName(), e);
        }
    }

    private CharSequence createDocument(final Collection<Map<String, ?>> list) throws IOException {
        final StringWriter stringWriter = new StringWriter();
        XMLOutputter xml = new XMLOutputter(stringWriter, "UTF-8");
        if (lineBreak != null)
            xml.setLineBreak(lineBreak);
        if (identation != null)
            xml.setIndentation(" ");
        xml.startTag("result");
        for (Map<String, ?> row : list) {
            formatRow(xml, row);
        }
        xml.endDocument();
        return stringWriter.getBuffer();
    }

    private void formatRow(final XMLOutputter xml, final Map<String, ?> row) throws IOException {
        xml.startTag("row");
        for (Map.Entry<String, ?> entry : row.entrySet()) {
            if (elements) {
                xml.startTag(formatName(entry));
                xml.pcdata(formatValue(entry));
                xml.endTag();
            } else {
                xml.attribute(formatName(entry), formatValue(entry));
            }
        }
        xml.endTag();
    }

    private String formatName(final Map.Entry<String, ?> entry) {
        return entry.getKey().toLowerCase();
    }

    private String formatValue(final Map.Entry<String, ?> entry) {
        final Object value = entry.getValue();
        return value == null ? "" : value.toString();
    }

    public void setElements(final boolean elements) {
        this.elements = elements;
    }

    public void setLineBreak(final LineBreak lineBreak) {
        this.lineBreak = lineBreak;
    }

    public void setIdentation(final String identation) {
        this.identation = identation;
    }
}