package com.brandmaker.cs.skyhigh.tdb.core;

import com.brandmaker.cs.skyhigh.tdb.collection.CustomDataCollection;
import com.brandmaker.cs.skyhigh.tdb.dto.CrossChargesDto;
import com.brandmaker.cs.skyhigh.tdb.dto.InvoiceDto;
import com.brandmaker.cs.skyhigh.tdb.dto.ParseMessageDto;
import com.brandmaker.cs.skyhigh.tdb.utils.Enumerations;
import com.brandmaker.cs.skyhigh.tdb.utils.Utils;
import com.brandmaker.cs.skyhigh.tdb.webapi.MaplRestServiceImpl;
import com.google.common.io.ByteStreams;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProcessCrossCharges {

    private static final Log LOG = LogFactory.getLog(ProcessCrossCharges.class);
    public static List<Integer> rejectedList;
    private final MaplRestServiceImpl maplRestService;

    public ProcessCrossCharges() {
        this.maplRestService = new MaplRestServiceImpl();
    }

    /**
     * Processing a selected CSV file
     * @param file
     * @return Object[] - [0] - boolean - file format,
     */
    public List doProcess(InputStream file) {

        rejectedList = new ArrayList<>();
        List response = new ArrayList();
        boolean allGood = true;


        try {
            byte[] bytes = ByteStreams.toByteArray(file);

            CSVParserBuilder csvParserBuilder = new CSVParserBuilder();
            CSVParser parser = csvParserBuilder.withSeparator(',').build();
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(new ByteArrayInputStream(bytes), StandardCharsets.UTF_8)).withCSVParser(parser)
                    .build();

            List<String[]> fData = reader.readAll();

            // FILE FORMAT OK
            if (Utils.checkCrossChargesFileFormat(fData.get(0))) {

                LOG.info("FILE FORMAT OK: SENDING RAW DATA...");
                fData.remove(0);
                ArrayList<CrossChargesDto> crossChargesDtoList = parseData(fData);

                // TEST FILE - to comment
//                String fileName = "C:\\Users\\ProBook\\Documents\\new docs\\pro doc\\tdb_cross_charges\\rezultat.csv";
//                StringBuilder sb = new StringBuilder();

                for (int i=0; i<crossChargesDtoList.size(); i++) {

                    CrossChargesDto invoiceItem = crossChargesDtoList.get(i);

                    try {
                        // api creating new invoice record
                        maplRestService.createInvoiceRecord(Integer.parseInt(invoiceItem.getElementId()), true, new InvoiceDto(invoiceItem, true));
                    } catch(Exception e) {
                        LOG.error("IMPORT ERROR: Could not create invoice record ("+i+"): " + e.getMessage());
                    }

                    // TEST FILE - to comment
//                    CrossChargesDto ccItem = crossChargesDtoList.get(i);
//                    sb.append(ccItem.getNameOfCharge());
//                    sb.append(",");
//                    sb.append(ccItem.getDescription());
//                    sb.append(",");
//                    sb.append(ccItem.getAmount());
//                    sb.append(",");
//                    sb.append(ccItem.getCurrency());
//                    sb.append(",");
//                    sb.append(ccItem.getAccountingDate());
//                    sb.append(",");
//                    sb.append(ccItem.getTransactionDate());
//                    sb.append(",");
//                    sb.append(ccItem.getAccountingTransactionId());
//                    sb.append(",");
//                    sb.append(ccItem.getCategoryCode());
//                    sb.append(",");
//                    sb.append(ccItem.getCostCenterProjId());
//                    sb.append(",");
//                    sb.append(ccItem.getBkRcGl());
//                    sb.append(",");
//                    sb.append(ccItem.getCrossChargeState());
//                    sb.append(",");
//                    sb.append(ccItem.getElementId());
//                    sb.append("\r\n");

                }

                // TEST FILE - to comment
//                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//                    writer.write(sb.toString());
//                }


            } else {
                allGood = false;
            }

        } catch (IOException | CsvException e) {
            LOG.error("PROCES CC ERROR 1: " + e.getMessage());
            e.printStackTrace();
        }

//        System.out.println("REJECTS: " + rejectedList.size());

        response.add(allGood);
        if (rejectedList.size() > 0) {
            response.add(rejectedList);
        }
        return response;
    }

    /**
     * Parse and validate csv data using CustomData collection
     * @param fileData - raw file data
     * @return Array of SellinActualDto
     */
    private ArrayList<CrossChargesDto> parseData(List<String[]> fileData) {

        LOG.info("PROCESS CC: PARSING FETCHED DATA...");

        ArrayList<CrossChargesDto> crossChargesDtos = new ArrayList<>();
        CustomDataCollection<CrossChargesDto> dataCollection = new CustomDataCollection<CrossChargesDto>(CrossChargesDto.class);

        // item = row
        for (String[] item : fileData) {
            try {
                Integer rowIndex = fileData.indexOf(item) + 1;
                ParseMessageDto parseMessage = dataCollection.processData(item, rowIndex);
                if (parseMessage.getLoadStatus() == Enumerations.ProcessOutcomeStatusEnum.SUCCESSED) {
                    crossChargesDtos.add(dataCollection.getLastItem());
                } else {
                    LOG.error("PROCESS CC ERROR: ROW "+ rowIndex +" REJECTED ");
                }
            } catch (SecurityException | IllegalArgumentException e) {
                LOG.error("PROCES CC ERROR 2: " + e.getMessage());
            }
        }

        return crossChargesDtos;

    }
}
