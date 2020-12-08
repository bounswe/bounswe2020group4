import React from 'react'
import GoogleMapReact from 'google-map-react';
import { Map, GoogleApiWrapper, Marker, InfoWindow } from 'google-maps-react';

const mapStyles = {
    width: '100%',
    height: '100%'
  };

const GoogleMaps = (props) => {

    var state = {
        showingInfoWindow: false,  // Hides or shows the InfoWindow
        activeMarker: {},          // Shows the active marker upon click
        selectedPlace: {}          // Shows the InfoWindow to the selected place upon a marker
      };

     const onMarkerClick = (props, marker, e) =>
      this.setState({
        selectedPlace: props,
        activeMarker: marker,
        showingInfoWindow: true
      });
  
    const onClose = props => {
      if (this.state.showingInfoWindow) {
        this.setState({
          showingInfoWindow: false,
          activeMarker: null
        });
      }
    };

    return (
        <div >
          <Map
        google={props.google}
        zoom={14}
        style={mapStyles}
        initialCenter={
          {
            lat: -1.2884,
            lng: 36.8233
          }
        }
      />
      <Marker
          onClick={onMarkerClick}
          name={'Kenyatta International Convention Centre'}
        />
        <InfoWindow
          marker={state.activeMarker}
          visible={state.showingInfoWindow}
          onClose={onClose}
        >
          <div>
            <h4>{state.selectedPlace.name}</h4>
          </div>
        </InfoWindow>
        </div>
      );

}

export default GoogleApiWrapper({
    apiKey: '' //put api key here
  })(GoogleMaps)