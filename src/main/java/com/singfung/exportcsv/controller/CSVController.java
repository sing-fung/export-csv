package com.singfung.exportcsv.controller;

import com.singfung.exportcsv.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sing-fung
 * @since 1/14/2022
 */

@RestController
@RequestMapping(value = "/api/csv")
public class CSVController
{
    private CSVService csvService;

    @Autowired
    public CSVController(CSVService csvService)
    { this.csvService = csvService; }

    @GetMapping("/local")
    public void exportCSVLocally() throws IOException
    { csvService.exportCSVLocally(); }

    @GetMapping("/browser")
    public void exportCSVFromBrowser(HttpServletResponse response)
    { csvService.exportCSVFromBrowser(response); }
}