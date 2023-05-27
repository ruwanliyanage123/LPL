package lk.premier.league.lpl.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVFileReader {
    //todo: need to singleton
    public void readCSVFile() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("/Users/ruwan/Documents/PROJECT/lpl/src/main/resources/data/match_result.csv"));
        sc.useDelimiter(",");
        while (sc.hasNext())
        {
            System.out.print(sc.next());
        }
        sc.close();
    }
}
