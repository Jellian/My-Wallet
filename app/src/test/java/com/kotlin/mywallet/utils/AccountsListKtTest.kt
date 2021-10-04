package com.kotlin.mywallet.utils


import com.google.common.truth.Truth.assertThat
import com.kotlin.mywallet.finance.Cuenta
import junit.framework.Assert.assertEquals

import org.junit.Test

class AccountsListKtTest {

    @Test
    fun getNumberAccounts_empty_returnsZero() {

        val accounts = listOf<Cuenta>()

        val result = getNumberAccounts(accounts)

        assertEquals(0, result)
    }

    @Test
    fun getNumberActiveAccounts_returnsTwo() {
        val accounts = listOf(
            Cuenta(
                0,
                "pepe",
                5.0f),
            Cuenta(
                1,
                "Vento",
                2.0f)
        )

        val result= getNumberAccounts(accounts)

        assertThat(result).isEqualTo(2)
    }
}