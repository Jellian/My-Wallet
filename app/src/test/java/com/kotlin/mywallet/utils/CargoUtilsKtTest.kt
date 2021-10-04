package com.kotlin.mywallet.utils

import com.google.common.truth.Truth.assertThat
import com.kotlin.mywallet.finance.Cargo
import com.kotlin.mywallet.finance.Cuenta
import junit.framework.TestCase
import org.junit.Test

class CargoUtilsKtTest {

    @Test
    fun getTypeofCharge_retbool(){

        val charge = listOf(
            Cargo(
                25.0f,
                "camiones",
                "ida y vuelta",
                "3-10-21"))


        val result= getTypeofCharge(charge[0].getAmount())

        assertThat(result).isEqualTo(true)
    }
    @Test
    fun getTypeofCharge_retbool2(){

        val charge = listOf(
            Cargo(
                -25.0f,
                "camiones",
                "ida y vuelta",
                "3-10-21"))


        val result= getTypeofCharge(charge[0].getAmount())

        assertThat(result).isEqualTo(false)
    }


}