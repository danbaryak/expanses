package org.expanses.login.rest;

import org.amdatu.security.tokenprovider.TokenProvider;
import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.expanses.login.api.UserService;
import org.osgi.framework.BundleContext;

public class Activator extends DependencyActivatorBase {

	@Override
	public void destroy(BundleContext arg0, DependencyManager arg1)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(BundleContext arg0, DependencyManager manager)
			throws Exception {
		
		manager.add(createComponent()
				.setInterface(Object.class.getName(), null)
				.setImplementation(UserResource.class)
				.add(createServiceDependency().setService(UserService.class).setRequired(true)));
		manager.add(createComponent()
				.setInterface(Object.class.getName(), null)
				.setImplementation(LoginResource.class)
				.add(createServiceDependency().setService(TokenProvider.class).setRequired(true)));
				
		
	}

}
