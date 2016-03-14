var JSONFileRW = function () {
};
JSONFileRW.prototype.writeJSON = function (successCallback, errorCallback, base64String, params) {
    cordova.exec(successCallback, errorCallback, "JSONFileRW", "writeJSON", [base64String, params]);
};

JSONFileRW.prototype.readJSON = function (successCallback, errorCallback, base64String, params) {
    cordova.exec(successCallback, errorCallback, "JSONFileRW", "readJSON", [base64String, params]);
};

if (!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.JSONFileRW) {
    window.plugins.JSONFileRW = new JSONFileRW();
}
