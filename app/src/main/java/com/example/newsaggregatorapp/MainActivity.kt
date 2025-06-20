package com.example.newsaggregatorapp


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.koushikdutta.ion.Ion
import org.json.JSONArray
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var mAdapter:MyAdapter
    lateinit var swipeRefresh:SwipeRefreshLayout
    lateinit var recyclerView:RecyclerView
    lateinit var layoutManager:LinearLayoutManager
    lateinit var searchQuery:String
    lateinit var category:String


    private var mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser
    lateinit var context:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSharedPreferences(getString(R.string.mypreference), Context.MODE_WORLD_READABLE)
            .edit()
            .putBoolean(getString(R.string.isRunning), false)
            .commit()
        category = getString(R.string.breaking_news)
        context = this

        setContentView(R.layout.activity_main)
        recyclerView = findViewById<View>(R.id.news_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        layoutManager = LinearLayoutManager(this) // Get the layout manager
        val mToolbar = findViewById<Toolbar>(R.id.toolbar_main)

        setSupportActionBar(mToolbar)

        populateNewsList() {returnedData ->
            recyclerView.layoutManager = layoutManager
            mAdapter = MyAdapter(returnedData)
            recyclerView.adapter = mAdapter
        }

        val response1 = "{\"totalArticles\":374601,\"articles\":[{\"title\":\"How to set up and use your Amazon Fire TV Stick\",\"description\":\"Amazon’s Fire TV Stick allows for an excellent streaming experience at a reasonable price.\",\"content\":\"If you got a Fire TV Stick this holiday season and you’re ready to set it up, you may be looking for guidance on how and where to start. We’re here to help.\\nHere's everything you need to know about setting up and using a Fire TV Stick, no matter whic... [4524 chars]\",\"url\":\"https://www.aboutamazon.com/news/devices/amazon-fire-tv-stick\",\"image\":\"https://assets.aboutamazon.com/dims4/default/e517c67/2147483647/strip/true/crop/2000x1000+0+63/resize/1200x600!/quality/90/?url=https%3A%2F%2Famazon-blogs-brightspot.s3.amazonaws.com%2Fc9%2Ff9%2F35a61f9e4bffa12c7146113ec46a%2Ffiretvstick-hero-2000x1125.jpg\",\"publishedAt\":\"2022-12-29T19:36:57Z\",\"source\":{\"name\":\"About Amazon\",\"url\":\"https://www.aboutamazon.com\"}},{\"title\":\"How to earn 2X rewards from Ron Jakowski contact missions in GTA Online? (December 29 - January 5)\",\"description\":\"The latest Thursday update is making waves in the GTA community, bringing in multiple rewards and discounts. Furthermore, it introduced 2x rewards for Ron Jakowski's contact missions.\",\"content\":\"The latest Thursday update is making waves in the GTA community, bringing in multiple rewards and discounts. Furthermore, it introduced 2x rewards for Ron Jakowski's contact missions.\\nHowever, beginners should remember that Ron's contact missions dif... [3485 chars]\",\"url\":\"https://www.sportskeeda.com/gta/how-earn-2x-rewards-ron-jakowski-contact-missions-gta-online-december-29-january-5\",\"image\":\"https://staticg.sportskeeda.com/editor/2022/12/a123e-16723378010167-1920.jpg\",\"publishedAt\":\"2022-12-29T19:26:26Z\",\"source\":{\"name\":\"Sportskeeda\",\"url\":\"https://www.sportskeeda.com\"}},{\"title\":\"Asus ZenBook S 13 Flip vs. HP Envy x360 13: the price is key\",\"description\":\"The HP Envy x360 13 and Asus ZenBook S 13 Flip are well-built, fast, and long-lasting. But the Envy is \$500 less expensive, making it the better bargain.\",\"content\":\"Digital Trends may earn a commission when you buy through links on our site. Why trust us?\\nWe love the HP Envy x360 13 convertible 2-in-1 for its excellent build quality, awesome display, and fine performance and battery life. It’s a hard laptop to b... [7030 chars]\",\"url\":\"https://www.digitaltrends.com/computing/asus-zenbook-s-13-flip-vs-hp-envy-x360-13/\",\"image\":\"https://www.digitaltrends.com/wp-content/uploads/2022/10/hp-envy-x360-06.jpg?resize=1200%2C630&p=1\",\"publishedAt\":\"2022-12-29T19:00:36Z\",\"source\":{\"name\":\"Digital Trends\",\"url\":\"https://www.digitaltrends.com\"}},{\"title\":\"The Perceiver Gameplay Deep Dive Revealed by Devs\",\"description\":\"Chinese developer 17ZHE Studio reveals 45 minutes of gameplay for action game Project: The Perceiver with commentary included.\",\"content\":\"Chinese developer 17ZHE Studio is closing 2022 with a staggering 45 minutes of gameplay from its third-person action game, Project: The Perceiver. It’s a content-filled video that shows new cutscenes, combat, traversal, and loads more, all with comme... [1258 chars]\",\"url\":\"https://www.escapistmagazine.com/project-the-perceiver-gameplay-dee-dive-45-minutes-developers/\",\"image\":\"https://www.escapistmagazine.com/wp-content/uploads/2022/12/project-the-perceiver-gameplay-developers.jpg\",\"publishedAt\":\"2022-12-29T18:56:58Z\",\"source\":{\"name\":\"The Escapist\",\"url\":\"https://www.escapistmagazine.com\"}},{\"title\":\"Nvidia's laptop RTX 4080 obliterates its predecessor\",\"description\":\"Rumor has it that Nvidia will soon be launching some laptop GPUs, and it seems that the mobile version of the RTX 4080 will bring a huge performance boost.\",\"content\":\"Nvidia’s RTX 4080 is already out in its desktop version, but all signs point to it soon being released as a laptop GPU. Some people have already gotten their hands on laptops equipped with one of Nvidia’s best graphics cards, and we now know the benc... [2418 chars]\",\"url\":\"https://www.digitaltrends.com/computing/nvidia-rtx-4080-mobile-spotted-in-benchmark/\",\"image\":\"https://www.digitaltrends.com/wp-content/uploads/2022/11/rtx-4080-review-01.jpg?resize=1200%2C630&p=1\",\"publishedAt\":\"2022-12-29T18:46:41Z\",\"source\":{\"name\":\"Digital Trends\",\"url\":\"https://www.digitaltrends.com\"}},{\"title\":\"Samsung's Jet 75, the Dyson cordless vacuum rival, is \$250 off\",\"description\":\"Samsung aims to challenge Dyson's dominance in the vacuum space with products like the Samsung Jet 75 Complete, which is currently on sale with a \$250 discount.\",\"content\":\"Most shoppers look for Dyson deals when they decide to buy a cordless vacuum, but you should know that there are other brands out there that make dependable products in the same category. One of them is Samsung, which is currently selling the Samsung... [1962 chars]\",\"url\":\"https://www.digitaltrends.com/dtdeals/samsung-jet-75-cordless-vacuum-deal-samsung-december-2022/\",\"image\":\"https://www.digitaltrends.com/wp-content/uploads/2022/12/Samsung-Jet-75-Complete-cordless-vacuum.jpg?resize=1200%2C630&p=1\",\"publishedAt\":\"2022-12-29T18:30:10Z\",\"source\":{\"name\":\"Digital Trends\",\"url\":\"https://www.digitaltrends.com\"}},{\"title\":\"Call of Duty Modern Warfare II - What Is Cyber Attack and How It’s Different From Search and Destroy?\",\"description\":\"Call of Duty Modern Warfare II features a new mode in Cyber Attack and it is quite different from the already popular Search and Destroy.\",\"content\":\"Published 12/29/2022, 1:09 PM EST\\nCall of Duty franchise has swayed millions of players this year with the record-setting debut of Modern Warfare II. While the latest installment was already offering a fun experience, it’s been stretched further with... [2166 chars]\",\"url\":\"https://www.essentiallysports.com/esports-news-call-of-duty-modern-warfare-ii-cyber-attack-mode-explained-different-from-search-and-destroy/\",\"image\":\"https://image-cdn.essentiallysports.com/wp-content/uploads/call-of-duty-modern-warfare-II-shipment-map-picture.jpg\",\"publishedAt\":\"2022-12-29T18:09:00Z\",\"source\":{\"name\":\"EssentiallySports\",\"url\":\"https://www.essentiallysports.com\"}},{\"title\":\"Smash director Sakurai says he is \\\"semi-retired\\\" from game development\",\"description\":\"Longtime Super Smash Bros. director Masahiro Sakurai said in a recent interview that his time as a game developer is gradually coming to an end.\",\"content\":\"According to a recent interview from Denfaminico Gamer (and translated by Twitter user PushDustin), Masahiro Sakurai is \\\"semi-retired\\\" from being a longtime game director at Nintendo.\\n\\\"[Sakurai] is 52,\\\" reads the translated tweet. \\\"He felt that if he... [2503 chars]\",\"url\":\"https://www.gamedeveloper.com/business/smash-director-sakurai-says-he-is-semi-retired-from-game-development\",\"image\":\"https://eu-images.contentstack.com/v3/assets/blt95b381df7c12c15d/bltfc2ea1dddc827c8f/630650beaf389656eb3b2919/sakurai.png\",\"publishedAt\":\"2022-12-29T17:57:39Z\",\"source\":{\"name\":\"Game Developer\",\"url\":\"https://www.gamedeveloper.com\"}},{\"title\":\"Your guide to Forza Horizon 5's Horizon Holidays Spring Festival Playlist\",\"description\":\"How to complete this week's Photo Challenge, Forzathon Shop highlights and Weekly Challenge tips, in Forza Horizon 5's Horizon Holidays Spring playlist.\",\"content\":\"Forza Your guide to Forza Horizon 5’s Horizon Holidays Spring Festival Playlist By\\nHow to complete this week’s Photo Challenge, Forzathon Shop highlights and Weekly Challenge tips, in Forza Horizon 5’s Horizon Holidays Spring playlist.\\nThe final week... [2285 chars]\",\"url\":\"https://traxion.gg/your-guide-to-forza-horizon-5s-horizon-holidays-spring-festival-playlist/\",\"image\":\"https://traxion.gg/wp-content/uploads/2022/12/spring_0-1000x600.jpg\",\"publishedAt\":\"2022-12-29T17:26:15Z\",\"source\":{\"name\":\"Traxion\",\"url\":\"https://traxion.gg\"}},{\"title\":\"GTA Online New Year update adds Western Powersurge, Fooligan Job rewards, and more\",\"description\":\"Festive Surprise continues with the GTA Online New Year weekly update today, adding a brand new Western Powersurge motorcycle, many new rewards, and extra bonuses on different game modes.\",\"content\":\"Festive Surprise continues with the GTA Online New Year weekly update today, adding a brand new Western Powersurge motorcycle, many new rewards, and extra bonuses on different game modes. Having started on December 29, 2022, the event will run throug... [3895 chars]\",\"url\":\"https://www.sportskeeda.com/gta/news-gta-online-new-year-update-adds-western-powersurge-fooligan-job-rewards\",\"image\":\"https://staticg.sportskeeda.com/editor/2022/12/8d7f6-16723324878898-1920.jpg\",\"publishedAt\":\"2022-12-29T17:23:21Z\",\"source\":{\"name\":\"Sportskeeda\",\"url\":\"https://www.sportskeeda.com\"}}]}"

        val response2 = "{\"totalArticles\":374626,\"articles\":[{\"title\":\"15 Awesome Indie Games You Should Play on Your New Switch\",\"description\":\"So you finally got a Nintendo Switch. Congratulations! You might feel like you’re five years late to the game, but actually, you’re lucky:\",\"content\":\"15 Awesome Indie Games You Should Play on Your New Switch\\nSo you finally got a Nintendo Switch. Congratulations! You might feel like you’re five years late to the game, but actually, you’re lucky: You’ve got a half decade’s worth of catching up to do... [12685 chars]\",\"url\":\"https://www.lifehacker.com.au/2022/12/15-awesome-indie-games-you-should-play-on-your-new-switch/\",\"image\":\"https://www.lifehacker.com.au/wp-content/uploads/sites/4/2022/12/29/6370b164bf8e0b62bf009c7a66bdc051.jpg?quality=80&resize=1280,720\",\"publishedAt\":\"2022-12-29T22:30:00Z\",\"source\":{\"name\":\"Lifehacker Australia\",\"url\":\"https://www.lifehacker.com.au\"}},{\"title\":\"Warzone 2.0 Season 1 Reloaded: Best hipfire loadout for the PDSW 528 SMG\",\"description\":\"On November 14, 2022, Call of Duty: Warzone 2.0 and Modern Warfare 2.0 launched the mid-season update for Season 1.\",\"content\":\"On November 14, 2022, Call of Duty: Warzone 2.0 and Modern Warfare 2.0 launched the mid-season update for Season 1. Known as Season 1 Reloaded, the most recent update brings brand-new content to both games, including Call of Duty's first-ever raid - ... [3757 chars]\",\"url\":\"https://www.sportskeeda.com/esports/warzone-2-0-season-1-reloaded-best-hipfire-loadout-pdsw-528-smg\",\"image\":\"https://staticg.sportskeeda.com/editor/2022/12/31d3f-16722598980425-1920.jpg\",\"publishedAt\":\"2022-12-29T22:14:41Z\",\"source\":{\"name\":\"Sportskeeda\",\"url\":\"https://www.sportskeeda.com\"}},{\"title\":\"Xbox begins 2023 with Iris Fall and Autonauts for Gold members\",\"description\":\"Xbox announced its first free games of 2023: Iris Fall and Autonauts for those who use its Live Gold service.\",\"content\":\"Connect with gaming and metaverse leaders online at GamesBeat Summit: Into the Metaverse 3 this February 1-2. Register here.\\nXbox has announced its first free games for subscribers in 2023. While the news doesn’t cover the first wave of Game Pass add... [1169 chars]\",\"url\":\"https://venturebeat.com/games/xbox-begins-2023-with-iris-fall-and-autonauts-for-gold-members/\",\"image\":\"https://venturebeat.com/wp-content/uploads/2022/12/Xbox-GWG-January-2023.jpg?w=1200&strip=all\",\"publishedAt\":\"2022-12-29T22:01:08Z\",\"source\":{\"name\":\"VentureBeat\",\"url\":\"https://venturebeat.com\"}},{\"title\":\"Fitbit smartwatch for less than \$200\",\"description\":\"The best deal on Amazon today is the Fitbit Versa 4, marked down \$50 ahead of the new year.\",\"content\":\"Amazon\\nWhen you know better, you do better. And Fitbit’s exercise and sleep tracking technology provides personalized health information to help you create informed goals and develop healthy habits — at a discount, thanks to Amazon.\\nRight now you can... [332 chars]\",\"url\":\"https://www.sfgate.com/shopping/article/best-deals-on-amazon-17683924.php\",\"image\":\"https://s.hdnux.com/photos/01/30/74/70/23318057/3/rawImage.jpg\",\"publishedAt\":\"2022-12-29T21:28:26Z\",\"source\":{\"name\":\"SFGATE\",\"url\":\"https://www.sfgate.com\"}},{\"title\":\"Intel Raptor Lake Mobile CPU Benchmarks Leak, Taking On Desktop Chips Ahead Of CES\",\"description\":\"Intel's 13th-generation mobile processors have sprung a leak as multiple results appear within Geekbench's database.\",\"content\":\"Today we have two leaks on the docket, both from one of our usual sources: the Geekbench database. As always with Geekbench leaks, take them with a grain of salt because it's very easy to fool Geekbench. Still, these two leaks look plenty legitimate,... [2877 chars]\",\"url\":\"https://hothardware.com/news/raptor-lake-mobile-cpu-benchmarks-leak\",\"image\":\"https://images.hothardware.com/contentimages/newsitem/60506/content/Intel_Tiger_Lake.jpg\",\"publishedAt\":\"2022-12-29T21:15:00Z\",\"source\":{\"name\":\"Hot Hardware\",\"url\":\"https://hothardware.com\"}},{\"title\":\"Nintendo Europe running New Year's Switch eShop sale\",\"description\":\"Start the new year with major savings\",\"content\":\"Did you just get a Switch over the holidays? Perhaps you got some Switch eShop cards or some cash and they’re burning a hole in your pockets? If you live in Europe (or you have a European eShop account), why not hop on the Switch eShop today and take... [1097 chars]\",\"url\":\"https://www.gonintendo.com/contents/14398-nintendo-europe-running-new-year-s-switch-eshop-sale\",\"image\":\"https://gonintendo.com/attachments/image/20551/file/medium-0cedc7ac224026eba3107274adce9774.png\",\"publishedAt\":\"2022-12-29T20:36:29Z\",\"source\":{\"name\":\"GoNintendo\",\"url\":\"https://www.gonintendo.com\"}},{\"title\":\"Beatspend app: how to make your own Spotify listening receipt\",\"description\":\"Want to see how much money you've paid toward your favorite artists this year? This nifty app will generate your very own Spotify receipt.\",\"content\":\"It's that time of year again when we're all looking back on what transpired in 2022, and for many music fans, this includes your life's playlist. Streaming services like Spotify and Apple Music recognize this kind of nostalgia, of course, and have lo... [2553 chars]\",\"url\":\"https://www.digitaltrends.com/mobile/beatspend-spotify-how-to-make-your-receipt/\",\"image\":\"https://www.digitaltrends.com/wp-content/uploads/2022/12/beatspend-spotify-receipt-hero-photo.jpg?resize=1200%2C630&p=1\",\"publishedAt\":\"2022-12-29T20:36:11Z\",\"source\":{\"name\":\"Digital Trends\",\"url\":\"https://www.digitaltrends.com\"}},{\"title\":\"Dishonored - Definitive Edition available free on Epic Games Store\",\"description\":\"You can nab 'Dishonored - Definitive Edition' and 'Eximius' for free on the Epic Game Store until January 5th.\",\"content\":\"Gamers can claim Dishonored – Definitive Edition for free on the Epic Games store until January 5th as part of the current rotation of free titles.\\nAlongside Dishonored is a game called Eximius, which is also free until January 5th. Eximius is descri... [773 chars]\",\"url\":\"https://mobilesyrup.com/2022/12/29/dishonored-definitive-edition-free-epic-games-store/\",\"image\":\"https://cdn.mobilesyrup.com/wp-content/uploads/2022/12/dishonored-definitive-edition-header-scaled.jpg\",\"publishedAt\":\"2022-12-29T20:15:00Z\",\"source\":{\"name\":\"MobileSyrup\",\"url\":\"https://mobilesyrup.com\"}},{\"title\":\"5 things GTA Online players should buy before 2023\",\"description\":\"With the new year on the horizon, GTA 5 and its multiplayer variant are approaching their tenth anniversary.\",\"content\":\"With the new year on the horizon, GTA 5 and its multiplayer variant are approaching their tenth anniversary. Although the game is nearly a decade old, players continue to dive into it, and Rockstar Games frequently updates the multiplayer version.\\nWi... [3472 chars]\",\"url\":\"https://www.sportskeeda.com/gta/5-things-gta-online-players-buy-2023\",\"image\":\"https://staticg.sportskeeda.com/editor/2022/12/e3b19-16723185663375-1920.jpg\",\"publishedAt\":\"2022-12-29T20:09:57Z\",\"source\":{\"name\":\"Sportskeeda\",\"url\":\"https://www.sportskeeda.com\"}},{\"title\":\"Nintendo Switch Sports in-game rewards for the week of Dec. 29th, 2022\",\"description\":\"The last content drop of the year\",\"content\":\"A new set of items is now available for players to earn in Nintendo Switch Sports. See below to find out exactly what you can earn and when by playing online matches.\\nHere’s what’s coming to the item section this week. This collection will be availab... [357 chars]\",\"url\":\"https://gonintendo.com/contents/14390-nintendo-switch-sports-in-game-rewards-for-the-week-of-dec-29th-2022\",\"image\":\"https://gonintendo.com/attachments/image/20542/file/medium-8eef4a6b9fa91a0269f424b7147b5243.png\",\"publishedAt\":\"2022-12-29T19:43:41Z\",\"source\":{\"name\":\"GoNintendo\",\"url\":\"https://gonintendo.com\"}}]}"


        /*recyclerView.layoutManager = layoutManager
        mAdapter = MyAdapter(extractData(response2))
        recyclerView.adapter = mAdapter*/

        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            populateNewsList() {returnedData ->
                recyclerView.layoutManager = layoutManager
                mAdapter = MyAdapter(returnedData)
                recyclerView.adapter = mAdapter
            }
            swipeRefresh.isRefreshing = false
        }

        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.queryHint = getString(R.string.searchQueryHint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchQuery = "\"" + query + "\""
                Log.d("SSSSSSSSSSSSS", searchQuery)
                searchPopulateNewsList() {returnedData ->
                    recyclerView.layoutManager = layoutManager
                    mAdapter = MyAdapter(returnedData)
                    recyclerView.adapter = mAdapter
                }
                return false
            }

        })
    }

    private fun startService() {
        val notifierService = Intent(this, NewsNotifierService::class.java)
        notifierService.putExtra(getString(R.string.action), getString(R.string.start))
        startForegroundService(notifierService)
    }
    private fun stopService() {
        val notifierService = Intent(this, NewsNotifierService::class.java)
        notifierService.putExtra(getString(R.string.action), getString(R.string.stop))
        startService(notifierService)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate((R.menu.toolbar_layout), menu)
        val logOutBtn : MenuItem = menu.findItem(R.id.logout_btn)
        logOutBtn.setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {
            mAuth.signOut()
            val newIntent = Intent(this, LoginActivity::class.java)
            startActivity(newIntent)
            false
        })
        val newsCategories: Array<String> = arrayOf(getString(R.string.breaking_news),
            getString(R.string.world), getString(R.string.business), getString(R.string.technology),
            getString(R.string.entertainment), getString(R.string.sports), getString(R.string.science),
            getString(R.string.health))

        val spinner: Spinner = menu.findItem(R.id.spinner).actionView as Spinner
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, newsCategories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                category = selectedItem
                populateNewsList() {returnedData ->
                    recyclerView.layoutManager = layoutManager
                    mAdapter = MyAdapter(returnedData)
                    recyclerView.adapter = mAdapter
                }
                Log.d("||||||||||||||||++++", selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val myView = findViewById<View>(R.id.toolbar_main)
        when (item.itemId){
            R.id.settings_btn -> {
                val newIntent = Intent(this, TopicSelector::class.java)
                startActivity(newIntent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun populateNewsList(callback:(MutableList<MyModel>)->Unit) {
        var finalList = mutableListOf<MyModel>()
        val apikey = getString(R.string.api_key)
        if (category == null) {
            category = getString(R.string.breaking_news)
        }
        val url = getString(R.string.top_headlines_p1) + category + "&token=" + apikey + "&lang=en&max=10"
        Ion.with(this)
            .load(url)
            .setHeader(getString(R.string.user_agent), getString(R.string.user_agent_val))
            .setHeader(getString(R.string.accept), getString(R.string.type))
            .asString()
            .setCallback{ex, result ->
                if (ex == null) {
                    finalList = extractData(result)
                    Log.d("=============", "finalList isEmpty = " + finalList.isEmpty().toString())
                    callback(finalList)
                } else {
                    val recyclerView = findViewById<View>(R.id.news_recycler_view) as RecyclerView
                    val snackbar = Snackbar.make(recyclerView, "Check your internet connection and try again", Snackbar.LENGTH_LONG)
                    val sb: TextView = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text)
                    sb.setTextColor(getColor(R.color.light_orange))
                    snackbar.show()
                }
            }
        }
    private fun searchPopulateNewsList(callback:(MutableList<MyModel>)->Unit) {
        var finalList = mutableListOf<MyModel>()
        val apikey = getString(R.string.api_key)
        val url = getString(R.string.search_headlines_p1) + searchQuery + "&token=" + apikey + "&lang=en&max=10"
        Ion.with(this)
            .load(url)
            .setHeader(getString(R.string.user_agent), getString(R.string.user_agent_val))
            .setHeader(getString(R.string.accept), getString(R.string.type))
            .asString()
            .setCallback{ex, result ->
                if (ex == null) {
                    finalList = extractData(result)
                    Log.d("=============", "finalList isEmpty = " + finalList.isEmpty().toString())
                    callback(finalList)
                } else {
                    val recyclerView = findViewById<View>(R.id.news_recycler_view) as RecyclerView
                    val snackbar = Snackbar.make(recyclerView, getString(R.string.no_internet_msg), Snackbar.LENGTH_LONG)
                    val sb: TextView = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text)
                    sb.setTextColor(getColor(R.color.light_orange))
                    snackbar.show()
                }
            }
    }
    private fun extractData(result:String): MutableList<MyModel> {
        val finalList = mutableListOf<MyModel>()
        val jsonText = result.substring(result.indexOf('['), result.indexOf("}}]")+3)
        val myJSONArray = JSONArray(jsonText)
        for (i in 0..9) {
            val imageModel = MyModel()
            val myJSON = myJSONArray.getJSONObject(i)
            imageModel.setArticleTitle(myJSON.getString(getString(R.string.title)))
            imageModel.setArticleImage(myJSON.getString(getString(R.string.image)))
            imageModel.setArticleSummary(myJSON.getString(getString(R.string.description)))
            imageModel.setArticleContent(myJSON.getString(getString(R.string.content)))
            imageModel.setArticleSourceName(myJSON.getString(getString(R.string.source)))
            imageModel.setArticlePublishedAt(myJSON.getString(getString(R.string.published_at)))
            imageModel.setArticleURL(myJSON.getString(getString(R.string.url)))
            finalList.add(imageModel)
        }
        return finalList
    }
    override fun onStop() {
        startService()
        getSharedPreferences(getString(R.string.mypreference), Context.MODE_WORLD_READABLE)
            .edit()
            .putBoolean(getString(R.string.isRunning), true)
            .commit()
        super.onStop()
    }
    override fun onStart() {
        getSharedPreferences(getString(R.string.mypreference), Context.MODE_WORLD_READABLE)
            .edit()
            .putBoolean(getString(R.string.isRunning), false)
            .commit()
        Timer().schedule(object : TimerTask() {
            override fun run() {
                stopService()
            }
        }, 1000)
        Log.d("MMMMMMMMMMMMM", "Service stopped!")
        super.onStart()
    }

    override fun onDestroy() {
        Log.d("MMMMMMMMMMMMM", "Service started! (main activity destroyed)")
        super.onDestroy()
    }
}