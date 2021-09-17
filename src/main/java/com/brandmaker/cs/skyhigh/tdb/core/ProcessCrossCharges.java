package com.brandmaker.cs.skyhigh.tdb.core;

import com.brandmaker.cs.skyhigh.tdb.servlets.CrossChargesServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessCrossCharges {

    private static final Log LOG = LogFactory.getLog(ProcessCrossCharges.class);


    public Boolean doProcess(InputStream file) {

        boolean allGood = false;

        List fileData = parseCsv(file, ',');

        System.out.println(Arrays.toString(fileData.toArray()));

//        try (InputStreamReader isr = new InputStreamReader(file,
//                     StandardCharsets.UTF_8);
//             BufferedReader br = new BufferedReader(isr)) {
//
//            br.lines().forEach(line -> System.out.println(line));
//            allGood = true;
//            LOG.info("Reading file good.");
//        } catch (IOException e) {
//            allGood = false;
//            LOG.error("Reading file error: " + e.getMessage());
//        }

        return allGood;
    }

    /**
     * CSV content parser. Convert an InputStream with the CSV contents to a two-dimensional List
     * of Strings representing the rows and columns of the CSV. Each CSV record is expected to be
     * separated by the specified CSV field separator.
     * @param csvInput The InputStream with the CSV contents.
     * @param csvSeparator The CSV field separator to be used.
     * @return A two-dimensional List of Strings representing the rows and columns of the CSV.
     */
    public static List<List<String>> parseCsv(InputStream csvInput, char csvSeparator) {

        // Prepare.
        BufferedReader csvReader = null;
        List<List<String>> csvList = new ArrayList<List<String>>();
        String csvRecord = null;

        // Process records.
        try {
            csvReader = new BufferedReader(new InputStreamReader(csvInput, "UTF-8"));
            while ((csvRecord = csvReader.readLine()) != null) {
                csvList.add(parseCsvRecord(csvRecord, csvSeparator));
            }
        } catch (IOException e) {
            throw new RuntimeException("Reading CSV failed.", e);
        } finally {
            if (csvReader != null)
                try {
                    csvReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return csvList;
    }

    /**
     * CSV record parser. Convert a CSV record to a List of Strings representing the fields of the
     * CSV record. The CSV record is expected to be separated by the specified CSV field separator.
     * @param record The CSV record.
     * @param csvSeparator The CSV field separator to be used.
     * @return A List of Strings representing the fields of each CSV record.
     */
    private static List<String> parseCsvRecord(String record, char csvSeparator) {

        // Prepare.
        boolean quoted = false;
        StringBuilder fieldBuilder = new StringBuilder();
        List<String> fields = new ArrayList<String>();

        // Process fields.
        for (int i = 0; i < record.length(); i++) {
            char c = record.charAt(i);
            fieldBuilder.append(c);

            if (c == '"') {
                quoted = !quoted; // Detect nested quotes.
            }

            if ((!quoted && c == csvSeparator) // The separator ..
                    || i + 1 == record.length()) // .. or, the end of record.
            {
                String field = fieldBuilder.toString() // Obtain the field, ..
                        .replaceAll(csvSeparator + "$", "") // .. trim ending separator, ..
                        .replaceAll("^\"|\"$", "") // .. trim surrounding quotes, ..
                        .replace("\"\"", "\""); // .. and un-escape quotes.
                fields.add(field.trim()); // Add field to List.
                fieldBuilder = new StringBuilder(); // Reset.
            }
        }

        return fields;
    }
}
