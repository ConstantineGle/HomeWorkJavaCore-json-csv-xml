import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        //CSV - JSON парсер (задача 1)
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileNameCsv = "data.csv";
        List<Employee> listCsv = FileTypeMethods.parseCSV(columnMapping, fileNameCsv);
        String jsonCsv = FileTypeMethods.listToJson(listCsv);
        FileTypeMethods.writeString(jsonCsv, "data.json");

        //XML - JSON парсер (задача 2)
        String fileNameXml = "data.xml";
        List<Employee> listXml = FileTypeMethods.parseXml(fileNameXml);
        String jsonXml = FileTypeMethods.listToJson(listXml);
        FileTypeMethods.writeString(jsonXml, "data2.json");

        //JSON парсер (задача *)
        String json = FileTypeMethods.readString("data.json");
        List<Employee> listEmployee = FileTypeMethods.jsonToList(json);
        listEmployee.forEach(System.out::println);

    }
}
