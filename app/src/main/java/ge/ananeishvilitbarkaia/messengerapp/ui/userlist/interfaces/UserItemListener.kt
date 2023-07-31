package ge.ananeishvilitbarkaia.messengerapp.ui.userlist.interfaces

import ge.ananeishvilitbarkaia.messengerapp.model.MessageUsers

interface UserItemListener {
    fun onCLickUserItem(user: MessageUsers)
}