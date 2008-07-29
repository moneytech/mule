/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.registry;

import org.mule.MuleServer;
import org.mule.RegistryContext;
import org.mule.api.MuleException;
import org.mule.api.MuleRuntimeException;
import org.mule.api.agent.Agent;
import org.mule.api.config.MuleProperties;
import org.mule.api.context.MuleContextAware;
import org.mule.api.endpoint.EndpointBuilder;
import org.mule.api.endpoint.EndpointFactory;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.registry.RegistrationException;
import org.mule.api.registry.Registry;
import org.mule.config.i18n.CoreMessages;
import org.mule.util.UUID;
import org.mule.util.expression.ExpressionEvaluatorManager;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractRegistry implements Registry
{
    /** the unique id for this Registry */
    private String id;

    protected transient Log logger = LogFactory.getLog(getClass());

    /** Default Constructor */
    protected AbstractRegistry(String id)
    {
        if (id == null)
        {
            throw new MuleRuntimeException(CoreMessages.objectIsNull("RegistryID"));
        }
        this.id = id;
    }

    public final synchronized void dispose()
    {
        try
        {
            doDispose();
            // TODO Isn't there a better place for this?
            ExpressionEvaluatorManager.clearEvaluators();
        }
        catch (Exception e)
        {
            logger.error("Failed to cleanly dispose: " + e.getMessage(), e);
        }
    }

    abstract protected void doInitialise() throws InitialisationException;

    abstract protected void doDispose();

    public final void initialise() throws InitialisationException
    {
        if (id == null)
        {
            logger.warn("No unique id has been set on this registry");
            id = UUID.getUUID();
        }
        try
        {
            doInitialise();
        }
        catch (InitialisationException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new InitialisationException(e, this);
        }
    }

    public Object lookupObject(Class type) throws RegistrationException
    {
        // Accumulate objects from all registries.
        Collection objects = lookupObjects(type);
        
        if (objects.size() == 1)
        {
            return objects.iterator().next();
        }
        else if (objects.size() > 1)
        {
            throw new RegistrationException("More than one object of type " + type + " registered but only one expected.");
        }
        else
        {
            return null;
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // Registry Metadata
    // /////////////////////////////////////////////////////////////////////////

    public final String getRegistryId()
    {
        return id;
    }
}
