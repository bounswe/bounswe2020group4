package com.cmpe352group4.buyo.ui.googlemap

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_login_vendor.*
import kotlinx.android.synthetic.main.fragment_maps.*
import javax.inject.Inject

class MapsFragment : BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref

    companion object {
        fun newInstance() = MapsFragment()
    }

    lateinit var marker: Marker
    var markerSet: Boolean = false

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val initialLoc = LatLng(40.33, 36.54)
        googleMap.uiSettings.isRotateGesturesEnabled = false
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(initialLoc))

        googleMap.setOnMapClickListener {
            if (markerSet) {
                marker.remove()
            }
            marker = googleMap.addMarker(MarkerOptions().position(it).title("Selected location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            location_select.isEnabled = true
            location_select.alpha = 1f
            markerSet = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        selectLocButtonListener()
    }

    private fun selectLocButtonListener() {
        location_select.setOnClickListener {
            sharedPref.saveVendorLoc(marker)
            activity?.onBackPressed()
        }
    }
}