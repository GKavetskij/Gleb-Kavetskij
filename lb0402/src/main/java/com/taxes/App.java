package com.taxes;

import com.taxes.creator.IncomeCreator;
import com.taxes.entity.Incomes.Income;
import com.taxes.entity.Incomes.TaxPayment;
import com.taxes.service.TaxService;
import com.taxes.util.DataReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.List;

public class App {
    private static final Logger LOG = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        LOG.info("Application started");
        IncomeCreator creator  = new IncomeCreator();
        TaxService    service  = new TaxService();
        DataReader    reader   = new DataReader(creator);

        List<Income> incomes = reader.read(Path.of("src/main/resources/data.txt"));
        LOG.info("Loaded {} records", incomes.size());
        LOG.info("Total income: {}", String.format("%.2f", service.totalIncome(incomes)));
        LOG.info("Total tax   : {}", String.format("%.2f", service.totalTax(incomes)));

        List<TaxPayment> sorted = service.sortTaxPayments(incomes);
        LOG.info("Taxes sorted by amount:");
        sorted.forEach(p -> LOG.info("  {}", p));
        LOG.info("Application finished");
    }
}