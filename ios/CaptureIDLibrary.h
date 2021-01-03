//
//  iOS_CaptureID_Library.h
//  iOS-CaptureID-Library
//
//  Created by Christian Jung on 06.03.19.
//  Copyright © 2019 Christian Jung. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface CaptureIDLibrary : NSObject

@property (nonatomic, strong)UIView * _Nonnull rootView;

//-(_Nonnull instancetype)initWithUIview:(UIView * _Nonnull)rootView;
-(instancetype _Nonnull )initWithUIview:(UIView*_Nullable)rootView resultBlock:(void (^ _Nullable)(BOOL result))resultBlock;

#pragma mark - License

/*!
 @discussion Show the App settings, this function is used by the developer, if the camera-permission is not granted.
 */
-(void) showAppSettings;

/*!
 @discussion Activate the CortexDecoderLicense in CortexDecoder we need to check the Preferences for the Key.
 @param key NSString  The CortexDecoderLicense
 @param resultHandler (void (^)(NSArray *result))  We need a callback function, because the activateLicense is a async function
 */
-(void) activateLicense:(NSString * _Nonnull)key resultHandler:(nonnull void (^)(NSArray * _Nonnull result))resultHandler;

/*!
 @discussion Activate the CortexDecoderLicense in CortexDecoder we need to check the Preferences for the Key.
 @param key NSString  The CortexDecoderLicense
 @param resultHandler (void (^)(NSArray *result))  We need a callback function, because the activateLicense is a async function
 */
-(void) activateEDKLicense:(NSString * _Nonnull)filename customerID:(NSString * _Nonnull)customerID resultHandler:(nonnull void (^)(NSArray * _Nonnull result))resultHandler;

/*!
 @discussion Activate the CortexDecoderLicense in CortexDecoder we need to check the Preferences for the Key.
 @param key NSString  The CortexDecoderLicense
 @param resultHandler (void (^)(NSArray *result))  We need a callback function, because the activateLicense is a async function
 */
-(void) activateEDKLicenseWithKey:(NSString * _Nonnull)edkKey customerID:(NSString * _Nonnull)customerID resultHandler:(nonnull void (^)(NSArray * _Nonnull result))resultHandler;

/*!
 @discussion Check the camera permission
 @param resultBlock (void (^ _Nonnull)(BOOL result)) a callback that return true or false;
 */
-(void) checkCameraPermissionWithBlock:(void (^ _Nonnull)(BOOL result))resultBlock;

/**
 @discussion Check if license is activated
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) isLicenseActivated;

/**
 @discussion Check if license is Expired
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) isLicenseExpired;


#pragma mark - Version

/**
 @discussion Get the CortexDecoder decoderVersion
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull)decoderVersion;

/**
 @discussion Get the CortexDecoder decoderVersionLevel
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull)decoderVersionLevel;

/**
 @discussion Get the CortexDecoder getSdkVersion
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull)getSdkVersion;

/**
 @discussion Get the CortexDecoder libraryVersion
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull)libraryVersion;


#pragma mark - Scanner

/**
 @discussion Start CameraPreview
 @return NSArray PluginResultObject
 todo keepcallback = yes
 */
-(NSArray * _Nonnull)startCameraPreview:(nonnull void (^)(NSArray * _Nullable result))resulthandler;

/**
 @discussion Start Decoder KeepCallback = Yes
 @param resulthandler (void (^)(NSArray *)) Handles the barcode the the docerder decodes.
 @return NSArray PluginResultObject can return an Error
 */
-(NSArray * _Nonnull)startDecoder:(nonnull void (^)(NSArray * _Nonnull result))resulthandler;

/**
 @discussion Capture Image in Buffer
 @param value BOOL enable / disable the capture Mode
 @return NSArray PluginResultObject
 //todo
 */
-(NSArray * _Nonnull)captureImage:(BOOL)value;

