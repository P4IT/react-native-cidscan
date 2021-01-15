#import "CIDScan.h"
#import "CaptureIDLibrary.h"

@implementation CIDScan

RCT_EXPORT_MODULE()

- (NSArray<NSString *> *)supportedEvents {
  return @[@"decoderEvent"];
}

// Send a result object to JavaScript, the event is called "decoderEvent".
// to get the value from the decoder, the customer need to add a listener.
- (void)barcodeEventReceived:(NSArray *) result {
   [self sendEventWithName:@"decoderEvent" body:@{@"result": result}];
}

//public void initCaptureID()
RCT_EXPORT_METHOD(initCaptureID:(RCTResponseSenderBlock)callback) {
  NSLog(@"initCaptureID");
  // get display size
  CGFloat width = [UIScreen mainScreen].bounds.size.width;
  CGFloat height = [UIScreen mainScreen].bounds.size.height;
  
  // run on main thread to init the UIView
  dispatch_async(dispatch_get_main_queue(), ^{
    self.previewView = [[UIView alloc]initWithFrame:CGRectMake(0, 0, width, height)];
    //[self.previewView setBackgroundColor:[UIColor yellowColor]];
    self.previewView.alpha = 0.0f;
    //self.previewView.opaque = NO;
    //self.previewView.backgroundColor = [UIColor clearColor];
    UIWindow *currentWindow = [UIApplication sharedApplication].keyWindow;
    [currentWindow addSubview:self.previewView];
      self.CPID_decoder = [[CaptureIDLibrary alloc]initWithUIview:self.previewView resultBlock:^(BOOL result){}];
    NSArray * result = [self.CPID_decoder getSdkVersion];
    if(callback != nil)
        callback(@[[NSNull null], result]);
  });
}

//public void activateLicense(String key, Callback callback)
RCT_EXPORT_METHOD(activateLicense:(NSString *)key callback:(RCTResponseSenderBlock)callback) {
  [self.CPID_decoder activateLicense:key resultHandler:^(NSArray * _Nonnull result) {
    callback(@[[NSNull null], result]);
  }];
}

//public void activateEDKLicense(String filename, String customerID, Callback callback) {
RCT_EXPORT_METHOD(activateEDKLicense:(NSString *)filename customerID:(NSString *)customerID callback:(RCTResponseSenderBlock)callback) {
  [self.CPID_decoder activateEDKLicense:filename customerID:customerID resultHandler:^(NSArray * _Nonnull result) {
    callback(@[[NSNull null], result]);
  }];
}

//public void activateEDKLicenseWithKey(String key, String customerID, Callback callback) {
RCT_EXPORT_METHOD(activateEDKLicenseWithKey:(NSString *)key customerID:(NSString *)customerID callback:(RCTResponseSenderBlock)callback) {
  dispatch_async(dispatch_get_main_queue(), ^{
    [self.CPID_decoder activateEDKLicenseWithKey:key customerID:customerID resultHandler:^(NSArray * _Nonnull result) {
      callback(@[[NSNull null], result]);
    }];
  });
}

//public void captureCurrentImageInBuffer(Boolean value)
RCT_EXPORT_METHOD(captureCurrentImageInBuffer:(BOOL) value) {
  [self.CPID_decoder captureImage:value];
}

//public void changeBeepPlayerSound(String value)
RCT_EXPORT_METHOD(changeBeepPlayerSound:(NSString *) value) {
  //todo get file name from the JSON - Array
  [self.CPID_decoder changeSound:@""];
}

//public void closeCamera()
RCT_EXPORT_METHOD(closeCamera) {
  dispatch_async(dispatch_get_main_queue(), ^{
    [self.CPID_decoder stopDecoding];
    [self.CPID_decoder stopCameraPreview];
    self.previewView.alpha = 0.0f;
  });
}

//public void closeSharedObject()
RCT_EXPORT_METHOD(closeSharedObject) {
  [self.CPID_decoder closeSharedObject];
}

//public void currentSizeOfDecoderVideo(Callback success)
RCT_EXPORT_METHOD(currentSizeOfDecoderVideo:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder currentSizeOfDecoderVideo];
  callback(@[[NSNull null], result]);
}

//public void decoderTimeLimintInMilliseconds(int value)
RCT_EXPORT_METHOD(decoderTimeLimintInMilliseconds:(NSNumber *)timeLimit) {
  [self.CPID_decoder decoderTimeLimitInMilliseconds:timeLimit.intValue];
}

//public void decoderVersion(Callback success)
RCT_EXPORT_METHOD(decoderVersion:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder decoderVersion];
  callback(@[[NSNull null], result]);
}

