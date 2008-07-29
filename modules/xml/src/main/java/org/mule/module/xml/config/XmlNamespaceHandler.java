/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.xml.config;

import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.config.spring.parsers.collection.ChildMapEntryDefinitionParser;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.config.spring.parsers.specific.RouterDefinitionParser;
import org.mule.config.spring.parsers.specific.TransformerDefinitionParser;
import org.mule.module.xml.filters.IsXmlFilter;
import org.mule.module.xml.filters.JXPathFilter;
import org.mule.module.xml.filters.JaxenFilter;
import org.mule.module.xml.routing.FilteringXmlMessageSplitter;
import org.mule.module.xml.routing.RoundRobinXmlSplitter;
import org.mule.module.xml.transformer.XmlToOutputHandler;
import org.mule.module.xml.transformer.DomDocumentToXml;
import org.mule.module.xml.transformer.JXPathExtractor;
import org.mule.module.xml.transformer.ObjectToXml;
import org.mule.module.xml.transformer.XmlPrettyPrinter;
import org.mule.module.xml.transformer.XmlToDomDocument;
import org.mule.module.xml.transformer.XmlToObject;

public class XmlNamespaceHandler extends AbstractMuleNamespaceHandler
{

    public void init()
    {
        registerBeanDefinitionParser("jxpath-filter", new ChildDefinitionParser("filter", JXPathFilter.class));
        registerBeanDefinitionParser("jaxen-filter", new ChildDefinitionParser("filter", JaxenFilter.class));
        registerBeanDefinitionParser("is-xml-filter", new ChildDefinitionParser("filter", IsXmlFilter.class));
        registerBeanDefinitionParser("message-splitter", new RouterDefinitionParser(FilteringXmlMessageSplitter.class));
        registerMuleBeanDefinitionParser("round-robin-splitter", new RouterDefinitionParser(RoundRobinXmlSplitter.class)).addAlias("endpointFiltering", "enableEndpointFiltering");
        registerBeanDefinitionParser("dom-to-xml-transformer", new TransformerDefinitionParser(DomDocumentToXml.class));
        registerBeanDefinitionParser("dom-to-output-handler-transformer", new TransformerDefinitionParser(XmlToOutputHandler.class));
        registerBeanDefinitionParser("jxpath-extractor-transformer", new TransformerDefinitionParser(JXPathExtractor.class));
        registerBeanDefinitionParser("object-to-xml-transformer", new TransformerDefinitionParser(ObjectToXml.class));
        registerBeanDefinitionParser("xml-to-dom-transformer", new TransformerDefinitionParser(XmlToDomDocument.class));
        registerBeanDefinitionParser("xml-to-object-transformer", new TransformerDefinitionParser(XmlToObject.class));
        registerBeanDefinitionParser("xml-prettyprinter-transformer", new TransformerDefinitionParser(XmlPrettyPrinter.class));
        registerBeanDefinitionParser("xslt-transformer", new XsltTransformerDefinitionParser());
        registerBeanDefinitionParser("namespace", new ChildMapEntryDefinitionParser("namespaces", "prefix", "uri"));
        registerBeanDefinitionParser("context-property", new ChildMapEntryDefinitionParser("contextProperties", "key", "value"));
        registerBeanDefinitionParser("xslt-text", new XsltTextDefinitionParser("xslt", String.class));
    }

}

