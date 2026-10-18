package com.taxes.service;

import com.taxes.entity.Incomes.Income;
import com.taxes.entity.Incomes.TaxPayment;
import java.util.*;

public class TaxService {

    /** Comparator для сортировки по сумме налога. */
    public static class TaxAmountComparator implements Comparator<TaxPayment> {
        @Override public int compare(TaxPayment a, TaxPayment b) {
            return Double.compare(a.getTax(), b.getTax());
        }
    }

    private final Comparator<TaxPayment> comparator = new TaxAmountComparator();

    /** Общая сумма налогов за год. */
    public double totalTax(List<Income> incomes) {
        return incomes.stream().mapToDouble(Income::getTaxAmount).sum();
    }

    /** Общая сумма доходов за год. */
    public double totalIncome(List<Income> incomes) {
        return incomes.stream().mapToDouble(Income::getAmount).sum();
    }

    /** Сортировка налоговых выплат по сумме (возрастание). */
    public List<TaxPayment> sortTaxPayments(List<Income> incomes) {
        List<TaxPayment> payments = new ArrayList<>();
        for (Income income : incomes) {
            payments.add(new TaxPayment(income.getName(), income.getTaxAmount()));
        }
        payments.sort(comparator);
        return payments;
    }
}