/**
 @discussion Change the SoundFile
 @param fileName NSString path to file
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull)changeSound:(NSString * _Nonnull)fileName;

/**
 @discussion Function is not Supported
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull)closeSharedObject;

/**
 @discussion Disable VideoCapture and Decoding
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull)closeCamera;

/**
 @discussion Enable Vibrate on Scan
 @param value BOOL
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull)enableVibrateOnScan:(BOOL)value;

/**
 @discussion Set enableDecoding = NO and need to call keepcallback = NO
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull)stopDecoding;

/**
 @discussion Set enableVideoCapture = NO and [clearViews]
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull)stopCameraPreview;

/**
 @discussion if value = true -> [[CortexDecoderLibrary sharedObject] enableImageSaving:CD_ImageSaving_Manual];
 @param value BOOL
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull)enableScannedImageCapture:(BOOL)value;

//-- Decoder -- //
#pragma mark - Decoder

/**
 @discussion Call [[CortexDecoderLibrary sharedObject] lowContrastDecodingEnabled:enable];
 @param enable BOOL
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) lowContrastDecodingEnabled:(BOOL)enable;

/**
 @discussion Call    [[CortexDecoderLibrary sharedObject] decoderTimeLimitInMilliseconds:timeLimit];
 @param timeLimit int
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) decoderTimeLimitInMilliseconds:(int)timeLimit;

/**
 @discussion setDecoderResolution
 @param resolution NSString*
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setDecoderResolution:(NSString * _Nonnull)resolution;

// Default is 1
// Level = 1 - 20
/**
 @discussion setNumberOfDecodes. Default value is 1, Level should be between 1 and 20.
 @param num int
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setNumberOfBarcodesToDecode:(int)num;

// Default is 10
// Value tolorance level = 0 - 10
/**
 @discussion setDecoderToleranceLevel. Default value is 10, Level should be between 0 and 10.
 @param toleranceLevel int
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setDecoderToleranceLevel:(int)toleranceLevel;

/**
 @discussion Get the licensed Symbologies and return the Symbologies with the PluginResultobject.
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) getLicensedSymbologies;

/**
 @discussion Enable all symbologies or disable all symbologies.
 @param enable BOOL
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) enableAllDecoders:(BOOL)enable;


// -- Device -- //
#pragma mark - Device

/**
 @discussion Return the SupportedCameraTypes.
 @return NSArray PluginResultObject with BackFacing and FrontFacing
 */
-(NSArray * _Nonnull) getSupportedCameraTypes;

/**
 @discussion Set the CameraTypes or BackFacing and FrontFacing
 @param cameraType NSString*
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setCameraType:(NSString * _Nonnull)cameraType;

/**
 @discussion Set the CameraFocus Focus_Types [Focus_Far, Focus_Fix_Far, Focus_Fix_Normal, Focus_Normal]
 @param focus NSString*
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setFocus:(NSString * _Nonnull)focus;

/**
 @discussion Get a Bool - Value if zoom is Supported
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) isZoomSupported;

/**
 @discussion Set the Torch[enable] todo decoderCallbackID ->  keepcallback = yes
 @param enable BOOL
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setTorch:(BOOL)enable;

/**
 @discussion Return the Current size of the DecoderVideo
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) currentSizeOfDecoderVideo;

/**
 @discussion Return a Bool Value (if device has torch)
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) hasTorch;

/**
 @discussion Enabled the Sound when decode a barcode
 @param enable BOOL
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) enableBeepPlayer:(BOOL)enable;


// -- Region -- //
#pragma mark - Region

/**
 @discussion Set the ROI left padding
 @param column int
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) regionOfInterestLeft:(int)column;

/**
 @discussion Set the ROI top Padding
 @param row int
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) regionOfInterestTop:(int)row;

/**
 @discussion Set the ROI width
 @param roiWidth int
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) regionOfInterestWidth:(int)roiWidth;

/**
 @discussion Set the ROI heigth
 @param roiHeight int
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) regionOfInterestHeight:(int)roiHeight;

/**
 @discussion Enable Region of Interest
 @param enable BOOL
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) ensureRegionOfInterest:(BOOL)enable;


//-- Views -- //
#pragma mark - Views

/**
 @discussion Set Camera Buttons | Need to KeepCallBack  = Yes
 @param cameraButtons NSMutableArray* An Array of Buttons
 @param resultHandler (void (^)(NSArray *result)) the ResultHandler that get called when the button get pressed
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setCameraButtons:(NSMutableArray * _Nonnull)cameraButtons resultButtonHandler:(nonnull void (^)(NSArray * _Nonnull result))resultHandler;

/**
 @discussion Show CrossHair | Need to KeepCallBack  = Yes
 @param show BOOL
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) showCrossHair:(BOOL)show;

/**
 @discussion Toggle Camera | Need to KeepCallBack  = Yes
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) toggleCamera;

/**
 @discussion Toogle Camera Function. Get called from "toggleCamera"
 */
