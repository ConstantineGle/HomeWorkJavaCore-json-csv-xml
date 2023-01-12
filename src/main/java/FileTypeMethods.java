import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileTypeMethods {

    //parse csv method
    public static List<Employee> parseCSV(String[] columnMapping, String filename) {
        try (CSVReader csvReader = new CSVReader(new FileReader(filename))) {
            ColumnPositionMappingStrategy<Employee> strategy =
                    new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            return csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //parse xml method
    public static List<Employee> parseXml(String xmlFilename) throws ParserConfigurationException, IOException, SAXException {

        List<String> elements = new ArrayList<>();
        List<Employee> list = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(xmlFilename));
        Node root = doc.getDocumentElement();
        NodeList nodeList1 = root.getChildNodes();
        for (int i = 0; i < nodeList1.getLength(); i++) {
            Node node = nodeList1.item(i);
            if (node.getNodeName().equals("employee")) {
                NodeList nodeList2 = node.getChildNodes();
                for (int j = 0; j < nodeList2.getLength(); j++) {
                    Node node_ = nodeList2.item(j);
                    if (Node.ELEMENT_NODE == node_.getNodeType()) {
                        elements.add(node_.getTextContent());
                    }
                }
                list.add(new Employee(Long.parseLong(elements.get(0)),
                        elements.get(1),
                        elements.get(2),
                        elements.get(3),
                        Integer.parseInt(elements.get(4))));
                elements.clear();
            }
        }
        return list;
    }

    //convert list to json
    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        return gson.toJson(list, listType);
    }

    //write json to file
    public static void writeString(String json, String jsonFilename) {
        try (FileWriter file = new FileWriter(jsonFilename)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //read string
    public static String readString(String filename) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s).append("\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return sb.toString();

    }

    //json to object
    public static List<Employee> jsonToList(String json) {
        List<Employee> list = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            Object object = new JSONParser().parse(json);
            JSONArray array = (JSONArray) object;
            for (Object a : array) {
                list.add(gson.fromJson(a.toString(), Employee.class));
            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return list;

    }
}
