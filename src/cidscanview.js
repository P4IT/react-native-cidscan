import PropTypes from 'prop-types';
import { requireNativeComponent, ViewPropTypes } from 'react-native';

var viewProps = {
  name: 'CIDScanView',
  propTypes: {
    config: PropTypes.object,
    ...ViewPropTypes,
  },
};

module.exports = requireNativeComponent('CIDScanView', viewProps);
