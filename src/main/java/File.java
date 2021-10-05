import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.impl.STRefImpl;


import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class File {

    public int page;
    public int per_page;
    public int total;
    public int total_pages;
    public List<User> data;
    public Support support;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }




    public static File readFromJsonFile() {

        File fileRead = null;
        try {
            JSONParser parser = new JSONParser();
            //Use JSONObject for simple JSON and JSONArray for array of JSON.
            JSONObject json = (JSONObject) parser.parse(
                    new FileReader("users.json"));//path to the JSON file.

//            JSONArray jsonArray = (JSONArray) data.get("data");
//           List<User> list = new LinkedList<>();
//            for(int i=0;i<jsonArray.size();i++){
//               User user = new User();
//               user.avatar = (String) ((JSONObject) jsonArray.get(i)).get("avatar");
//               System.out.println(user.avatar);
//               list.add(user);
//           }

            ObjectMapper objectMapper = new ObjectMapper();
            String data = json.toJSONString();

            fileRead = objectMapper.readValue(data, File.class);




        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return fileRead;
    }

    public static void writeToJsonFileUsersWithFirstLetterFInLastname(File fileRead) {
        System.out.println();
        List<User> listUsers = fileRead.getData();


        List<User> listUsers2 = new ArrayList<>();


        try (FileWriter file = new FileWriter("post.json")) {
            //We can write any JSONArray or JSONObject instance to the file


            JsonFactory factory = new JsonFactory();
            JsonGenerator generator = factory.createGenerator(new java.io.File("post.json"), JsonEncoding.UTF8);

            ObjectMapper objectMapper2 = new ObjectMapper();

            for (User el : listUsers) {
                // save user with lastname stared 'F'
                System.out.println((String) el.getLast_name().subSequence(0, 1));
                if (el.getLast_name().subSequence(0, 1).equals("F")) {
                    listUsers2.add(el);
                }
            }
            objectMapper2.writeValue(generator, listUsers2);
            generator.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeUsersFromJsonFileToExcelFile() {

        try {

            JSONParser parser = new JSONParser();
            //Use JSONObject for simple JSON and JSONArray for array of JSON.
            JSONArray json = (JSONArray) parser.parse(
                    new FileReader("post.json"));//path to the JSON file.
            String data = json.toJSONString();

            System.out.println(data);

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Report");

            int rowCount = 0;
            System.out.println(json.size());
            for (int i = 0; i < json.size(); ++i)
            {
                JSONObject rec = (JSONObject) json.get(i);
                System.out.println(rec);
                Long userId = (Long) rec.get("id");
                String userEmail = (String) rec.get("email");
                String userFirstname = (String) rec.get("first_name");
                String userLastname = (String) rec.get("last_name");
                String userAvatar = (String) rec.get("avatar");


                Row row = sheet.createRow(++rowCount);
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(userId);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(userEmail);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(userFirstname);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue(userLastname);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue(userAvatar);

                System.out.println(userId+"\t"+userEmail+"\t"+userFirstname+"\t"+userLastname+"\t"+userAvatar);
                //want to excel file for this three field
            }


            try (FileOutputStream outputStream = new FileOutputStream("Report.xlsx")) {
                workbook.write(outputStream);
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        writeToJsonFileUsersWithFirstLetterFInLastname(readFromJsonFile());
        writeUsersFromJsonFileToExcelFile();
    }



}
