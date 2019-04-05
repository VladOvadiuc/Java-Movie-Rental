package mpp.vlad_dani.common.domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public class EntityFactory {
    public static String clientToFile(Client c) {
        return "" + c.getId() + "," + c.getName();
    }
    public static Client clientFromFile(List<String> data) {
        return new Client(Integer.valueOf(data.get(0).trim()),data.get(1).trim());
    }
    public static String movieToFile(Movie m) {
        return "" + m.getId()+","+m.getTitle()+","+m.getYear()+","+m.getGenre()+","+m.getDuration()+","+m.getRating();
    }
    public static Movie movieFromFile(List<String> data) {
        return new Movie(Integer.valueOf(data.get(0).trim()),data.get(1).trim(),Integer.valueOf(data.get(2).trim()),data.get(3).trim(),Integer.valueOf(data.get(4).trim()),Float.valueOf(data.get(5).trim()));
    }
    public static String rentalToFile(Rental r) {
        return "" + r.getId()+","+r.getMovie()+","+r.getClient()+","+r.getDate();
    }
    public static Rental rentalFromFile(List<String> data) {
        return new Rental(Integer.valueOf(data.get(1).trim()),Integer.valueOf(data.get(2).trim()),Integer.valueOf(data.get(0).trim()));
    }
    private static String getTextFromTagName(Element element, String tagName) {
        NodeList elements = element.getElementsByTagName(tagName);
        Node node = elements.item(0);
        return node.getTextContent();
    }

    private static void appendChildWithText(Document document, Node parent, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }
}
