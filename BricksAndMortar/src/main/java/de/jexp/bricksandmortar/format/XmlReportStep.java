package de.jexp.bricksandmortar.format;

import de.jexp.bricksandmortar.NamedWorkflowStep;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.results.XmlStepResult;
import static de.jexp.bricksandmortar.util.TextUtils.*;
import org.znerd.xmlenc.LineBreak;
import org.znerd.xmlenc.XMLOutputter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
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
            final CharSequence data = createDocument(result.getName(), list);
            return new XmlStepResult(getName(), data);
        } catch (IOException e) {
            throw new RuntimeException("Error creating xml document for " + result.getName(), e);
        }
    }

    private CharSequence createDocument(final String name, final Collection<Map<String, ?>> list) throws IOException {
        XMLOutputter xml = createOutputter();
        xml.startTag("rows");
        xml.attribute("name", name);
        formatRows(xml, list);
        xml.endDocument();
        return ((StringWriter) xml.getWriter()).getBuffer();
    }

    private void formatRows(final XMLOutputter xml, final Collection<Map<String, ?>> list) throws IOException {
        for (Map<String, ?> row : list) {
            formatRow(xml, row);
        }
    }

    private XMLOutputter createOutputter() throws UnsupportedEncodingException {
        final StringWriter stringWriter = new StringWriter();
        XMLOutputter xml = new XMLOutputter(stringWriter, "UTF-8");
        if (lineBreak != null)
            xml.setLineBreak(lineBreak);
        if (identation != null)
            xml.setIndentation(" ");
        return xml;
    }

    private void formatRow(final XMLOutputter xml, final Map<String, ?> row) throws IOException {
        xml.startTag("row");
        for (Map.Entry<String, ?> entry : row.entrySet()) {
            final String name = formatName(entry.getKey());
            final String valueString = formatValue(entry.getValue());
            if (elements) {
                xml.startTag(name);
                xml.pcdata(valueString);
                xml.endTag();
            } else {
                xml.attribute(name, valueString);
            }
        }
        xml.endTag();
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