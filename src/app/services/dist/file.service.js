"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
var core_1 = require("@angular/core");
var FileService = /** @class */ (function () {
    function FileService(http) {
        this.http = http;
        this.server = 'http://localhost:8080';
    }
    // define function to upload files
    FileService.prototype.upload = function (formData) {
        return this.http.post(this.server + "/file/upload", formData, {
            reportProgress: true,
            observe: 'events'
        });
    };
    // define function to download files
    FileService.prototype.download = function (filename) {
        return this.http.get(this.server + "/file/download/" + filename + "/", {
            reportProgress: true,
            observe: 'events',
            responseType: 'blob'
        });
    };
    FileService = __decorate([
        core_1.Injectable({
            providedIn: 'root'
        })
    ], FileService);
    return FileService;
}());
exports.FileService = FileService;

//# sourceMappingURL=file.service.js.map
