/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.util.concurrent;


public class DaemonThreadFactory extends NamedThreadFactory
{

    public DaemonThreadFactory(String name)
    {
        super(name);
    }

    public Thread newThread(Runnable runnable)
    {
        Thread t = super.newThread(runnable);
        t.setDaemon(true);
        return t;
    }

}
