package com.example.kotlin_contact_list

import androidx.room.Database
import androidx.room.RoomDatabase

//Database for the contacts, extending the RoomDatabase
@Database(
    entities = [Contact::class],
    version = 1
)
abstract class ContactDatabase : RoomDatabase(){
    abstract val dao: ContactDAO
}