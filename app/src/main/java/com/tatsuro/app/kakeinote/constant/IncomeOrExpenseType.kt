package com.tatsuro.app.kakeinote.constant

import com.tatsuro.app.kakeinote.R

/**
 * 収支の種類
 * @property strResId ローカル文字列のリソースID
 * @property drawableResId アイコンのリソースID
 */
enum class IncomeOrExpenseType(val strResId: Int, val drawableResId: Int) {

    SALARY(R.string.salary, R.drawable.ic_icooon_mono_salary_24),
    PENSION(R.string.pension, R.drawable.ic_icooon_mono_pension_24),
    REMITTANCE(R.string.remittance, R.drawable.ic_icooon_mono_remittance_24),
    POCKET_MONEY(R.string.pocket_money, R.drawable.ic_icooon_mono_pocket_money_24),
    BONUS(R.string.bonus, R.drawable.ic_icooon_mono_bonus_24),
    TEMPORARY_INCOME(R.string.temporary_income, R.drawable.ic_icooon_mono_temporary_income_24),
    LOAN(R.string.loan, R.drawable.ic_icooon_mono_loan_24),
    INTEREST(R.string.interest, R.drawable.ic_icooon_mono_wallet_24),
    INVESTMENT_INCOME(R.string.investment_income, R.drawable.ic_icooon_mono_investment_income_24),
    SALE_INCOME(R.string.sale_income, R.drawable.ic_icooon_mono_sale_income_24),
    OTHER_INCOME(R.string.other, R.drawable.ic_icooon_mono_other_income_24),

    FOOD_EXPENSE(R.string.food_expense, R.drawable.ic_icooon_mono_food_24),
    LEISURE(R.string.leisure, R.drawable.ic_icooon_mono_leisure_24),
    ENTERTAINMENT(R.string.entertainment, R.drawable.ic_icooon_mono_entertainment_24),
    CLOTHING(R.string.clothing, R.drawable.ic_icooon_mono_clothing_24),
    BEAUTY(R.string.beauty, R.drawable.ic_icooon_mono_beauty_24),
    BOOK(R.string.book, R.drawable.ic_icooon_mono_book_24),
    LIBERAL_ARTS(R.string.liberal_arts, R.drawable.ic_icooon_mono_liberal_arts_24),
    MEDICAL_CARE(R.string.medical_care, R.drawable.ic_icooon_mono_medical_care_24),
    HEALTH_CARE(R.string.health_care, R.drawable.ic_icooon_mono_health_care_24),
    TRANSPORTATION(R.string.transportation, R.drawable.ic_icooon_mono_transportation_24),
    HOUSING(R.string.housing, R.drawable.ic_icooon_mono_housing_24),
    WATER(R.string.water, R.drawable.ic_icooon_mono_water_24),
    GAS(R.string.gas, R.drawable.ic_icooon_mono_fire_24),
    ELECTRICITY(R.string.electricity, R.drawable.ic_icooon_mono_electricity_24),
    COMMUNICATION(R.string.communication, R.drawable.ic_icooon_mono_communication_24),
    TAX(R.string.tax, R.drawable.ic_icooon_mono_tax_24),
    OTHER_EXPENSE(R.string.other, R.drawable.ic_icooon_mono_other_expense_24)
}
