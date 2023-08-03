package com.example.kotlin_contact_list

//Interface for events involving Contacts database
sealed interface ContactEvent{
    object SaveContact: ContactEvent
    data class SetFirstName(val firstName: String): ContactEvent
    data class SetLastName(val lastName: String): ContactEvent
    data class SetPhoneNum(val phoneNumber: String): ContactEvent
    object ShowDialog: ContactEvent
    object HideDialog: ContactEvent
    data class SortContacts(val sortType: SortType): ContactEvent
    data class DeleteContacts(val contact: Contact): ContactEvent


}
