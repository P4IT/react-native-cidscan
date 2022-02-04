"use strict";

var _propTypes = _interopRequireDefault(require("prop-types"));

var _react = require("react");

var _reactNative = require("react-native");

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

class CIDScanView extends _react.Component {
  constructor(props) {
    super(props);
    this.onPreviewReady = this._onPreviewReady.onPreviewReady.bind(this);
  }

  _onPreviewReady(event) {
    if (!this.props.onPreviewReady) {
      return;
    }

    console.log('_onPreviewReady');
    this.props.onPreviewReady(event.nativeEvent);
  }

  render() {
    return /*#__PURE__*/React.createElement(RNCIDScanView, {
      onPreviewReady: this._onPreviewReady
    });
  }

}

CIDScanView.propTypes = { ..._reactNative.View.propTypes,
  onPreviewReady: _propTypes.default.func
};
const RNCIDScanView = (0, _reactNative.requireNativeComponent)('RNCIDScanView');
module.exports = RNCIDScanView;
//# sourceMappingURL=cidscanview.js.map