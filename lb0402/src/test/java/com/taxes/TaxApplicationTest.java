package com.taxes;

import com.taxes.creator.IncomeCreator;
import com.taxes.creator.IncomeCreator.InvalidDataException;
import com.taxes.creator.IncomeCreator.Type;
import com.taxes.entity.Incomes.*;
import com.taxes.service.TaxService;
import com.taxes.util.DataReader;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class TaxApplicationTest {

    private IncomeCreator creator;
    private TaxService   service;
    private DataReader   reader;

    @BeforeMethod public void setUp() {
        creator = new IncomeCreator();
        service = new TaxService();
        reader  = new DataReader(creator);
    }

    // ---------- IncomeCreator / валидация ----------

    @Test public void mainJobWithChildrenAndAid() {
        MainJob m = new MainJob("ЗП", 600000, 2, 5000);
        // 600000 + (5000-4000) - 2*1400*12 = 567400
        Assert.assertEquals(m.getTaxableAmount(), 567400.0, 0.001);
        Assert.assertEquals(m.getTaxAmount(),    567400.0 * 0.13, 0.001);
    }

    @Test public void childBenefitDoesNotGoNegative() {
        MainJob m = new MainJob("Малая", 1000, 3, 0);
        Assert.assertEquals(m.getTaxableAmount(), 0.0, 0.001);
        Assert.assertEquals(m.getTaxAmount(),     0.0, 0.001);
    }

    @Test public void creatorRejectsNegativeAmount() {
        Assert.expectThrows(InvalidDataException.class,
                () -> creator.create(Type.GIFT, "X", -1));
    }

    @Test public void creatorRejectsBlankName() {
        Assert.expectThrows(InvalidDataException.class,
                () -> creator.create(Type.GIFT, "  ", 100));
    }

    @Test public void creatorReplacesNegativeExtrasWithDefaults() throws InvalidDataException {
        MainJob m = (MainJob) creator.create(Type.MAIN_JOB, "ЗП", 500000, -3, -5);
        Assert.assertEquals(m.getChildren(), 0);
        Assert.assertEquals(m.getMaterialAid(), 0.0, 0.001);
    }

    // ---------- TaxService ----------

    @Test public void totalTaxAndIncome() {
        List<Income> incomes = List.of(
                new AdditionalJob("A", 100000),
                new Royalty("B", 50000),
                new Gift("C", 30000));
        Assert.assertEquals(service.totalIncome(incomes), 180000.0, 0.001);
        Assert.assertEquals(service.totalTax(incomes),    180000.0 * 0.13, 0.001);
    }

    @Test public void sortByTaxAmountAscending() {
        List<Income> incomes = List.of(
                new Gift("Большой", 100000),
                new AdditionalJob("Малый", 10000),
                new Royalty("Средний", 50000));
        List<TaxPayment> sorted = service.sortTaxPayments(incomes);
        Assert.assertEquals(sorted.get(0).getSource(), "Малый");
        Assert.assertEquals(sorted.get(2).getSource(), "Большой");
        Assert.assertTrue(sorted.get(0).getTax() <= sorted.get(1).getTax());
        Assert.assertTrue(sorted.get(1).getTax() <= sorted.get(2).getTax());
    }

    // ---------- DataReader ----------

    @Test public void readerSkipsInvalidAndKeepsValid() throws IOException {
        Path tmp = Files.createTempFile("taxes", ".txt");
        Files.write(tmp, List.of(
                "MAIN_JOB;ЗП;600000;2;5000",
                "ADDITIONAL_JOB;Отрицательный;-50000",
                "UNKNOWN;Тип;100000",
                "ROYALTY;abc;xyz",
                "GIFT;Подарок;30000"));
        Assert.assertEquals(reader.read(tmp).size(), 2);
        Files.deleteIfExists(tmp);
    }

    @Test public void readerHandlesEmptyFile() throws IOException {
        Path tmp = Files.createTempFile("empty", ".txt");
        Assert.assertTrue(reader.read(tmp).isEmpty());
        Files.deleteIfExists(tmp);
    }

    @Test public void readerDefaultsBadExtrasToZero() throws IOException {
        Path tmp = Files.createTempFile("bad-extra", ".txt");
        Files.write(tmp, List.of("MAIN_JOB;ЗП;500000;abc;xyz"));
        List<Income> r = reader.read(tmp);
        Assert.assertEquals(r.size(), 1);
        MainJob m = (MainJob) r.get(0);
        Assert.assertEquals(m.getChildren(), 0);
        Assert.assertEquals(m.getMaterialAid(), 0.0, 0.001);
        Files.deleteIfExists(tmp);
    }
}