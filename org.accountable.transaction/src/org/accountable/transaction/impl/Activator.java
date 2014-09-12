package org.accountable.transaction.impl;

import org.accountable.balance.BalanceService;
import org.accountable.balance.impl.BalanceServiceImpl;
import org.accountable.expanse.ExpansesService;
import org.accountable.expanse.impl.ExpanseServiceImpl;
import org.accountable.transaction.TransactionService;
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
				.setInterface(TransactionService.class.getName(), null)
				.setImplementation(TransactionServiceImpl.class)
				.add(createServiceDependency().setService(MongoDBService.class)
						.setRequired(true)));
	}
}
