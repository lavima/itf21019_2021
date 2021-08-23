package no.hiof.larseknu.playingwithgooglemaps


import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment(), OnMapReadyCallback {


    private lateinit var gmap: GoogleMap
    private lateinit var layerSpinner : Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        layerSpinner = view.findViewById(R.id.layersSpinner)
        setupLayerSpinner()

        return view
    }

    private fun setupLayerSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.layers_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        layerSpinner.adapter = adapter
        layerSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when(parent.getItemAtPosition(position).toString()) {
                    getString(R.string.hybrid) -> gmap.mapType = GoogleMap.MAP_TYPE_HYBRID
                    getString(R.string.satellite) -> gmap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                    getString(R.string.terrain) -> gmap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                    getString(R.string.normal) -> gmap.mapType = GoogleMap.MAP_TYPE_NORMAL
                    getString(R.string.none) -> gmap.mapType = GoogleMap.MAP_TYPE_NONE
                }

            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        gmap.setOnMapLongClickListener { latLng ->
            val markerOptions = MarkerOptions()
                .position(latLng)
                .title("A new marker!")
                .snippet("Minor text")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_icon))

            gmap.addMarker(markerOptions)
        }

        setupUISettings()

        gmap.addMarker(MarkerOptions().position(HIOF).title("Østfold University College"))
        gmap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(HIOF, 15f, 0f, 0f)))

        gmap.addMarker(MarkerOptions().position(FREDRIKSTAD).title("Fredrikstad Kino"))
        gmap.animateCamera(CameraUpdateFactory.newLatLng(FREDRIKSTAD), 2000, null)

    }

    @AfterPermissionGranted(LOCATION_PERMISSION_ID)
    private fun setupUISettings() {

        if (EasyPermissions.hasPermissions(context!!, Manifest.permission.ACCESS_FINE_LOCATION)) {
            gmap.isMyLocationEnabled = true
            gmap.uiSettings.isMyLocationButtonEnabled = true
        }
        else {
            EasyPermissions.requestPermissions(this, "We need this permission to show your location on the map",
                LOCATION_PERMISSION_ID, Manifest.permission.ACCESS_FINE_LOCATION)
        }

        gmap.uiSettings.setAllGesturesEnabled(true)
        gmap.uiSettings.isCompassEnabled = true
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    companion object {
        private const val LOCATION_PERMISSION_ID = 1;
        private val HIOF = LatLng(59.12797849, 11.35272861)
        private val FREDRIKSTAD = LatLng(59.21047628, 10.93994737)
    }
}
