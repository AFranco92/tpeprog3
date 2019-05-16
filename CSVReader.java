package tpe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
	
    String csvFile = "";
    String line = "";
    String cvsSplitBy = ";";

	public CSVReader(String csvFile) {
		this.csvFile = csvFile;
	}
	
    public void setData() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                String[] items = line.split(cvsSplitBy);

                // ---------------------------------------------
                // Poner el codigo para cargar los datos
                // ---------------------------------------------

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}