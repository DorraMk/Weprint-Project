"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
var http_1 = require("@angular/common/http");
var core_1 = require("@angular/core");
var ImpressionComponent = /** @class */ (function () {
    function ImpressionComponent(fileService) {
        this.fileService = fileService;
        this.filenames = [];
        this.fileStatus = { status: '', requestType: '', percent: 0 };
    }
    // define a function to upload files
    ImpressionComponent.prototype.onUploadFiles = function (files) {
        var _this = this;
        var formData = new FormData();
        for (var _i = 0, files_1 = files; _i < files_1.length; _i++) {
            var file = files_1[_i];
            formData.append('files', file, file.name);
        }
        this.fileService.upload(formData).subscribe(function (event) {
            console.log(event);
            _this.resportProgress(event);
        }, function (error) {
            console.log(error);
        });
    };
    // define a function to download files
    ImpressionComponent.prototype.onDownloadFile = function (filename) {
        var _this = this;
        this.fileService.download(filename).subscribe(function (event) {
            console.log(event);
            _this.resportProgress(event);
        }, function (error) {
            console.log(error);
        });
    };
    ImpressionComponent.prototype.resportProgress = function (httpEvent) {
        switch (httpEvent.type) {
            case http_1.HttpEventType.UploadProgress:
                this.updateStatus(httpEvent.loaded, httpEvent.total, 'Uploading... ');
                break;
            case http_1.HttpEventType.DownloadProgress:
                this.updateStatus(httpEvent.loaded, httpEvent.total, 'Downloading... ');
                break;
            case http_1.HttpEventType.ResponseHeader:
                console.log('Header returned', httpEvent);
                break;
            case http_1.HttpEventType.Response:
                if (httpEvent.body instanceof Array) {
                    this.fileStatus.status = 'done';
                    for (var _i = 0, _a = httpEvent.body; _i < _a.length; _i++) {
                        var filename = _a[_i];
                        this.filenames.unshift(filename);
                    }
                }
                else {
                    saveAs(new File([httpEvent.body], httpEvent.headers.get('File-Name'), { type: httpEvent.headers.get('Content-Type') + ";charset=utf-8" }));
                    // saveAs(new Blob([httpEvent.body!], 
                    //   { type: `${httpEvent.headers.get('Content-Type')};charset=utf-8`}),
                    //    httpEvent.headers.get('File-Name'));
                }
                this.fileStatus.status = 'done';
                break;
            default:
                console.log(httpEvent);
                break;
        }
    };
    ImpressionComponent.prototype.updateStatus = function (loaded, total, requestType) {
        this.fileStatus.status = 'progress';
        this.fileStatus.requestType = requestType;
        this.fileStatus.percent = Math.round(100 * loaded / total);
    };
    ImpressionComponent = __decorate([
        core_1.Component({
            selector: 'app-impression',
            templateUrl: './impression.component.html',
            styleUrls: ['./impression.component.css']
        })
    ], ImpressionComponent);
    return ImpressionComponent;
}());
exports.ImpressionComponent = ImpressionComponent;
function saveAs(arg0) {
    throw new Error('Function not implemented.');
}

//# sourceMappingURL=impression.component.js.map
