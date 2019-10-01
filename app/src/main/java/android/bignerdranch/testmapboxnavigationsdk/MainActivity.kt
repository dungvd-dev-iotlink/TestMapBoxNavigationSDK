package android.bignerdranch.testmapboxnavigationsdk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TOKEN = "pk.eyJ1IjoiY2h1bmdsdiIsImEiOiJjazB5cmplNmcwaWRtM2JwMWswMDFvdGo4In0.NIlvkg3MuVXV8w0ipd_6Kg"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, TOKEN)
        setContentView(R.layout.activity_main)
        updateNavigation()
    }

    private fun updateNavigation() {
        // From Mapbox to The White House
        val origin = Point.fromLngLat(108.216152, 16.076858)
        val destination = Point.fromLngLat(108.223383, 16.071734)

        NavigationRoute.builder(this)
            .accessToken(TOKEN)
            .origin(origin)
            .destination(destination)
            .build()
            .getRoute(object : Callback<DirectionsResponse> {
                override fun onResponse(call: Call<DirectionsResponse>, response: Response<DirectionsResponse>) {
                    val routes = response.body()?.routes()
                    val route = routes?.get(0)
                    val simulateRoute = true

                    // Create a NavigationLauncherOptions object to package everything together
                    val options = NavigationLauncherOptions.builder()
                        .directionsRoute(route)
                        .shouldSimulateRoute(simulateRoute)
                        .build()

                    // Call this method with Context from within an Activity
                    NavigationLauncher.startNavigation(this@MainActivity, options)
                }

                override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {

                }
            })
    }
}
