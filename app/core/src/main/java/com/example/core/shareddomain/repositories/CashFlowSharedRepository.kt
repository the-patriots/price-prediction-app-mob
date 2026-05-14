package com.example.core.shareddomain.repositories

import com.example.core.shareddomain.entities.CashFlow

interface CashFlowSharedRepository {
    suspend fun getCashFlows(): List<CashFlow>
    suspend fun getCashFlowById(id: String): CashFlow?
}