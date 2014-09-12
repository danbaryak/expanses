package bidir.unitservice.config;

import java.util.Map;

import bidir.configurator.ServiceConfigurator;
import bidir.conifgfile.ServiceConfigurationListener;

public class UnitServiceConfigurator implements ServiceConfigurator {

	private volatile ServiceConfigurationListener configurationHandler;
	
	@Override
	public void configureService(Map<String, String> serviceProperties) {
		System.out.println("configureService() called");
	}

}
