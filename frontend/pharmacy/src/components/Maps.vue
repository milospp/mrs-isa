<template>
    <div id="map"></div>
</template>
<script>

import Map from 'ol/Map';
import View from 'ol/View';
import {Tile as TileLayer} from 'ol/layer';
import {Vector as VectorLayer} from 'ol/layer';
import {OSM, Vector as VectorSource} from 'ol/source';
import { defaults as defaultControls, ScaleLine } from 'ol/control';
import * as proj from 'ol/proj';
import * as ol from 'ol';
import * as geom from 'ol/geom';
import {Point as tacka} from 'ol/geom';
import {Feature as Feature} from 'ol/Feature';
import { DragFeature as DragFeature } from 'ol/control';
import 'ol/ol.css'


export default {
    data() {
         return {
                trenutnaPozicija: {lat: 45.252600, lon: 19.830002, adresa: "Cirpanova 51, Novi Sad"}
            }
    },
    mounted() { 
        this.kreiranjeMape();
    },
    methods: {
       obeleziTackom(koordinate) {
        //    ovo zavisi da li korisnik odabere ok
           this.trenutnaPozicija.lon = koordinate[0];
           this.trenutnaPozicija.lat = koordinate[1];
            fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + this.trenutnaPozicija.lon + '&lat=' + this.trenutnaPozicija.lat)
                 .then(function(response) {
                        return response.json();
                    }).then(function(json) {
                        console.log(json);
                        
                    });
        },
        kreiranjeMape() {
            let self = this;
            // kreiranje vektora
            var vectorSource = new VectorSource({});
            var vektor = new VectorLayer({
                source: vectorSource,
            });
            // kreiranje prikaza
            var prikaz = new TileLayer({
                source: new OSM(),
            });
            // kreiranje mape
            var mapa = new Map({
                controls: defaultControls().extend([
                    new ScaleLine({
                        units: "degrees",
                    }),
                ]),
                target: "map",
                layers: [prikaz, vektor],
                view: new View({
                    maxZoom: 25,
                    center: [	2247066.76, 	5659454.38],
                    zoom: 8
                    }),
                });
            mapa.on('singleclick', function (evt) {
                var koordinate = proj.toLonLat(evt.coordinate);
                var kruzic = new ol.Feature(new tacka(proj.fromLonLat(koordinate)));
                vectorSource.addFeature(kruzic);
                self.obeleziTackom(koordinate);

            });
        },
    }
};
</script>
<style>
#map {
  position: absolute;
  margin: 1%;
  padding: 1%;
  height: 700px;
  width:  100%;
  text-align: center;
}
</style>