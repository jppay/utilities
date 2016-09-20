import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//The following two imports should be used for Java 1.5 and lower
//import au.com.bytecode.opencsv.CSVReader;
//import au.com.bytecode.opencsv.CSVWriter;

//Import for Java 1.6 and above
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class OpenCsvExample {
  
  public static void main(String[] args) {
    //Write to a CSV file
    //File name
    String fileName = "C:\NewReport.csv";
    try {
      CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName)); //CSVWriter object
      String[] aRecord = {"Nike", "USA", "Sports Company"}; //String array record to be written in CSV
      csvWriter.writeNext(aRecord); //Write it to the CSV file
      
      String[] bRecord = {"BMW", "Germany", "Car Manufacturer"}; //Another record
      csvWriter.writeNext(bRecord);
      
      csvWriter.close(); //Close the CSVWriter
      
      //Read the CSV file
      CSVReader csvReader = new CSVReader(new FileReader(fileName), ","); //CSVReader object with the filename and separator
      String[] rowOfData;
      
      while(csvReader.readNext() != null) {
        rowOfData = csvReader.readNext(); //Read data line-by-line
        System.out.println(rowOfData);
      }
      
      List<String[]> rowsOfData = csvReader.readAll(); //Read all lines at once
      for(String[] singleRow : rowsOfData) {
        System.out.println(Arrays.toString(singleRow));
      }
    }
    catch(IOException e) {
      System.out.println("IOException encountered");
      e.printStackTrace();
    }
    
    
  }

}
