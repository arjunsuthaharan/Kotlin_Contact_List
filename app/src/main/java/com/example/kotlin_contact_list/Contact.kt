package com.example.kotlin_contact_list

import androidx.room.Entity
import androidx.room.PrimaryKey

//Data Class for Contact, containing ID as the primary key along with a first name, last name and phone number
@Entity()
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String
)
