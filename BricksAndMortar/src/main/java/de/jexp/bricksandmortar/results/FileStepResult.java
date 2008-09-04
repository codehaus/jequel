package de.jexp.bricksandmortar.results;

import de.jexp.bricksandmortar.StepResult;

/**
 * Created by mh14 on 06.07.2007 23:08:48
*/
public class FileStepResult extends StepResult<byte[]> {
    private String contentType;
    private final String extension;

    public FileStepResult(final String name, final byte[] bytes, final String extension) {
        super(name, bytes);
        this.extension = extension;
    }

    public String getContentType() {
        return contentType;
    }
    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public String getExtension() {
        return extension;
    }
    public String getFileName() {
        return getName()+"."+getExtension();
    }

    public int getContentSize() {
        final byte[] data = getResult();
        return data == null ? 0 : data.length;
    }

    public CharSequence textify() {
        return String.format("BinaryData: %s Size %d File %s",getContentType(), getContentSize(),getFileName());
    }
}
