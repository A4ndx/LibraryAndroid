package com.andxpar.biblioparcial.data

data class Book(
    var idbook: Int,
    var ISBN: Int,
    var editorial: String,
    var year: Int,
    var author: String,
    var knowledgeArea: String,
    var quantity: Int,
    var campusAva: Int,
    var comments: String
)