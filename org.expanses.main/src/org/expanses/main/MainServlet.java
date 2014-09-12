package org.expanses.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.osgi.framework.Bundle;

public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private List<BundleResources> resources = new ArrayList<>();

	private String mainContent;

	public void start() {
		System.out.println("Main serrvlet started");
	}

	public void jsBundleAdded(Bundle bundle) {
		addFiles(getBundleResources(bundle).getJsResources(), bundle,
				String.valueOf(bundle.getHeaders().get("X-SPA-JS")));
	}

	public void jsBundleRemoved(Bundle bundle) {
		System.out.println("Removing JS lib for bundle "
				+ bundle.getSymbolicName());
		removeFiles(getBundleResources(bundle).getJsResources(), bundle,
				String.valueOf(bundle.getHeaders().get("X-SPA-JS")));
	}

	public void jsLibBundleAdded(Bundle bundle) {
		addFiles(getBundleResources(bundle).getJsLibResources(), bundle,
				String.valueOf(bundle.getHeaders().get("X-SPA-JSLIB")));
	}

	public void jsLibBundleRemoved(Bundle bundle) {
		removeFiles(getBundleResources(bundle).getJsLibResources(), bundle,
				String.valueOf(bundle.getHeaders().get("X-SPA-JSLIB")));
	}

	public void cssBundleAdded(Bundle bundle) {
		addFiles(getBundleResources(bundle).getCssResources(), bundle,
				String.valueOf(bundle.getHeaders().get("X-SPA-CSS")));
	}

	public void cssBundleRemoved(Bundle bundle) {
		removeFiles(getBundleResources(bundle).getCssResources(), bundle,
				String.valueOf(bundle.getHeaders().get("X-SPA-CSS")));
	}

	public void cssLibBundleAdded(Bundle bundle) {
		addFiles(getBundleResources(bundle).getCssLibResources(), bundle,
				String.valueOf(bundle.getHeaders().get("X-SPA-CSSLIB")));
	}

	public void cssLibBundleRemoved(Bundle bundle) {
		removeFiles(getBundleResources(bundle).getCssLibResources(), bundle,
				String.valueOf(bundle.getHeaders().get("X-SPA-CSSLIB")));
	}

	public void mainAdded(Bundle bundle) {
		System.out.println("Main added");
		String mainContentPath = String.valueOf(bundle.getHeaders().get(
				"X-SPA-Main"));

		try {
			this.mainContent = IOUtils.toString(bundle.getResource(
					mainContentPath).openStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private BundleResources getBundleResources(Bundle bundle) {
		String bundleName = bundle.getSymbolicName();
		String[] dependencies = String.valueOf(
				bundle.getHeaders().get("Require-Bundle")).split(",");
		BundleResources result = null;
		for (BundleResources res : resources) {
			if (res.getBundleName().equals(bundleName)) {
				result = res;
				break;
			}
		}
		
		if (result == null) {
			result = new BundleResources(bundleName);
			resources.add(result);
		}
		result.setBundleDependencies(Arrays.asList(dependencies));
		Collections.sort(resources);
		System.out.println("Resources order: ");
		for (BundleResources resource : resources) {
			System.out.println(resource.getBundleName());
		}
		return result;
	}

	public void mainRemoved(Bundle bundle) {
		System.out.println("Main removed");
	}

	private void addFiles(List<String> list, Bundle bundle, String pathToAdd) {
		if (pathToAdd.contains(",")) {
			for (String path : pathToAdd.split(",")) {
				addFiles(list, bundle, path);
			}
			return;
		}
		if (pathToAdd.endsWith(".js") || pathToAdd.endsWith(".css") || pathToAdd.startsWith("http")) {
			list.add(pathToAdd);
			return;
		}
		Enumeration paths = bundle.getEntryPaths(pathToAdd);
		while (paths.hasMoreElements()) {
			String path = (String) paths.nextElement();
			addFiles(list, bundle, path);
		}
	}

	private void removeFiles(List<String> list, Bundle bundle,
			String pathToRemove) {
		if (pathToRemove.contains(",")) {
			for (String path : pathToRemove.split(",")) {
				removeFiles(list, bundle, path);
			}
			return;
		}
		if (pathToRemove.endsWith(".js") || pathToRemove.endsWith(".css") || pathToRemove.startsWith("http")) {
			list.remove(pathToRemove);
			return;
		}

		Enumeration paths = bundle.getEntryPaths(pathToRemove);
		while (paths.hasMoreElements()) {
			String path = (String) paths.nextElement();
			removeFiles(list, bundle, path);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");

		out.println("<head>");
		for (BundleResources res : resources) {
			for (String cssFileName : res.getCssLibResources()) {
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\""
						+ cssFileName + "\">");
			}
		}

		for (BundleResources res : resources) {
			for (String cssFileName : res.getCssResources()) {
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\""
						+ cssFileName + "\">");
			}
		}
		for (BundleResources res : resources) {
			for (String scriptName : res.getJsLibResources()) {
				out.println("<script src=\"" + scriptName + "\"></script>");
			}
		}
		out.println("</head>");
		out.println("<body>");
		if (mainContent != null) {
			out.println(mainContent);

		}
		// add scripts

		for (BundleResources res : resources) {
			for (String scriptName : res.getJsResources()) {
				out.println("<script src=\"" + scriptName + "\"></script>");
			}
		}

		out.println("</body>");
		out.println("</html>");
	}

}
