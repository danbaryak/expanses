package org.accountable.expanse.rest;

import org.accountable.expanse.ExpansesService;
import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;

/**
 * Activator
 */
public class Activator extends DependencyActivatorBase {
	
	/**
	 * OSGI lifecycle method, gets called when bundled is destroyed
	 * @param context the Bundle Context
	 * @param manager the Dependency manager
	 */
	@Override
	public void destroy(BundleContext context, DependencyManager manager)
			throws Exception {
	}

	/**
	 * OSGI lifecycle method, gets called when bundled is initialized. Creates the REST Resource
	 * and it's service dependencies.
	 * 
	 * @param context the Bundle Context
	 * @param manager the Dependency manager
	 */
	@Override
	public void init(BundleContext context, DependencyManager manager)
			throws Exception {		
		
		manager.add(createComponent()
				.setInterface(Object.class.getName(), null)
				.setImplementation(ExpanseResource.class)
				.add(createServiceDependency()
						.setService(ExpansesService.class)
						.setRequired(true)));
		
		
	}
}
