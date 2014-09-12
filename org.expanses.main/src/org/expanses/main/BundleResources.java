package org.expanses.main;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes JS and CSS resources for a specific OSGI bundle.
 * 
 * @author Dan Bar-Yaakov
 */
public class BundleResources implements Comparable<BundleResources> {
	private String bundleName;
	private List<String> bundleDependencies = new ArrayList<>();
	
	private List<String> jsResources = new ArrayList<>();
	private List<String> jsLibResources = new ArrayList<>();
	
	private List<String> cssResources = new ArrayList<>();
	private List<String> cssLibResources = new ArrayList<>();
	
	public BundleResources(String bundleName) {
		this.setBundleName(bundleName);
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public List<String> getBundleDependencies() {
		return bundleDependencies;
	}

	public void setBundleDependencies(List<String> bundleDependencies) {
		this.bundleDependencies = bundleDependencies;
	}

	public List<String> getJsResources() {
		return jsResources;
	}

	public void setJsResources(List<String> jsResources) {
		this.jsResources = jsResources;
	}

	public List<String> getJsLibResources() {
		return jsLibResources;
	}

	public void setJsLibResources(List<String> jsLibResources) {
		this.jsLibResources = jsLibResources;
	}

	public List<String> getCssResources() {
		return cssResources;
	}

	public void setCssResources(List<String> cssResources) {
		this.cssResources = cssResources;
	}

	public List<String> getCssLibResources() {
		return cssLibResources;
	}

	public void setCssLibResources(List<String> cssLibResources) {
		this.cssLibResources = cssLibResources;
	}
	
	/**
	 * Compares this bundle to another based on its dependencies.
	 */
	public int compareTo(BundleResources other) {
		if (bundleDependencies.contains(other.bundleName)) {
			return 1;
		}
		if (other.bundleDependencies.contains(bundleName)) {
			return -1;
		}
		return 0;
	}
}
	
	
