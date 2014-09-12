package bidir.systemparameters;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;

import bidir.configurator.ServiceConfigurator;
import bidir.conifgfile.ServiceConfigurationListener;

public class Activator extends DependencyActivatorBase {

	@Override
	public void destroy(BundleContext context, DependencyManager manager)
			throws Exception {

	}

	@Override
	public void init(BundleContext context, DependencyManager manager)
			throws Exception {

		manager.add(createComponent()
				.setInterface(ServiceConfigurationListener.class.getName(),
						null)
				.setImplementation(SystemParameters.class)
				.add(createServiceDependency()
						.setService(ServiceConfigurator.class)
						.setRequired(true)));
	}
}
