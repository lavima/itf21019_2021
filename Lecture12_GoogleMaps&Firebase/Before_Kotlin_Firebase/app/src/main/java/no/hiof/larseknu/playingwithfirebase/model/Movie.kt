package no.hiof.larseknu.playingwithfirebase.model



data class Movie(var title : String = "", var description : String = "", var releaseDate: String = "",
                 var uid : String = "0", var posterUrl : String = "") {
}