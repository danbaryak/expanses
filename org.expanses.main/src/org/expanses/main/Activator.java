package org.expanses.main;

import java.util.Properties;

import javax.servlet.Servlet;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

public class Activator extends DependencyActivatorBase {
	@Override
	public void destroy(BundleContext context, DependencyManager manager)
			throws Exception {
		System.out.println("in destroy");
	}

	@Override
	public void init(BundleContext context, DependencyManager manager)
			throws Exception {
		System.out.println("In expanses main activator");
		Properties props = new Properties();
		props.put("alias", "/");
		manager.add(createComponent()
				.setInterface(Servlet.class.getName(), props)
				.setImplementation(MainServlet.class)
				.add(createBundleDependency().setFilter("(X-SPA-JS=*)")
						.setCallbacks("jsBundleAdded", "jsBundleRemoved")
						.setStateMask(Bundle.ACTIVE))
				.add(createBundleDependency().setFilter("(X-SPA-CSS=*)")
						.setCallbacks("cssBundleAdded", "cssBundleRemoved")
						.setStateMask(Bundle.ACTIVE))
				.add(createBundleDependency().setFilter("(X-SPA-JSLIB=*)")
						.setCallbacks("jsLibBundleAdded", "jsLibBundleRemoved")
						.setStateMask(Bundle.ACTIVE))
				.add(createBundleDependency()
						.setFilter("(X-SPA-CSSLIB=*)")
						.setCallbacks("cssLibBundleAdded",
								"cssLibBundleRemoved")
						.setStateMask(Bundle.ACTIVE))
				.add(createBundleDependency()
						.setFilter("(X-SPA-Main=*)")
						.setCallbacks("mainAdded",
								"mainRemoved")
						.setStateMask(Bundle.ACTIVE)));

		System.out.println("Initialized");
	}

}
