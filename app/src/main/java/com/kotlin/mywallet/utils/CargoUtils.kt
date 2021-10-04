package com.kotlin.mywallet.utils

import com.kotlin.mywallet.finance.Cargo

internal fun getTypeofCharge(charge:Float): Boolean {

     if (charge<0){
         return false
     }
         return true


 }