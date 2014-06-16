package a1qa.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class CaseReader {

    /** create file with selection of test cases
     * @param path path to file with test cases
     * @param quantity necessary quantity of test cases in the result file
     * @return path to result file
     */
    public String selectCases(File path, int quantity){
        FileReader fileReader;
        String resultsPath = "";
        try {
            // 1. read
            fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            HashSet<String> validLines = new HashSet<String>();
            List<String> initLines = new ArrayList<String>();
            String line = null;
            String columnsLine = "";
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                initLines.add(line);
                if(i == 0){
                    columnsLine = line;
                }else{
                    if(validateLine(line, i, columnsLine.split("\\t").length)){
                        validLines.add(line);
                    }
                }
                i++;
            }
            bufferedReader.close();
            /// n unique

            // 3. array quantity. random select list
            HashSet<String> results = new HashSet<String>();
            if(validLines.size() >= quantity){
                while(results.size() != quantity){
                    results.add(validLines.toArray()[(new Random().nextInt(validLines.size()))].toString());
                }
            }else{
                results = validLines;
                System.err.println("Cannot create result file with " + quantity + " unique lines. Max unique lines may be only " + validLines.size());
            }
            // 4. for вырезаем выбранные
            for (String r : results) {
                initLines.remove(r);
            }

            // 5. записываем init
            writeFile(path, initLines);
            // 6. записываем results

            // Имя файла без пути, делим по точкам
            String[] srcFileName = path.getName().split("\\.");

            // Расширение будет последним в строковом массиве srcFileName (если вообще будет :) )
            String srcFileExt = srcFileName[srcFileName.length - 1];
            // Если файл с расширением, начинаем думать над именем
            if (srcFileName.length > 1) {

                // Учтем ситуацию, когда имя одной из папок-предков совпадает с именем файла.
                StringBuilder sbRevAbsPath = new StringBuilder(path.getAbsolutePath()).reverse();
                StringBuilder sbRevSrcName = new StringBuilder(path.getName()).reverse();
                String revPathToDir = sbRevAbsPath.toString().replaceFirst(sbRevSrcName.toString(), "");
                StringBuilder sbPathToDir = new StringBuilder(revPathToDir).reverse();

                resultsPath = sbPathToDir.toString() + path.getName().replace("." + srcFileExt, "") + "_res." + srcFileExt;
            } else {
                // Ну а если без него - берем абсолютный путь и прибавляем к нему "_res"
                resultsPath = path.getAbsolutePath() + "_res";
            }
            return writeFile(new File(resultsPath), new ArrayList<String>(results));
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            return "file not found";
        } catch (IOException e) {
            // TODO: cannot readLine in buffer reader
            return "cannot read file";
        }
    }

    private boolean validateLine(String line,int lineNumber, int columneCount){
        if(line.split("\\t").length == columneCount){
            return true;
        }
        System.err.println(String.format("WARN: Line with number %1$d is not valid: %2$s. Cannot be added to results file", lineNumber, line));
        return false;
    }

    private String writeFile(File path, List<String> lines){
        File file = new File(path.getAbsolutePath());
        file.delete();
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (String string : lines) {
                bw.write(string + "\r\n");
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("ERROR: Cannot write file by path: " + path);
        }
        System.out.println("File " + path + " was wrote");
        return file.getAbsolutePath();
    }
}