-(void) toggleCameraFunction;

/**
 @discussion EnableNativeZoom -> return FUNCTION_NOT_SUPPORTED
 @param enable BOOL
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) enableNativeZoom:(BOOL)enable;

/**
 @discussion SeekBar for Zoom -> return FUNCTION_NOT_SUPPORTED
 @param enable BOOL
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) enableSeekBarZoom:(BOOL)enable;

/**
 @discussion Remove ar_infoText that was build by ar_showDetails
 */
-(void) clearViews;


// --AugmentedReality -- //
#pragma mark - AugmentedReality

-(NSArray * _Nonnull) enablePicklistMode:(BOOL)enable;

-(NSArray * _Nonnull) setAimStyle:(int)style;

-(NSArray * _Nonnull) setAimSize:(int)width height:(int)height color:(int)color;

/**
 @discussion Enable AugmentedReality, this needs be enabled first, when we would use other ar_ functions.
 @param enable BOOL
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) enableAugmentedReality:(BOOL)enable;

/**
 @discussion  set continuousScanning = enable and displayBarcodeOverlay = enable
 @param enable BOOL
 @return NSArray PluginResultObject with Error when ar_ is disabled
 */
-(NSArray * _Nonnull) ar_showVisualizeBarcodes:(BOOL)enable;

/**
 @discussion find Barcode and set findBarcode = data
 @param data NSString*
 @return NSArray PluginResultObject with Error when ar_ is disabled
 */
-(NSArray * _Nonnull) ar_detectBarcode:(NSString * _Nonnull)data;

/**
 @discussion show details of decoded Barcode as HTML - Site
 @param html NSString*
 @return NSArray PluginResultObject with Error when ar_ is disabled
 */
-(NSArray * _Nonnull) ar_showDetails:(NSString * _Nonnull)html;


#pragma mark - Other Functions

/**
 @discussion Function return -> FUNCTION_NOT_SUPPORTED
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) CRD_Set;

/**
 @discussion Function return -> FUNCTION_NOT_SUPPORTED
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) doDecode;

/**
 @discussion Enable or disable the fixedExposureMode | Need to check the error code
 @param enabled BOOL
 @return NSArray PluginResultObject with Error
 */
-(NSArray * _Nonnull) enableFixedExposureMode:(BOOL)enabled;

/**
 @discussion This method is used to generate Device Unique ID.
 */
-(NSArray * _Nonnull) generateDeviceID;

