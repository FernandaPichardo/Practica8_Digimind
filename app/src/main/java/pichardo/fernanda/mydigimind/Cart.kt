package pichardo.fernanda.mydigimind

import java.io.Serializable

class Cart: Serializable {
    var reminders = ArrayList<Reminder>()

    fun add(p: Reminder): Boolean{
        return reminders.add(p)
    }
}