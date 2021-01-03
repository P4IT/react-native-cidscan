#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import "CaptureIDLibrary.h"

NS_ASSUME_NONNULL_BEGIN

@interface CIDScan : RCTEventEmitter <RCTBridgeModule>
@property (nonatomic) CaptureIDLibrary *CPID_decoder;
@property (nonatomic) UIView *previewView;
@end

NS_ASSUME_NONNULL_END
