package bidir.systemparameters;

import java.util.HashMap;
import java.util.Map;

import bidir.configurator.ServiceConfigurator;
import bidir.conifgfile.ServiceConfigurationListener;

public class SystemParameters implements ServiceConfigurationListener {

	private volatile ServiceConfigurator serviceConfigurator;
	

	public void start() {
		Map<String, String> configuration = new HashMap<>();
		configuration.put("someKey", "someValue");
		serviceConfigurator.configureService(configuration);
	}
	@Override
	public void handleServiceConfiguration(Map<String, String> configuration) {
		System.out.println("handleServiceConfiguration() called");
		
	}
	
}
