/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.standard.processor.attr;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IAttributeNameProcessorMatcher;
import org.thymeleaf.processor.attr.AbstractSingleAttributeModifierAttrProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressionExecutionContext;
import org.thymeleaf.standard.expression.StandardExpressions;

/**
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.0
 *
 */
public abstract class AbstractStandardSingleAttributeModifierAttrProcessor 
        extends AbstractSingleAttributeModifierAttrProcessor {


    private final StandardExpressionExecutionContext expressionExecutionContext;



    protected AbstractStandardSingleAttributeModifierAttrProcessor(final IAttributeNameProcessorMatcher matcher) {
        this(matcher, false);
    }

    protected AbstractStandardSingleAttributeModifierAttrProcessor(final String attributeName) {
        this(attributeName, false);
    }

    // @since 2.1.6
    protected AbstractStandardSingleAttributeModifierAttrProcessor(final IAttributeNameProcessorMatcher matcher, final boolean restrictedExpressionEvaluationMode) {
        this(matcher, (restrictedExpressionEvaluationMode? StandardExpressionExecutionContext.RESTRICTED : StandardExpressionExecutionContext.NORMAL));
    }

    // @since 2.1.6
    protected AbstractStandardSingleAttributeModifierAttrProcessor(final String attributeName, final boolean restrictedExpressionEvaluationMode) {
        this(attributeName, (restrictedExpressionEvaluationMode? StandardExpressionExecutionContext.RESTRICTED : StandardExpressionExecutionContext.NORMAL));
    }

    // @since 2.1.7
    protected AbstractStandardSingleAttributeModifierAttrProcessor(final IAttributeNameProcessorMatcher matcher, final StandardExpressionExecutionContext expressionExecutionContext) {
        super(matcher);
        this.expressionExecutionContext = expressionExecutionContext;
    }

    // @since 2.1.7
    protected AbstractStandardSingleAttributeModifierAttrProcessor(final String attributeName, final StandardExpressionExecutionContext expressionExecutionContext) {
        super(attributeName);
        this.expressionExecutionContext = expressionExecutionContext;
    }



    

    @Override
    protected String getTargetAttributeValue(
            final Arguments arguments, final Element element, final String attributeName) {

        final String attributeValue = element.getAttributeValue(attributeName);

        final Configuration configuration = arguments.getConfiguration();
        final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);

        final IStandardExpression expression = expressionParser.parseExpression(configuration, arguments, attributeValue);

        final Object result = expression.execute(configuration, arguments, this.expressionExecutionContext);
        return (result == null? "" : result.toString());

    }




    @Override
    protected boolean recomputeProcessorsAfterExecution(final Arguments arguments,
            final Element element, final String attributeName) {
        return false;
    }


    
}
