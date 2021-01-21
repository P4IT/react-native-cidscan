"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
var _exportNames = {};
exports.default = void 0;

var _reactNative = require("react-native");

var _cbresult = require("./cbresult");

Object.keys(_cbresult).forEach(function (key) {
  if (key === "default" || key === "__esModule") return;
  if (Object.prototype.hasOwnProperty.call(_exportNames, key)) return;
  if (key in exports && exports[key] === _cbresult[key]) return;
  Object.defineProperty(exports, key, {
    enumerable: true,
    get: function () {
      return _cbresult[key];
    }
  });
});
const {
  CIDScan
} = _reactNative.NativeModules;
var _default = CIDScan;
exports.default = _default;
//# sourceMappingURL=index.js.map