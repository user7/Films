package com.geekbrains.films.view.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.geekbrains.films.R
import com.geekbrains.films.databinding.MapsFragmentBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class MapsFragment : Fragment(), CoroutineScope by MainScope() {
    private lateinit var map: GoogleMap
    private var menu: Menu? = null
    private val markers: ArrayList<Marker> = ArrayList()
    private var _binding: MapsFragmentBinding? = null
    private val binding get() = _binding!!
    private var i = 0;

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        val place = LatLng(44.952117, 34.102417)
        val marker = googleMap.addMarker(
            MarkerOptions().position(place).title(getString(R.string.start_marker))
        )
        marker?.let { markers.add(marker) }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(place))
        googleMap.setOnMapLongClickListener { latLng ->
            ++i
            val prev = markers.last().position
            setMarker(latLng, "long click $i")
            val cur = markers.last().position
            map.addPolyline(PolylineOptions().add(prev, cur).color(Color.RED).width(5f))
        }
    }

    private fun setMarker(position: LatLng, title: String) {
        map.addMarker(
            MarkerOptions().position(position).title(title)
        )?.let { markers.add(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}