package bidir.conifgfile;

import java.util.Map;

public interface ServiceConfigurationListener {
	public void handleServiceConfiguration(Map<String, String> configuration);
}
