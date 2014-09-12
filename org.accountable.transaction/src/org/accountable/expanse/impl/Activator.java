package org.accountable.expanse.impl;

import org.accountable.expanse.ExpansesService;
import org.amdatu.mongo.MongoDBService;
import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;

public class Activator extends DependencyActivatorBase {

	@Override
	public void destroy(BundleContext context, DependencyManager manager)
			throws Exception {
		
		
	}

	@Override
	public void init(BundleContext context, DependencyManager manager)
			throws Exception {
		manager.add(createComponent()
				.setInterface(ExpansesService.class.getName(), null)
				.setImplementation(ExpanseServiceImpl.class)
				.add(createServiceDependency().setService(MongoDBService.class)
						.setRequired(true)));
	}

}
