package com.taxes.entity;

import com.taxes.util.TaxConstants;
import java.util.Objects;

/** Контейнер иерархии доходов. Все подклассы — static nested (см. Java Code Convention, п. 3.4.1.1). */
public abstract class Incomes {

    public abstract static class Income {
        private final String name;
        private final double amount;

        protected Income(String name, double amount) {
            this.name = name;
            this.amount = amount;
        }

        public String getName()   { return name; }
        public double getAmount() { return amount; }

        /** Сумма, с которой считается налог (учитывает льготы). */
        public abstract double getTaxableAmount();

        public double getTaxAmount() {
            return getTaxableAmount() * getTaxRate();
        }

        protected abstract double getTaxRate();

        @Override public String toString() {
            return String.format("%s{name='%s', amount=%.2f, taxable=%.2f, tax=%.2f}",
                    getClass().getSimpleName(), name, amount,
                    getTaxableAmount(), getTaxAmount());
        }
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Income i = (Income) o;
            return Double.compare(i.amount, amount) == 0 && Objects.equals(name, i.name);
        }
        @Override public int hashCode() { return Objects.hash(name, amount); }
    }

    /** Основное место работы: льгота на детей + материальная помощь. */
    public static class MainJob extends Income {
        private final int children;
        private final double materialAid;

        public MainJob(String name, double amount, int children, double materialAid) {
            super(name, amount);
            this.children = children;
            this.materialAid = materialAid;
        }
        public int getChildren()       { return children; }
        public double getMaterialAid() { return materialAid; }

        @Override public double getTaxableAmount() {
            double childBenefit = children * TaxConstants.CHILD_BENEFIT
                    * TaxConstants.MONTHS_IN_YEAR;
            double aidExcess = Math.max(0, materialAid - TaxConstants.MATERIAL_AID_LIMIT);
            return Math.max(0, amount() + aidExcess - childBenefit);
        }
        private double amount() { return getAmount(); }

        @Override protected double getTaxRate() { return TaxConstants.INCOME_TAX_RATE; }
    }

    public static class AdditionalJob extends Income {
        public AdditionalJob(String name, double amount) { super(name, amount); }
        @Override public double getTaxableAmount() { return getAmount(); }
        @Override protected double getTaxRate()    { return TaxConstants.INCOME_TAX_RATE; }
    }

    public static class Royalty extends Income {
        public Royalty(String name, double amount) { super(name, amount); }
        @Override public double getTaxableAmount() { return getAmount(); }
        @Override protected double getTaxRate()    { return TaxConstants.ROYALTY_TAX_RATE; }
    }

    public static class PropertySale extends Income {
        public PropertySale(String name, double amount) { super(name, amount); }
        @Override public double getTaxableAmount() { return getAmount(); }
        @Override protected double getTaxRate()    { return TaxConstants.PROPERTY_SALE_TAX_RATE; }
    }

    public static class Gift extends Income {
        public Gift(String name, double amount) { super(name, amount); }
        @Override public double getTaxableAmount() { return getAmount(); }
        @Override protected double getTaxRate()    { return TaxConstants.GIFT_TAX_RATE; }
    }

    public static class ForeignTransfer extends Income {
        public ForeignTransfer(String name, double amount) { super(name, amount); }
        @Override public double getTaxableAmount() { return getAmount(); }
        @Override protected double getTaxRate()    { return TaxConstants.FOREIGN_TRANSFER_TAX_RATE; }
    }

    /** Налоговая выплата — отдельная сущность для отчётов/сортировки. */
    public static class TaxPayment {
        private final String source;
        private final double tax;

        public TaxPayment(String source, double tax) {
            this.source = source;
            this.tax = tax;
        }
        public String getSource() { return source; }
        public double getTax()    { return tax; }

        @Override public String toString() {
            return String.format("TaxPayment{source='%s', tax=%.2f}", source, tax);
        }
    }

    private Incomes() { } // утилитарный контейнер не инстанцируется
}