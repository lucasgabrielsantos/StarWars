package br.com.codelab.starwars.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import br.com.codelab.starwars.model.API.StarWarsAPI
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var listView : ListView
    private lateinit var movieAdapter : ArrayAdapter<String>
    private var movies = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listView = ListView(this)
        setContentView(listView)
        movieAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, movies)
        listView.adapter = movieAdapter

        val api = StarWarsAPI()
        api.loadMoviewFull()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({ movie ->
                movies.add("${movie.title} -- ${movie.episodeId}\n ${movie.characters.toString()}")
            }, { e ->
                e.printStackTrace()
            },{
                movieAdapter.notifyDataSetChanged()
            })
    }
}
