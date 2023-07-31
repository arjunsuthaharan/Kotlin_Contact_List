package com.example.kotlin_contact_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel (
    private val dao: ContactDAO
): ViewModel() {
    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val _state = MutableStateFlow(ContactState())

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