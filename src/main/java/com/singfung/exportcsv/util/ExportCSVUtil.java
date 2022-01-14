package com.singfung.exportcsv.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportCSVUtil
{
    private static final Logger logger = LoggerFactory.getLogger(ExportCSVUtil.class);

    public static byte[] writeCsvAfterToBytes(String[] headers, List<Object[]> cellList)
	{
    	byte[] bytes = new byte[0];
    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    	OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8);
    	BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
    	CSVPrinter  csvPrinter = null;
		try {
			csvPrinter = new CSVPrinter(bufferedWriter, CSVFormat.DEFAULT.withHeader(headers));
			csvPrinter.printRecords(cellList);
			csvPrinter.flush();
			bytes = byteArrayOutputStream.toString(StandardCharsets.UTF_8.name()).getBytes();
		} catch (IOException e) {
			logger.error("writeCsv IOException:{}", e.getMessage(), e);
		} finally {
			try {
				if (csvPrinter != null) {
					csvPrinter.close();
				}
				if (bufferedWriter != null) {
					bufferedWriter.close();
				}
				if (outputStreamWriter != null) {
					outputStreamWriter.close();
				}
				if (byteArrayOutputStream != null) {
					byteArrayOutputStream.close();
				}
			} catch (IOException e) {
				logger.error("iostream close IOException:{}", e.getMessage(), e);
			}
		}
		return bytes;
    }       

    public static void responseSetProperties(String fileName, byte[] bytes, HttpServletResponse response)
	{
		try {
			fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
			response.setContentType("application/csv");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "max-age=30");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(bytes);
			outputStream.flush();
		} catch (IOException e) {
			logger.error("iostream error:{}", e.getMessage(), e);
		}
    }
}