import * as tripFileSchema from '../../schemas/TripFile.json';
import { isJsonResponseValid } from './restfulAPI';
import { Place } from '../models/place.model';

export function SavePlaces(fileNameWithExtension, mimeType, fileText) {
    const file = new Blob([fileText], { type: mimeType });
    const link = document.createElement("a");
    const url = URL.createObjectURL(file);
    link.href = url;
    link.download = fileNameWithExtension;
    document.body.appendChild(link);
    link.click();
    setTimeout(function() {
      document.body.removeChild(link);
      window.URL.revokeObjectURL(url);
    }, 0);
}