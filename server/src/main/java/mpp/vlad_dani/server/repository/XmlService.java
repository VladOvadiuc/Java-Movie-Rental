package mpp.vlad_dani.server.repository;

import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.domain.Rental;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class XmlService {
    public static List<Movie> loadMoviesData(String fileName) throws Exception {
        return loadData(fileName, XmlService::createMovie);
    }

    public static List<Client> loadClientsData(String fileName) throws Exception {
        return loadData(fileName, XmlService::createClient);
    }

    public static List<Rental> loadRentalsData(String fileName) throws Exception {
        return loadData(fileName, XmlService::createRentals);
    }

    private static <T> List<T> loadData(String path, Function<Element, T> function) throws Exception {
        String xml = Files.lines(Paths.get(path)).collect(Collectors.joining(""));
        if ( xml.isEmpty() )
            return null;
        List<T> listOfData = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(path);
        Element root = document.getDocumentElement();

        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node dataNode = nodes.item(i);
            if (dataNode.getNodeType() == Node.ELEMENT_NODE) {
                T object = function.apply((Element) dataNode);
                listOfData.add(object);
            }
        }
        return listOfData;
    }

    public static void saveMovieData(Document document, Movie movie, String filename) throws Exception {
        Element root = document.getDocumentElement();
        if (root == null)
            root = document.createElement("moviestore");

        Element movieElement = document.createElement("movie");
        movieElement.setAttribute("id", String.valueOf(movie.getId()));
        root.appendChild(movieElement);

        appendChildWithText(document, movieElement, "title", movie.getTitle());
        appendChildWithText(document, movieElement, "year",
                String.valueOf(movie.getYear()));
        appendChildWithText(document, movieElement, "genre", movie.getGenre());

        appendChildWithText(document, movieElement, "duration",
                String.valueOf(movie.getDuration()));
        appendChildWithText(document, movieElement, "rating",
                String.valueOf(movie.getRating()));


        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult(
                new FileOutputStream(filename)));
    }

    public static void saveClientData(Document document, Client client, String fileName) throws Exception {
        Element root = document.getDocumentElement();
        if (root == null)
            root = document.createElement("moviestore");

        Element clientElement = document.createElement("client");
        clientElement.setAttribute("id", String.valueOf(client.getId()));
        root.appendChild(clientElement);

        appendChildWithText(document, clientElement, "name", client.getName());

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult(
                new FileOutputStream(fileName)));
    }



    public static void saveRentalData(Document document, Rental rental, String fileName) throws Exception {
        Element root = document.getDocumentElement();
        if ( root == null)
            root = document.createElement("moviestore");

        Element rentalElement = document.createElement("rental");
        rentalElement.setAttribute("id", String.valueOf(rental.getId()));
        root.appendChild(rentalElement);

        appendChildWithText(document, rentalElement, "movieId", String.valueOf(rental.getMovie()));
        appendChildWithText(document, rentalElement, "clientId", String.valueOf(rental.getClient()));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult(
                new FileOutputStream(fileName)));
    }
    private static Rental createRentals(Element rentalNode) {
        String id = rentalNode.getAttribute("id");
        Rental rental = new Rental();

        rental.setId(Integer.valueOf(id));
        rental.setMovie(Integer.valueOf(getTextFromTagName(rentalNode, "movieId")));
        rental.setClient(Integer.valueOf(getTextFromTagName(rentalNode, "clientId")));
        return rental;


    }
    private static void appendChildWithText(Document document, Node parent, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    private static Movie createMovie(Element movieNode) {
        String id = movieNode.getAttribute("id");
        Movie b = new Movie();
        b.setId(Integer.valueOf(id));

        b.setTitle(getTextFromTagName(movieNode, "title"));
        b.setGenre(getTextFromTagName(movieNode, "genre"));
        b.setYear(Integer.valueOf(getTextFromTagName(movieNode, "year")));
        b.setDuration(Integer.valueOf(getTextFromTagName(movieNode, "duration")));
        b.setRating(Float.valueOf(getTextFromTagName(movieNode, "rating")));

        return b;
    }

    private static Client createClient(Element clientNode) {
        String id = clientNode.getAttribute("id");
        Client client = new Client();

        client.setId(Integer.valueOf(id));
        client.setName(getTextFromTagName(clientNode, "name"));
        return client;
    }

    private static String getTextFromTagName(Element element, String tagName) {
        NodeList elements = element.getElementsByTagName(tagName);
        return elements.item(0).getTextContent();
    }
}
