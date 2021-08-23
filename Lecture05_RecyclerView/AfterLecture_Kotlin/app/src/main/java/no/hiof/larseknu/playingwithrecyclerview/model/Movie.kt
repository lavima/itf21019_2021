package no.hiof.larseknu.playingwithrecyclerview.model

import no.hiof.larseknu.playingwithrecyclerview.R


data class Movie(val uid : Int, var title : String, var description : String, var posterUrl : Int) {

    companion object {
        fun getMovies() : ArrayList<Movie> {
            val data = ArrayList<Movie>()

            val posters = intArrayOf(
                R.drawable.captain_america_civil_war,
                R.drawable.donnie_darko,
                R.drawable.iron_man_3,
                R.drawable.spirited_away,
                R.drawable.star_wars_the_force_awakens,
                R.drawable.the_hobbit,
                R.drawable.up,
                R.drawable.pulp_fiction,
                R.drawable.coco,
                R.drawable.deadpool,
                R.drawable.inside_out,
                R.drawable.into_the_wild,
                R.drawable.the_hateful_eight,
                R.drawable.the_intouchables,
                R.drawable.the_lion_king
            )

            val titles = arrayOf(
                "Captain America Civil War",
                "Donnie Darko",
                "Iron Man 3",
                "Spirited Away",
                "Star Wars The Force Awakens",
                "The Hobbit",
                "Up",
                "Pulp Fiction",
                "Coco",
                "Deadpool",
                "Inside Out",
                "Into The Wild",
                "The Hateful Eight",
                "The Intouchables",
                "The Lion King"
            )

            titles.forEachIndexed { index, title ->
                val aMovie = Movie(index, title, title + " is a good movie.", posters.get(index))

                data.add(aMovie)
            }

            return data
        }
    }

}