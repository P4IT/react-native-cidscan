import PropTypes from 'prop-types';
import { requireNativeComponent, ViewPropTypes } from 'react-native';

var viewProps = {
  name: 'RNCIDScanView',
  propTypes: {
    config: PropTypes.object,
    onPreviewReady: PropTypes.func,
    ...ViewPropTypes,
  },
};

module.exports = requireNativeComponent('RNCIDScanView', viewProps);
