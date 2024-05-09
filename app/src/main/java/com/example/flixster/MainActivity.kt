package com.example.flixster
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PersonAdapter.OnItemClickListener {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PersonAdapter
    private val client = AsyncHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Determine whether to show fragment or RecyclerView based on your needs
        if (shouldShowFragment()) {
            showFragment()
        } else {
            showRecyclerView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release resources
        client.cancelAllRequests(true)
    }

    private fun shouldShowFragment(): Boolean {
        // Implement your logic to decide whether to show fragment or RecyclerView
        // For example, check if the device is in landscape mode or if it's a tablet
        return resources.getBoolean(R.bool.is_tablet)
    }

    private fun showFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content, BestMoviesFragment(), null).commit()
    }

    private fun showRecyclerView() {
        progressBar.visibility = View.VISIBLE
        adapter = PersonAdapter(ArrayList(), this)
        recyclerView.adapter = adapter

        fetchPersons()
    }

    private fun fetchPersons() {
        val url = "https://api.themoviedb.org/3/person/popular"
        val params = RequestParams().apply {
            put("api_key", "a07e22bc18f5cb106bfe4cc1f83ad8ed")
        }

        client.get(url, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject) {
                progressBar.visibility = View.GONE
                val results = response.optJSONArray("results")
                if (results != null) {
                    val persons = parsePersons(results)
                    adapter.updateData(persons)
                } else {
                    // Handle empty response
                    Toast.makeText(this@MainActivity, "No data available", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, throwable: Throwable?, errorResponse: JSONObject?) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                throwable?.printStackTrace()
            }
        })
    }

    private fun parsePersons(jsonArray: JSONArray): List<Person> {
        val persons = mutableListOf<Person>()
        for (i in 0 until jsonArray.length()) {
            val personJson = jsonArray.getJSONObject(i)
            val person = Person(
                id = personJson.optInt("id"),
                name = personJson.optString("name"),
                profilePath = personJson.optString("profile_path"),
                knownFor = parseKnownFor(personJson.optJSONArray("known_for")),
                posterPath = personJson.optString("known_for_department"),
                biography = personJson.optString("biography")
            )
            persons.add(person)
        }
        return persons
    }

    private fun parseKnownFor(knownForArray: JSONArray?): List<String> {
        val knownForList = mutableListOf<String>()
        knownForArray?.let {
            for (i in 0 until it.length()) {
                val knownForJson = it.optJSONObject(i)
                val title = knownForJson?.optString("title")
                if (!title.isNullOrEmpty()) {
                    knownForList.add(title)
                }
            }
        }
        return knownForList
    }

    override fun onItemClick(person: Person) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("person", person)
        }
        startActivity(intent)
    }
}
