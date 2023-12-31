package com.example.kotlin_contact_list

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

//DAO for the Contact data class, contains the CRUD operations along with SQL queries for sorting data by first name, last name or phone number
@Dao
interface ContactDAO {
    @Upsert
    suspend fun addOrUpdateContact(contact: Contact)
    @Delete
    suspend fun removeContact(contact: Contact)

    @Query("SELECT * FROM contact ORDER BY firstName ASC")
    fun getContactsByFirstName(): Flow<List<Contact>>

    @Query("SELECT * FROM contact ORDER BY lastName ASC")
    fun getContactsByLastName(): Flow<List<Contact>>

    @Query("SELECT * FROM contact ORDER BY phoneNumber ASC")
    fun getContactsByPhoneNum(): Flow<List<Contact>>
}