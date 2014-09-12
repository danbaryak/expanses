package bidir.unitservice.config;

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
				.setInterface(ServiceConfigurator.class.getName(),
						null)
				.setImplementation(UnitServiceConfigurator.class)
				.add(createServiceDependency()
						.setService(ServiceConfigurationListener.class)
						.setRequired(false)));
	}
}
