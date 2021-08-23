package no.hiof.larseknu.playingwithfirebase.model

import com.google.firebase.firestore.Exclude


data class Movie(var title : String = "", var description : String = "", var releaseDate: String = "",
                 @get:Exclude var uid : String = "0", var posterUrl : String = "") {
}