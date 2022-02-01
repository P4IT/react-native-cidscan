import PropTypes from 'prop-types';
import { Component } from 'react';
import { requireNativeComponent, View, ViewPropTypes } from 'react-native';

class CIDScanView extends Component {
  
  constructor(props) {
    super(props);
    this.onPreviewReady = this._onPreviewReady.onPreviewReady.bind(this);
  }

  _onPreviewReady(event) {
    if(!this.props.onPreviewReady) {
      return;
    }
    console.log('_onPreviewReady');
    this.props.onPreviewReady(event.nativeEvent);
  }

  render() {
    return <RNCIDScanView onPreviewReady={this.onPreviewReady}/>
  }
}

CIDScanView.propTypes = {
  ...View.propTypes,
  onPreviewReady: PropTypes.func
}


const RNCIDScanView = requireNativeComponent('RNCIDScanView');
module.exports = RNCIDScanView;