//public void decoderVersionLevel(Callback success)
RCT_EXPORT_METHOD(decoderVersionLevel:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder decoderVersionLevel];
  callback(@[[NSNull null], result]);
}

//public void doDecode(Callback success)
RCT_EXPORT_METHOD(doDecode:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder doDecode];
  callback(@[[NSNull null], result]);
}

//public void enableBeepPlayer(boolean value)
RCT_EXPORT_METHOD(enableBeepPlayer:(BOOL)value) {
  [self.CPID_decoder enableBeepPlayer:value];
}

//public void enableFixedExposureMode(boolean value)
RCT_EXPORT_METHOD(enableFixedExposureMode:(BOOL)value) {
  [self.CPID_decoder enableFixedExposureMode:value];
}

//public void enableScannedImageCapture(boolean value)
RCT_EXPORT_METHOD(enableScannedImageCapture:(BOOL)value) {
  [self.CPID_decoder enableScannedImageCapture:value];
}

//public void enableVibrateOnScan(boolean value)
RCT_EXPORT_METHOD(enableVibrateOnScan:(BOOL)value) {
  [self.CPID_decoder enableVibrateOnScan:value];
}

//public void ensureRegionOfInterest(boolean value)
RCT_EXPORT_METHOD(ensureRegionOfInterest:(BOOL)value) {
  [self.CPID_decoder ensureRegionOfInterest:value];
}

//public void generateDeviceID(Callback success)
RCT_EXPORT_METHOD(generateDeviceID:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder generateDeviceID];
  callback(@[[NSNull null], result]);
}

//public void getCameraPreview()
RCT_EXPORT_METHOD(getCameraPreview) {
  NSArray *result = [self.CPID_decoder getCameraPreview];
  NSLog(@"getCameraPreview function not supported");
}

//public void getDecoderVal()
RCT_EXPORT_METHOD(getDecoderVal) {
  NSArray *result = [self.CPID_decoder getDecodeVal];
  NSLog(@"getDecoderVal function not supported");
}

//public void getExposureTime(Callback success)
RCT_EXPORT_METHOD(getExposureTime:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder getExposureTime];
  callback(@[[NSNull null], result]);
}

//public void getFocusDistance(Callback success)
RCT_EXPORT_METHOD(getFocusDistance:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder getFocusDistance];
  callback(@[[NSNull null], result]);
}

//public void getLicensedSymbologies(Callback success)
RCT_EXPORT_METHOD(getLicensedSymbologies:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder getLicensedSymbologies];
  callback(@[[NSNull null], result]);
}

//public void getMaxZoom(Callback success)
RCT_EXPORT_METHOD(getMaxZoom:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder getMaxZoom];
  callback(@[[NSNull null], result]);
}

//public void getSDKVersion(Callback success)
RCT_EXPORT_METHOD(getSDKVersion:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder getSdkVersion];
  callback(@[[NSNull null], result]);
}

//public void getSensitivityBoost(Callback success)
RCT_EXPORT_METHOD(getSensitivityBoost:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder getSensitivityBoost];
  callback(@[[NSNull null], result]);
}

//public void getSizeForROI(Callback success)
RCT_EXPORT_METHOD(getSizeForROI:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder getSizeForROI];
  callback(@[[NSNull null], result]);
}

//public void getSupportedCameraTypes(Callback success)
RCT_EXPORT_METHOD(getSupportedCameraTypes:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder getSupportedCameraTypes];
  callback(@[[NSNull null], result]);
}

//public void getSupportedFocusModes(Callback success)
RCT_EXPORT_METHOD(getSupportedFocusModes:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder getSupportedFocusModes];
  callback(@[[NSNull null], result]);
}

//public void getSupportedWhiteBalance(Callback success)
RCT_EXPORT_METHOD(getSupportedWhiteBalance:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder getSupportedWhiteBalance];
  callback(@[[NSNull null], result]);
}

//public void getZoomRatios(Callback success)
RCT_EXPORT_METHOD(getZoomRatios:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder getZoomRatios];
  callback(@[[NSNull null], result]);
}

//public void hasTorch(Callback success)
RCT_EXPORT_METHOD(hasTorch:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder hasTorch];
  callback(@[[NSNull null], result]);
}

//public void isLicenseActivated(Callback success)
RCT_EXPORT_METHOD(isLicenseActivated:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder isLicenseActivated];
  callback(@[[NSNull null], result]);
}

//public void isLicenseExpired(Callback success)
RCT_EXPORT_METHOD(isLicenseExpired:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder isLicenseExpired];
  callback(@[[NSNull null], result]);
}

//public void isZoomSupported(Callback success)
RCT_EXPORT_METHOD(isZoomSupported:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder isZoomSupported];
  callback(@[[NSNull null], result]);
}

