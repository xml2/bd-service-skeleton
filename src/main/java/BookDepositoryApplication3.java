import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;

import org.restlet.data.Protocol;

import org.restlet.routing.Extractor;
import org.restlet.routing.Redirector;
import org.restlet.routing.Router;

public class BookDepositoryApplication3 extends Application {

	static {
		System.setProperty("org.restlet.engine.loggerFacadeClass", "org.restlet.ext.slf4j.Slf4jLoggerFacade");
	}

	public static void main(String[] args) {
		Component component = new Component();
		component.getDefaultHost().attach("/bookdepository", new BookDepositoryApplication3());
		Server server = new Server(Protocol.HTTP, 8111, component);
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.setDefaultMatchingQuery(true);

		Redirector isbnRedirector = new Redirector(
			getContext(),
			"http://www.bookdepository.com/search?searchIsbn={isbn}&advanced=true",
			Redirector.MODE_CLIENT_FOUND
		);
		Extractor isbnExtractor = new Extractor(getContext(), isbnRedirector);
		isbnExtractor.extractFromEntity("isbn", "isbn", true);
		router.attach("/book/{isbn}", isbnExtractor);

		Redirector searchRedirector = new Redirector(
			getContext(),
			"http://www.bookdepository.com/search?searchTerm={term}&search=Find+book",
			Redirector.MODE_CLIENT_FOUND
		);
		Extractor searchExtractor = new Extractor(getContext(), searchRedirector);
		searchExtractor.extractFromQuery("term", "searchTerm", true);
		router.attach("/search?{query}", searchExtractor);

		return router;
	}

}