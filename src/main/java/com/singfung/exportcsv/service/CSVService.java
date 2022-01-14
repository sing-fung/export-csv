package com.singfung.exportcsv.service;

import com.singfung.exportcsv.util.ExportCSVUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sing-fung
 * @since 1/14/2022
 */

@Service
public class CSVService
{
    public void exportCSVLocally() throws IOException
    {
        List<Object[]> employees = getData();

        CSVPrinter printer = new CSVPrinter(new FileWriter("csv\\example.csv"), CSVFormat.DEFAULT);
        printer.printRecord(getHeader());

        for (Object[] employee : employees)
        { printer.printRecord(employee); }

        printer.flush();
        printer.close();
    }

    public void exportCSVFromBrowser(HttpServletResponse response)
    {
        String fileName = "example.csv";
        byte[] bytes = ExportCSVUtil.writeCsvAfterToBytes(getHeader(), getData());
        ExportCSVUtil.responseSetProperties(fileName, bytes, response);
    }

    public String[] getHeader()
    {
        String[] result = {"FirstName", "LastName", "Email", "Department"};
        return result;
    }

    public List<Object[]> getData()
    {
        List<Object[]> result = new ArrayList<>();
        Object[] obj0 = {"Man", "Sparkes", "msparkes0@springhow.com", "Engineering"};
        Object[] obj1 = {"Dulcinea", "Terzi", "dterzi1@springhow.com", "Engineering"};
        Object[] obj2 = {"Tamar", "Bedder", "tbedder2@springhow.com", "Legal"};
        Object[] obj3 = {"Vance", "Scouller", "vscouller3@springhow.com", "Sales"};
        Object[] obj4 = {"Gran", "Jagoe", "gjagoe4@springhow.com", "Business Development"};

        result.add(obj0);
        result.add(obj1);
        result.add(obj2);
        result.add(obj3);
        result.add(obj4);

        return result;
    }
}
