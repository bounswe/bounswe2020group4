import React, { useState, useEffect } from 'react'
import GoogleMapReact from 'google-map-react'
import MapMarker from '../images/map-marker.png'

import './GoogleMaps.css'

const GoogleMaps = ({ selectedCoord, setSelectedCoord, useDefaultCenter }) => {
	const [coord, setCoord] = useState()

	useEffect(() => {
		navigator.geolocation.getCurrentPosition((pos) => {
			if (useDefaultCenter) {
				setCoord({ lat: pos.coords.latitude, lng: pos.coords.longitude})
			}
		})
	}, [])

	useEffect(() => {
		if (!useDefaultCenter && selectedCoord && !coord) {
			setCoord({ lat: parseFloat(selectedCoord.lat), lng: parseFloat(selectedCoord.lng) })
		}
	}, [selectedCoord?.lat, selectedCoord?.lng])

	const handleApiLoaded = (map) => {
		map.addListener('click', (e) => {
			const latLng = { lat: e.latLng.lat(), lng: e.latLng.lng() }
			setSelectedCoord(latLng)
			map.panTo(latLng)
		})
	}

	return (
		<div className={ coord ? 'google-maps-container cursor-pointer' : null }>
			{ coord ?
				<GoogleMapReact
					bootstrapURLKeys={{ key: 'AIzaSyCYMANjIVMZ7MYjbi6CpEEZfs4OFVTi8FE' }}
					defaultCenter={coord}
					defaultZoom={11}
					yesIWantToUseGoogleMapApiInternals
					onGoogleApiLoaded={({ map, maps }) => handleApiLoaded(map, maps)}
				>
					{ selectedCoord ?
						<div className='marker-container' lat={selectedCoord.lat} lng={selectedCoord.lng}>
							<img className='marker' src={MapMarker} alt='marker'/>
						</div>
						: null
					}
				</GoogleMapReact>
				: null
			}
		</div>
	)
}

export default GoogleMaps