package com.example.domain.cashflow.usecases

import com.example.core.constans.enums.InputTransactionEnum
import com.example.domain.cashflow.entities.CashFlowEntity
import com.example.domain.cashflow.repository.CashFlowRepository

class GetCashFlowsUseCase(
    private val repository: CashFlowRepository
) {
    suspend operator fun invoke(
        type: InputTransactionEnum.TypeCashFlow,
        month: String,
        year: Int,
        search: String = ""
    ): Result<List<CashFlowEntity>> {
        return repository.getCashflows(
            type = type,
            month = month,
            year = year,
            search = search
        )
    }
}