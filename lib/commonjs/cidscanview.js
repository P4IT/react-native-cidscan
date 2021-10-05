"use strict";

var _propTypes = _interopRequireDefault(require("prop-types"));

var _reactNative = require("react-native");

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var viewProps = {
  name: 'RNCIDScanView',
  propTypes: {
    config: _propTypes.default.object,
    ..._reactNative.ViewPropTypes
  }
};
module.exports = (0, _reactNative.requireNativeComponent)('RNCIDScanView', viewProps);
//# sourceMappingURL=cidscanview.js.map