import PropTypes from 'prop-types';
import { requireNativeComponent, ViewPropTypes } from 'react-native';
var viewProps = {
  name: 'RNCIDScanView',
  propTypes: {
    config: PropTypes.object,
    ...ViewPropTypes
  }
};
module.exports = requireNativeComponent('RNCIDScanView', viewProps);
//# sourceMappingURL=cidscanview.js.map