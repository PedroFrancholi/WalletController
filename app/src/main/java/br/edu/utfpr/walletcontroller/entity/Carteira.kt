package br.edu.utfpr.walletcontroller.entity

data class Carteira(
    var id:Int,
    var tipo:String,
    var detalhe:String,
    var valor:Double,
    var datalancto:String
)
