import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    private static List<Employee> parseXML(String path) {
        List<Employee> employees = new ArrayList<>();
        HashMap<String, Node> nodesValues = new HashMap<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(path));

            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                if (Node.ELEMENT_NODE == nodeList.item(i).getNodeType()){
                    Element element = (Element) nodeList.item(i);
                    employees.add(new Employee(Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent())
                        ,element.getElementsByTagName("firstName").item(0).getTextContent()
                        ,element.getElementsByTagName("lastName").item(0).getTextContent()
                        ,element.getElementsByTagName("country").item(0).getTextContent()
                        ,Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent())
                        )
                    );
                }
            }

        } catch(IOException | SAXException | ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }

        return employees;

    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Type listType = new TypeToken<List<Employee>>() {}.getType();

        return gson.toJson(list,listType);

    }

    private static void writeString(List<Employee> list, String fileName) {
        try(FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(listToJson(list));
            fileWriter.flush();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {

        List<Employee> ree = parseXML("data.xml");

        writeString(ree, "data2.json");

    }
}
