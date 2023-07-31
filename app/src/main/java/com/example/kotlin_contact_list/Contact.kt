package com.example.kotlin_contact_list

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String
)
