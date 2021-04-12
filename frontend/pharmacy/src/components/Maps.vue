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

export default {
    data: function () {
        return {
        }
    },
    async mounted() { 
        await this.kreiranjeMape();
    },
    created() {
    },
    methods: {
        kreiranjeMape() {
            // kreiranje vektora
            var vektor = new VectorLayer({
                source: new VectorSource(),
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
                var koordinate = proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');
                alert(koordinate);
            });
        },
    },
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