//TODO
/**
 @discussion Function return -> FUNCTION_NOT_SUPPORTED
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) getCameraPreview;

//TODO
/**
 @discussion Return the getDecodeVal from the CortexDecoderLibrary
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) getDecodeVal;

/**
 @discussion Return the ExposureTime -> exposure_TimeScale
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) getExposureTime;

/**
 @discussion Function return -> FUNCTION_NOT_SUPPORTED
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) getFocusDistance;

/**
 @discussion Return the ma Zoom as Double Value
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) getMaxZoom;

/**
 @discussion Function return -> FUNCTION_NOT_SUPPORTED
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) getSensitivityBoost;
//todo

/**
 @discussion Function return -> FUNCTION_NOT_SUPPORTED
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) getSizeForROI;

/**
 @discussion Get the supported FocusModes as objectValue
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) getSupportedFocusModes;

/**
 @discussion Function return -> FUNCTION_NOT_SUPPORTED
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) getSupportedWhiteBalance;

/**
 @discussion get the ZoomRations as object Value
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) getZoomRatios;

//todo
/**
 @discussion Load the LicenseFile
 @param fileName NSString* filename / Path
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) loadLicenseFile:(NSString * _Nonnull)fileName;

/**
 @discussion Play Sound AudioServicesPlaySystemSound(1106);
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) playBeepSound;

/**
 @discussion Function return -> FUNCTION_NOT_SUPPORTED
 @param value BOOl enable or disable the autofocus by count function
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setAutoFocusResetByCount:(BOOL)value;

/**
 @discussion Function return -> FUNCTION_NOT_SUPPORTED
 @param enabled BOOL enable the native camera zoom
 @param zoom NSNumber* set the camera zoom as a float
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setCameraZoom:(BOOL)enabled cameraZoom:(NSNumber * _Nonnull)zoom;

/**
 @discussion Function return -> FUNCTION_NOT_SUPPORTED
 @param charsetName NSString* The charset that will be set
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setEncodingCharsetName:(NSString * _Nonnull)charsetName ;

/**
 @discussion Enable or disable the CortexDecoderFunction matchDecodeCountExactly.
 @param enable BOOL
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setExactlyNBarcodes:(BOOL)enable;

/**
 @discussion Set the ExposureSensitivity as Number, can return an Error
 @param iso NSNumber*
 @return NSArray PluginResultObject with error
 */
-(NSArray * _Nonnull) setExposureSensitivity:(NSNumber * _Nonnull)iso;

/**
 @discussion Set the ExposureTime as Number, can return an Error
 @param ep NSNumber*
 @return NSArray PluginResultObject with error
 */
-(NSArray * _Nonnull) setExposureTime:(NSNumber * _Nonnull)ep;

/**
 @discussion Function return -> FUNCTION_NOT_SUPPORTED
 @param distance NSNumber* the distance that will be focused
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setFocusDistance:(NSNumber * _Nonnull) distance;

/**
 @discussion Function return -> FUNCTION_NOT_SUPPORTED
 @param enable BOOL is true then this feature is enabled.
 @param mBalance NSString sets the white balance mode if enable is true.
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setWhiteBalance:(BOOL)enable balance:(NSString * _Nonnull)mBalance;

/**
 @discussion Return the SymbologyType as NSString
 @param type NSString*
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) stringFromSymbologyType:(NSString * _Nonnull)type;

/**
 @discussion Return the CameraTypeValue as NSString
 @param type NSString*
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) CameraTypeValueOf:(NSString * _Nonnull) type;

/**
 @discussion get all CameraTypes
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) CameraTypeValues;

/**
 @discussion Return the SymbologyType as NSString
 @param type NSString*
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) FocusValueOf:(NSString * _Nonnull) type;

/**
 @discussion Return the FocusValues as Object
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) FocusValues;

/**
 @discussion Return the FocusValue
 @param type NSString*
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) SymbologyTypeValueOf:(NSString * _Nonnull)type;

/**
 @discussion Return the FocusValue as Objects
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) SymbologyTypeValues;

// todo decoderCallbackID ->  keepcallback = yes
/**
 @discussion set the Properties of a Symbology | KeepCallback = yes
 @param symbology NSString symbology name
 @param data NSDictionary Symbology Data Object
 @return NSArray PluginResultObject
 */
-(NSArray * _Nonnull) setSymbologyProperties:(NSString * _Nonnull)symbology symbologyData:(NSDictionary * _Nonnull)data;

/**
 @discussion parse the Objects to a NSMutableDictionary
 @param data NSArray Symbology Data Object
 @return NSMutableDictionary Objectvalues
 */
-(NSMutableDictionary * _Nonnull) parsePreferences:(NSArray * _Nonnull)data;



@end