//public void libraryVersion(Callback success)
RCT_EXPORT_METHOD(libraryVersion:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder libraryVersion];
  callback(@[[NSNull null], result]);
}

//public void loadLicenseFile(String filename, Callback success)
RCT_EXPORT_METHOD(loadLicenseFile:(NSString *)filename callback:(RCTResponseSenderBlock)callback) {
  NSArray *result = [self.CPID_decoder loadLicenseFile:filename];
  callback(@[[NSNull null], result]);
}

//public void lowContrastDecodingEnabled(boolean value)
RCT_EXPORT_METHOD(lowContrastDecodingEnabled:(BOOL)value) {
  [self.CPID_decoder lowContrastDecodingEnabled:value];
}

//public void playBeepSound()
RCT_EXPORT_METHOD(playBeepSound) {
  [self.CPID_decoder playBeepSound];
}

//public void regionOfInterestHeight(int value)
RCT_EXPORT_METHOD(regionOfInterestHeight:(NSNumber *)value) {
  [self.CPID_decoder regionOfInterestHeight:value.intValue];
}

//public void regionOfInterestLeft(int value)
RCT_EXPORT_METHOD(regionOfInterestLeft:(NSNumber *)value) {
  [self.CPID_decoder regionOfInterestLeft:value.intValue];
}

//public void regionOfInterestTop(int value)
RCT_EXPORT_METHOD(regionOfInterestTop:(NSNumber *)value) {
  [self.CPID_decoder regionOfInterestTop:value.intValue];
}

//public void regionOfInterestWidth(int value)
RCT_EXPORT_METHOD(regionOfInterestWidth:(NSNumber *)value) {
  [self.CPID_decoder regionOfInterestWidth:value.intValue];
}

//public void setAutoFocusResetByCount(boolean value)
RCT_EXPORT_METHOD(setAutoFocusResetByCount:(BOOL)value) {
  [self.CPID_decoder setAutoFocusResetByCount:value];
}

//public void setCameraType(String value)
RCT_EXPORT_METHOD(setCameraType:(NSString *)value) {
  [self.CPID_decoder setCameraType:value];
}

//public void setCameraZoom(boolean enable, float zoom)
RCT_EXPORT_METHOD(setCameraZoom:(BOOL)value zommValue:(NSNumber *)zoom) {
  [self.CPID_decoder setCameraZoom:value cameraZoom:zoom];
}

//public void setDecoderResolution(String value)
RCT_EXPORT_METHOD(setDecoderResolution:(NSString *)value) {
  [self.CPID_decoder setDecoderResolution:value];
}

//public void setDecoderToleranceLevel(int value)
RCT_EXPORT_METHOD(setDecoderToleranceLevel:(NSNumber *)value) {
  [self.CPID_decoder setDecoderToleranceLevel:value.intValue];
}

//public void setEncodingCharsetName(String charset)
RCT_EXPORT_METHOD(setEncodingCharsetName:(NSString *)charset) {
  [self.CPID_decoder setEncodingCharsetName:charset];
}

//public void setExactlyNBarcodes(boolean enable)
RCT_EXPORT_METHOD(setExactlyNBarcodes:(BOOL)enable) {
  [self.CPID_decoder setExactlyNBarcodes:enable];
}

//public void setExposureSensitivity(String iso)
RCT_EXPORT_METHOD(setExposureSensitivity:(NSString *)iso) {
  char isoChar = [iso UTF8String];
  NSNumber *isoValue = [[NSNumber alloc]initWithChar:isoChar];
  [self.CPID_decoder setExposureSensitivity:isoValue];
}

//public void setFixedExposureTime(int value)
RCT_EXPORT_METHOD(setFixedExposureTime:(NSNumber *)value) {
  [self.CPID_decoder setExposureTime:value];
}

//public void setFocus(String focus)
RCT_EXPORT_METHOD(setFocus:(NSString *)focus) {
  [self.CPID_decoder setFocus:focus];
}

//public void setFocusDistance(float focus)
RCT_EXPORT_METHOD(setFocusDistance:(NSNumber *)focus) {
  [self.CPID_decoder setFocusDistance:focus];
}

//public void setNumberOfBarcodesToDecode(int number)
RCT_EXPORT_METHOD(setNumberOfBarcodesToDecode:(NSNumber *)number) {
  [self.CPID_decoder setNumberOfBarcodesToDecode:number.intValue];
}

//public void setTorch(boolean enable)
RCT_EXPORT_METHOD(setTorch:(BOOL)enable) {
  [self.CPID_decoder setTorch:enable];
}

//public void setWhiteBalance(Boolean enable, String balance)
RCT_EXPORT_METHOD(setWhiteBalance:(BOOL)enable balanceValue:(NSString *)balance) {
  [self.CPID_decoder setWhiteBalance:enable balance:balance];
}

