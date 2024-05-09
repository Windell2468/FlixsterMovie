package com.example.flixster

import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

// Import the necessary classes for handling JSON responses
import your.package.name.Person
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class PersonFragment : Fragment(), PersonAdapter.OnItemClickListener {

    private lateinit var progressBar: ContentLoadingProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PersonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_person_list, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PersonAdapter(ArrayList(), this)
        recyclerView.adapter = adapter
        fetchData()
        return view
    }

    private fun fetchData() {
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams().apply {
            put("api_key", API_KEY)
        }

        client.get(
            "https://api.themoviedb.org/3/person/popular",
            params,
            object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, json: JSONObject) {
                    progressBar.hide()
                    val results = json.getJSONArray("results")
                    val persons = parsePersons(results)
                    adapter.updateData(persons)
                }

                override fun onFailure(statusCode: Int, headers: Array<out Header>?, response: String?, throwable: Throwable?) {
                    progressBar.hide()
                    // Handle failure
                }
            }
        )
    }

    private fun parsePersons(jsonArray: JSONArray): List<Person> {
        val persons = mutableListOf<Person>()
        for (i in 0 until jsonArray.length()) {
            val personJson = jsonArray.getJSONObject(i)
            val person = Person(
                id = personJson.getInt("id"),
                name = personJson.getString("name"),
                profilePath = personJson.optString("profile_path", null),
                knownFor = parseKnownFor(personJson.getJSONArray("known_for")),
                posterPath = personJson.optString("known_for_department", null),
                biography = personJson.getString("biography")
            )
            persons.add(person)
        }
        return persons
    }

    private fun parseKnownFor(knownForArray: JSONArray): List<String> {
        val knownForList = mutableListOf<String>()
        for (i in 0 until knownForArray.length()) {
            val knownForJson = knownForArray.getJSONObject(i)
            knownForList.add(knownForJson.getString("title")) // Assuming known_for is movies
        }
        return knownForList
    }

    override fun onItemClick(person: Person) {
        // Handle item click
    }
}
