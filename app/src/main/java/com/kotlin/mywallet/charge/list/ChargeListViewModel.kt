package com.kotlin.mywallet.charge.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.data.entities.Charge
import kotlinx.coroutines.launch

class ChargeListViewModel(private val userRepository: UserRepository): ViewModel() {

    private var charges: List<Charge> = listOf()

    fun getChargeListByUserAndAccount(username: String, accountName: String): List<Charge>{
        charges = userRepository.getChargesByUserAndAccount(username, accountName)
        return charges
    }

    init{
        //prepopulateCharges()
    }

    private fun prepopulateCharges(){

        val charges = listOf(
            Charge(amount =-254f , category = "Transporte" , note = "Transporte a casa.",date ="", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-45.78f , category = "Comida" , note = "Sandwiches.",date ="", accountName = "Mi banco 1" , username = "Drake" ),
            Charge(amount =-7.5f , category = "Transporte", note = "Combi a trabajo.",date ="", accountName = "Segunda", username = "Peter Parker"),
            Charge(amount =-350f , category = "Ropa" , note = "Pantalón.",date ="", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =-700f , category = "Salud" , note = "Cita mensual dentista.",date = "", accountName = "Segunda", username = "Peter Parker"),
            Charge(amount =-558f , category = "Auto", note = "Verificación.",date ="", accountName = "Vales", username = "Drake"),
            Charge(amount =-250f , category = "Restaurante", note = "Comida con amigos.",date ="", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =-100f , category = "Casa", note = "Repareción puerta.",date ="", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-12f , category = "Transporte", note = "Combis a escuela.",date ="", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-890f , category = "Ropa", note = "Teniss.",date ="", accountName = "Ahorros", username = "Drake" ),
            Charge(amount =-350f , category = "Comida", note = "Despensa Super.",date ="", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =-560f , category = "Salud", note = "Medicamentos.",date ="", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-200f , category = "Entretenimiento", note = "Salida al cine.",date ="", accountName = "Segunda", username = "Peter Parker"),
            Charge(amount =-70f , category = "Casa", note = "Candado nuevo.",date ="", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-40f , category = "Auto", note = "Estacionamiento.",date ="", accountName = "Vales", username = "Drake"),
            Charge(amount =-24f , category = "Transporte", note = "Taxi.",date = "", accountName = "Segunda", username = "Peter Parker"),
            Charge(amount =-25f , category = "Casa", note = "Cooperación edificio.",date ="", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =-230f , category = "Ropa", note = "Playeras.",date = "", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =-449.50f , category = "Salud", note = "Rayos X",date = "", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =-187.90f , category = "Restaurante", note = "Restaurante Palmas.",date ="", accountName = "Ahorros", username = "David Uppen"),


            Charge(amount =200f , category = "Depósito", note = "Nada.",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =230f , category = "Inversión", note = "Rendimiento quincenal.",date = "", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =200f , category = "Ahorro", note = "Ahorro semanal.",date = "", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =235f , category = "Ahorro", note = "Ahorro semanal.",date = "", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =137.12f , category = "Inversión", note = "Rendimientos.",date = "", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =100f , category = "Depósito", note = "David PM.",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =17000f , category = "Salario", note = "Quincena.",date = "", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =24000f , category = "Salario", note = "Quincena.",date = "", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =2500f , category = "Depósito", note = "René.",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =900f , category = "Ventas", note = "",date = "", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =103.88f , category = "Inversión", note = "Acumulado.",date = "", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =100f , category = "Depósito", note = "Karen Denisse M.",date = "", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =378f , category = "Ahorro", note = "Ahorro semanal.",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =22000f , category = "Salario", note = "Mensual.",date = "", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =900f , category = "Depósito", note = "Javier A.",date = "", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =20000f , category = "Salario", note = "Quincena.",date = "", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =87.3f , category = "Inversión", note = "Mensual.",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =350f , category = "Depósito", note = "A cuenta viaje Qro. YIPM.",date = "", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =460f , category = "Depósito", note = "Yunnuen S.G.",date = "", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =35000f , category = "Salario", note = "Mensual.",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =27000f , category = "Salario", note = "Octubre.",date = "", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =30f , category = "Depósito", note = "Torta xd",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =250f, category = "Ventas", note = "",date = "", accountName = "Banco Nacional", username = "Travis Scott")
        )

        viewModelScope.launch {
            userRepository.populateCharges(charges)
        }
    }

}