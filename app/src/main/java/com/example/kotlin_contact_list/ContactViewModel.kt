package com.example.kotlin_contact_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel (
    private val dao: ContactDAO
): ViewModel() {
    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val _contacts = _sortType
        .flatMapLatest { _sortType ->
            when(_sortType){
                SortType.FIRST_NAME -> dao.getContactsByFirstName()
                SortType.LAST_NAME -> dao.getContactsByLastName()
                SortType.PHONE_NUMBER -> dao.getContactsByPhoneNum()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(ContactState())

    val state = combine(_state, _sortType, _contacts){ state, sortType, contacts ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())

    fun onEvent(event: ContactEvent){
        when(event){
            is ContactEvent.DeleteContacts -> {
                viewModelScope.launch{
                    dao.removeContact(event.contact)
                }
            }
            ContactEvent.HideDialog -> {
                _state.update{it.copy(
                    isAddingContact = false
                )}
            }
            ContactEvent.SaveContact -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNum = state.value.phoneNumber

                if(firstName.isBlank() || lastName.isBlank() || phoneNum.isBlank()){
                    return
                }
                val contact = Contact(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNum
                )
                viewModelScope.launch{
                    dao.addOrUpdateContact(contact)
                }

                _state.update { it.copy(
                    isAddingContact = false,
                    firstName = "",
                    lastName = "",
                    phoneNumber = ""
                )}

            }
            is ContactEvent.SetFirstName -> {
                _state.update{ it.copy(
                    firstName = event.firstName
                )}
            }
            is ContactEvent.SetLastName -> {
                _state.update{ it.copy(
                    lastName = event.lastName
                )}
            }
            is ContactEvent.SetPhoneNum -> {
                _state.update{ it.copy(
                    phoneNumber = event.phoneNumber
                )}
            }
            ContactEvent.ShowDialog -> {
                _state.update{ it.copy(
                    isAddingContact = true
                )}
            }
            is ContactEvent.SortContacts -> {
                _sortType.value = event.sortType
            }
        }
    }
}