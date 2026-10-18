package com.taxes.creator;

import com.taxes.entity.Incomes.Income;
import com.taxes.entity.Incomes.*;


public class IncomeCreator {

    public enum Type { MAIN_JOB, ADDITIONAL_JOB, ROYALTY, PROPERTY_SALE, GIFT, FOREIGN_TRANSFER }

    /** Factory Method: выбирает нужный конструктор по типу и валидирует данные. */
    public Income create(Type type, String name, double amount, double... extra)
            throws InvalidDataException {
        if (name == null || name.isBlank()) throw new InvalidDataException("blank name");
        if (type == null)                    throw new InvalidDataException("null type");
        return switch (type) {
            case MAIN_JOB       -> createMain(name, amount, extra);
            case ADDITIONAL_JOB -> createAdditional(name, amount);
            case ROYALTY        -> createRoyalty(name, amount);
            case PROPERTY_SALE  -> createPropertySale(name, amount);
            case GIFT           -> createGift(name, amount);
            case FOREIGN_TRANSFER -> createForeignTransfer(name, amount);
        };
    }

    private Income createMain(String name, double amount, double[] extra)
            throws InvalidDataException {
        if (amount < 0) throw new InvalidDataException("negative amount: " + amount);
        int children = extra.length > 0 ? Math.max(0, (int) extra[0]) : 0;
        double aid   = extra.length > 1 ? Math.max(0, extra[1])        : 0;
        return new MainJob(name, amount, children, aid);
    }
    private Income createAdditional(String n, double a) throws InvalidDataException {
        requireNonNegative(a); return new AdditionalJob(n, a);
    }
    private Income createRoyalty(String n, double a) throws InvalidDataException {
        requireNonNegative(a); return new Royalty(n, a);
    }
    private Income createPropertySale(String n, double a) throws InvalidDataException {
        requireNonNegative(a); return new PropertySale(n, a);
    }
    private Income createGift(String n, double a) throws InvalidDataException {
        requireNonNegative(a); return new Gift(n, a);
    }
    private Income createForeignTransfer(String n, double a) throws InvalidDataException {
        requireNonNegative(a); return new ForeignTransfer(n, a);
    }

    private static void requireNonNegative(double v) throws InvalidDataException {
        if (v < 0) throw new InvalidDataException("negative amount: " + v);
    }
    public static class InvalidDataException extends Exception {
        public InvalidDataException(String message) {
            super(message);
        }
    }
}
