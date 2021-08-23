package no.hiof.larseknu.playingwithviewmodel.screens.summary

import android.util.Log
import androidx.lifecycle.ViewModel

class SummaryViewModel : ViewModel() {
    var numberAnswered: Int = 0
    var numberSeen: Int = 0

    init {
        Log.i("SummaryViewModel", "SummaryViewModel created")
    }
}