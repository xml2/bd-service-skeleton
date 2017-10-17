import org.restlet.data.Status;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class SearchResource extends ServerResource {

	@Get
	public String represent() {
		String searchTerm = getQueryValue("searchTerm");
		if (searchTerm == null) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Required parameter 'searchTerm' is missing");
		}
		return String.format("Books matching search term '%s'", searchTerm);
	}

}