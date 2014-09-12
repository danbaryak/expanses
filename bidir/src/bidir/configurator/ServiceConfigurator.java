package bidir.configurator;

import java.util.Map;

public interface ServiceConfigurator {
	public void configureService(Map<String, String> serviceProperties);
}
