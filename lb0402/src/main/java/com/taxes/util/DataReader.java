package com.taxes.util;

import com.taxes.creator.IncomeCreator;
import com.taxes.creator.IncomeCreator.InvalidDataException;
import com.taxes.creator.IncomeCreator.Type;
import com.taxes.entity.Incomes.Income;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataReader {
    private static final Logger LOG = LogManager.getLogger(DataReader.class);
    private final IncomeCreator creator;

    public DataReader(IncomeCreator creator) { this.creator = creator; }

    public List<Income> read(Path path) {
        List<Income> result = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(path)) {
            String line; int no = 0;
            while ((line = r.readLine()) != null) {
                no++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                try {
                    result.add(parse(line));
                    LOG.debug("line {}: OK", no);
                } catch (InvalidDataException | NumberFormatException e) {
                    LOG.warn("line {} skipped ('{}'): {}", no, line, e.getMessage());
                }
            }
        } catch (IOException e) {
            LOG.error("cannot read {}", path, e);
        }
        return result;
    }

    private Income parse(String line) throws InvalidDataException {
        String[] p = line.split(";");
        if (p.length < 3) throw new InvalidDataException("too few fields");
        Type type;
        try { type = Type.valueOf(p[0].trim().toUpperCase()); }
        catch (IllegalArgumentException e) { throw new InvalidDataException("unknown type " + p[0]); }
        double amount;
        try { amount = Double.parseDouble(p[2].trim()); }
        catch (NumberFormatException e) { throw new InvalidDataException("bad amount " + p[2]); }
        double[] extras = new double[Math.max(0, p.length - 3)];
        for (int i = 3; i < p.length; i++) {
            try { extras[i - 3] = Double.parseDouble(p[i].trim()); }
            catch (NumberFormatException e) {
                LOG.warn("non-numeric extra '{}', default 0", p[i]);
                extras[i - 3] = 0;
            }
        }
        return creator.create(type, p[1].trim(), amount, extras);
    }
}