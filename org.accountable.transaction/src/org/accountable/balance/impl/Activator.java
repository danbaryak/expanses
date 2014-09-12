package org.accountable.balance.impl;

import org.accountable.balance.BalanceService;
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
				.setInterface(BalanceService.class.getName(), null)
				.setImplementation(BalanceServiceImpl.class)
				.add(createServiceDependency().setService(MongoDBService.class)
						.setRequired(true)));
	}

}
