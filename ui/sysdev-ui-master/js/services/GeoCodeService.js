/* global sysdevinterface */

sysdevinterface.service('reverseGeocode', ['$http', function ($http) {

  const nominatimUrl = 'https://nominatim.openstreetmap.org/reverse?format=json';

  this.reverseMarker = function (model, index) {
    let lat = model.map.markers[index].lat;
    let lon = model.map.markers[index].lng;

    $http.get(nominatimUrl + '&lat=' + lat + '&lon=' + lon)
      .then(response => {
        if (response.data && response.data.display_name) {
          model.map.markers[index].formattedAddress =
            response.data.display_name.split(', ');
          model.infoDrop = true;
        }
      })
      .catch(err => console.log('Geocode error:', err));
  };

  this.reverseInstructions = function (model) {
    for (let feature of model.map.geojson.data.features) {
      for (let index in feature.properties.instructions) {
        let instr = feature.properties.instructions[index];

        if (instr instanceof Array || instr instanceof Object) {
          let lat = instr[0];
          let lon = instr[1];

          $http.get(nominatimUrl + '&lat=' + lat + '&lon=' + lon)
            .then(response => {
              if (response.data && response.data.display_name) {
                feature.properties.instructions[index] =
                  response.data.display_name.split(', ')[0];
              }
            })
            .catch(err => console.log('Instruction geocode error:', err));
        }
      }
    }
  };

}]);

