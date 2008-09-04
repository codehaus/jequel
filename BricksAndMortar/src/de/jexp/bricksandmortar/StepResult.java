package de.jexp.bricksandmortar;

/**
 * Created by mh14 on 06.07.2007 14:27:47
 */
public class StepResult<T> {
    private final String name;
    private final Class resultType;
    private T result;

    public StepResult(final String name, final Class resultType, final T result) {
        this(name, resultType);
        this.result = result;
    }

    public StepResult(final String name, final T result) {
        this(name, result.getClass(), result);
    }

    public StepResult(final String name, final Class resultType) {
        this.name = name;
        this.resultType = resultType;
    }

    public String getName() {
        return name;
    }

    public void setResult(final T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public Class getResultType() {
        return resultType;
    }

    public CharSequence textify() {
        return "StepResult: "+getName()+" / "+getResultType().getSimpleName();
    }

    public boolean isEmpty() {
        return false;
    }
}
