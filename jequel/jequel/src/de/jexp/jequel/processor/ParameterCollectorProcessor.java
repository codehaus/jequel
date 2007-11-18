package de.jexp.jequel.processor;

import de.jexp.jequel.expression.Expression;
import de.jexp.jequel.expression.ParamExpression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mh14 @ jexp.de
 * @since 24.10.2007 00:16:48 (c) 2007 jexp.de
 */
public class ParameterCollectorProcessor extends AbstractExpressionProcessor<List<ParamExpression>> {
    List<ParamExpression> paramExpressions = new LinkedList<ParamExpression>();
    List<ParamExpression> namedExpressions = new LinkedList<ParamExpression>();

    public List<ParamExpression> getResult() {
        final List<ParamExpression> result = new ArrayList<ParamExpression>(paramExpressions);
        result.addAll(namedExpressions);
        return result;
    }

    public List<ParamExpression> getParamExpressions() {
        return paramExpressions;
    }

    public List<ParamExpression> getNamedExpressions() {
        return namedExpressions;
    }

    protected void doProcess(final Expression<?> expression) {
        if (expression instanceof ParamExpression) {
            final ParamExpression paramExpression = (ParamExpression) expression;
            if (paramExpression.isNamedExpression()) {
                namedExpressions.add(paramExpression);
            } else {
                paramExpressions.add(paramExpression);
            }
        }
    }

}
