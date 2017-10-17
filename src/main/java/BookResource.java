import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class BookResource extends ServerResource {

	@Get
	public String represent() {
		String isbn = getAttribute("isbn");
		return String.format("Book with ISBN number '%s'", isbn);
	}

}