//todo callback anpassen -> erstellen wenn der benutzer die camera preview schliesst.
//public void startCameraPreview(Callback success)
RCT_EXPORT_METHOD(startCameraPreview:(RCTResponseSenderBlock)callback) {
  dispatch_async(dispatch_get_main_queue(), ^{
    self.previewView.alpha = 1.0f;
    //NSArray *result = [self.CPID_decoder startCameraPreview];
    NSArray * result = [self.CPID_decoder startCameraPreview:^(NSArray *result) {
      self.previewView.alpha = 0.0f;
    }];
    if(callback != nil)
      callback(@[[NSNull null], result]);
  });
}

//public void startDecoder(Callback success)
RCT_EXPORT_METHOD(startDecoder) {
  dispatch_async(dispatch_get_main_queue(), ^{
    [self.CPID_decoder startDecoder:^(NSArray * _Nonnull result) {
      [self barcodeEventReceived:result];
    }];
  });
}

//public void stopCameraPreview()
RCT_EXPORT_METHOD(stopCameraPreview) {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self.CPID_decoder stopDecoding];
        [self.CPID_decoder stopCameraPreview];
        self.previewView.alpha = 0.0f;
    });
}

//public void stopDecoding()
RCT_EXPORT_METHOD(stopDecoding) {
  [self.CPID_decoder stopDecoding];
}

//public void stringFromSymbologyType(String type, Callback success)
RCT_EXPORT_METHOD(stringFromSymbologyType:(NSString *)type callbackName:(RCTResponseSenderBlock)callback) {
  NSArray *res = [self.CPID_decoder stringFromSymbologyType:type];
  callback(@[[NSNull null], res]);
}

//todo debuggen
//public void setSymbologyProperties(JSONArray array)
RCT_EXPORT_METHOD(setSymbologyProperties:(NSArray *)array) {
  NSLog(@"Test setSymbologyProperties");
  //NSArray *res = [self.CPID_decoder setSymbologyProperties:<#(NSString * _Nonnull)#> symbologyData:<#(NSDictionary * _Nonnull)#>:type];
  
  //IONIC
  /*
   
   NSDictionary* symbology = [[command arguments] objectAtIndex:0];
   NSString *value = symbology[@"symbology"];
   
   NSDictionary* data = [self parsePreferences:[[command arguments] objectAtIndex:1]];
   
   NSArray *res = [self.CPID_decoder setSymbologyProperties:value symbologyData:data];
   
   */
}

//todo
//public void setCameraButtons(JSONArray buttons, Callback buttonCallback)
RCT_EXPORT_METHOD(setCameraButtons:(NSArray *)array) {
  NSLog(@"Test");
  //NSArray *res = [self.CPID_decoder setSymbologyProperties:<#(NSString * _Nonnull)#> symbologyData:<#(NSDictionary * _Nonnull)#>:type];
}

//public void showCrossHair(Boolean enable)
RCT_EXPORT_METHOD(showCrossHair:(BOOL)enable) {
  [self.CPID_decoder showCrossHair:enable];
}

//public void enableNativeZoom(Boolean enable)
RCT_EXPORT_METHOD(enableNativeZoom:(BOOL)enable) {
  [self.CPID_decoder enableNativeZoom:enable];
}

//public void enableAugmentedReality(Boolean enable)
RCT_EXPORT_METHOD(enableAugmentedReality:(BOOL)enable) {
  [self.CPID_decoder enableAugmentedReality:enable];
}

//public void ar_showVisualizeBarcodes(Boolean enable)
RCT_EXPORT_METHOD(ar_showVisualizeBarcodes:(BOOL)enable) {
  [self.CPID_decoder ar_showVisualizeBarcodes:enable];
}

//TODO
//public void ar_detectBarcode(String[] values)
RCT_EXPORT_METHOD(ar_detectBarcode:(NSString *)data) {
  [self.CPID_decoder ar_detectBarcode:data];
  /*
   if(self.CPID_augmentedReality) {
   NSDictionary* dict = [[command arguments] objectAtIndex:0];
   NSString* data = dict[@"data"];
   NSArray * res = [self.CPID_decoder ar_detectBarcode:data];
   pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:res];
   } else {
   pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"enable Augmented Reality to show visualized Barcodes."];
   */
}


//public void ar_showDetails(String html)
RCT_EXPORT_METHOD(ar_showDetails:(NSString *)html) {
  [self.CPID_decoder ar_showDetails:html];
}

//public void enableAllBarcodes(Boolean enable)
RCT_EXPORT_METHOD(enableAllBarcodes:(BOOL)enable) {
  [self.CPID_decoder enableAllDecoders:enable];
}

@end
